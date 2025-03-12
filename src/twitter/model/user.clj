(ns twitter.model.user
  (:require
   [twitter.common :as comm]
   [twitter.db :as db]))


(defn user-exists? [username email]
  (db/find-one (:mongo-coll-usr comm/config)
               {"$and" [{:active true}
                        {"$or" [{:username username} {:email email}]}]}))


(defn register [user_id username email password profile-data]
  (if (user-exists? username email)
    (throw (Exception. "user-already-exists"))
    (let [current-time (java.util.Date/from (java.time.Instant/now))]
      (db/insert (:mongo-coll-usr comm/config)
                 {:id user_id :username username
                  :email email :password password
                  :profile profile-data :active true
                  :followers [] :following []
                  :created-at current-time :updated-at current-time}))))
