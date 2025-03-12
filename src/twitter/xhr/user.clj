(ns twitter.xhr.user
  (:require
   [clojure.tools.logging :as log]
   [twitter.common :as comm]
   [twitter.handler.user :as thu]
   [twitter.status-codes :refer [http-code]]))


(defn register
  [params]
  (try (let [token (thu/register params)]
         (comm/json-response (http-code :ok)
                             {:message "user registered successfully", :token token}))
       (catch Exception e
         (cond (= (.getMessage e) "parameter-validation-failed")
               (comm/json-response (http-code :bad-request)
                                   {:message "Username, Email And Password Are Mandatory"}
                                   (.getMessage e))
               (= (.getMessage e) "user-already-exists")
               (comm/json-response (:conflict http-code)
                                   {:message "User Already Exists"}
                                   (.getMessage e))
               :else (comm/json-response (:internal-server-error http-code)
                                         {:message "Internal Server Error"})))))
