(ns twitter.model.user
  (:require
   [cheshire.core :as json]
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


(defn login [username email]
  (let [user-details (user-exists? username email)]
    [(get user-details "id") (get user-details "username") (get user-details "password")]))


(defn get-user [id username]
  (let [user-data (into {} (db/find-one (:mongo-coll-usr comm/config)
                                        {"$and" [{:id id} {:username username}]}))]
    (if (= false (get user-data "active"))
      (throw (Exception. "user-doesnt-exists"))
      (dissoc user-data "_id" "id" "password"))))


(defn update-user [user-data-from-token update-params]
  (let [update-time (java.util.Date/from (java.time.Instant/now))
        update-query (-> (into {} (for [[k v] update-params]
                                    (if (= "profile" k)
                                      [(keyword k) (json/parse-string v)]
                                      [(keyword k) v])))
                         (assoc :updated-at update-time))
        id (user-data-from-token :id)
        username (user-data-from-token :username)]
    (get-user id username) ;to check if user is active
    (db/update-query (:mongo-coll-usr comm/config)
                     {:id id} update-query)))
