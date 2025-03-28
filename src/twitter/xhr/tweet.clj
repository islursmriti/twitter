(ns twitter.xhr.tweet
  (:require
   [twitter.common :as comm]
   [twitter.status-codes :refer [http-code]]
   [twitter.handler.tweet :as tht]))


(defn post-tweet
  [tweet user-data]
  (try (let [tweet-id (tht/post-tweet tweet user-data)]
         (comm/json-response (http-code :ok)
                             {:message "success" :tweet-id tweet-id}))
       (catch Exception e
         (cond (= (.getMessage e) "parameter-validation-failed")
               (comm/json-response (http-code :bad-request)
                                   {:message "Text Or Media is Mandatory"}
                                   (.getMessage e))
               (= (.getMessage e) "user-doesnt-exists")
               (comm/json-response (http-code :bad-request)
                                   {:message "User Doesn't Exists"}
                                   (.getMessage e))
               :else (comm/json-response (:internal-server-error http-code)
                                         {:message "Internal Server Error"})))))
