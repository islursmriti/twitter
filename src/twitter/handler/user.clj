(ns twitter.handler.user
  (:require
   [cheshire.core :as json]
   [clojure.string]
   [twitter.authentication :as auth]
   [twitter.common :as comm]
   [twitter.model.user :as tmu]))


(defn register
  [{:strs [username email password profile]}]
  (if (some empty? [username email password])
    (throw (Exception. "parameter-validation-failed"))
    (let [user_id (str (:uuid-prefix-user comm/config)
                       (random-uuid))
          profile-data (if profile (json/parse-string profile true) nil)]
      (tmu/register user_id username email password profile-data)
      (auth/generate-jwt {:id user_id :username username}))))
