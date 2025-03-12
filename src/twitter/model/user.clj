(ns twitter.model.user
  (:require
   [twitter.common :as comm]
   [twitter.db :as db]))


(defn user-exists? [username email]
  (db/find-one (:mongo-coll-usr comm/config)
               {"$or" [{:username username} {:email email}]}))


(defn register [user_id username email password]
  (if (user-exists? username email)
    (throw (Exception. "user-already-exists"))
    (db/insert (:mongo-coll-usr comm/config) {:id user_id :username username :email email :password password})))
