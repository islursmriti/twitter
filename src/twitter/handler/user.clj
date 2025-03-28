(ns twitter.handler.user
  (:require
   [cheshire.core :as json]
   [clojure.string]
   [twitter.authentication :as auth]
   [twitter.common :as comm]
   [twitter.model.user :as tmu]
   [twitter.utils :as utils]))


(defn register
  [{:strs [username email password profile]}]
  (if (some empty? [username email password])
    (throw (Exception. "parameter-validation-failed"))
    (let [user_id (str (:uuid-prefix-user utils/data)
                       (random-uuid))
          profile-data (if profile (json/parse-string profile true) nil)]
      (tmu/register user_id username email password profile-data)
      (auth/generate-jwt {:id user_id :username username}))))


(defn login
  [{:strs [password username email]}]
  (if (or (empty? password) (every? empty? [username email]))
    (throw (Exception. "parameter-validation-failed"))
    (let [[user_id username-from-db password-from-db] (tmu/login username email)]
      (if (or (empty? user_id)
              (not= password-from-db password))
        (throw (Exception. "invalid-arguments"))
        (auth/generate-jwt {:id user_id :username username-from-db})))))


(defn get-user
  [user-data]
  (tmu/get-user (user-data :id) (user-data :username)))


(defn update-user
  [user-data-from-token user-data-from-params]
  (tmu/update-user user-data-from-token user-data-from-params))


(defn delete-user
  [user-data]
  (tmu/delete-user (user-data :id)))
