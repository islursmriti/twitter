(ns twitter.handler.user
  (:require
   [clojure.string]
   [clojure.tools.logging :as log]
   [twitter.authentication :as auth]
   [twitter.common :as comm]
   [twitter.model.user :as tmu]))


(defn register
  [{:strs [username email password]}]
  (if (some empty? [username email password])
    (throw (Exception. "parameter-validation-failed"))
    (let [user_id (str (:uuid-prefix-user comm/config)
                       (random-uuid))]
      (tmu/register user_id username email password)
      (auth/generate-jwt {:id user_id :username username}))))
