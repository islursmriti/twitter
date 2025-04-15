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


(defn update-tweet
  [tweet-data user-data]
  (try (tht/update-tweet tweet-data user-data)
       (comm/json-response (http-code :ok)
                           {:message "success"})
       (catch Exception e
         (cond (= (.getMessage e) "parameter-validation-failed")
               (comm/json-response (http-code :bad-request)
                                   {:message "Tweet Id and Text Or Media is Mandatory"}
                                   (.getMessage e))
               (= (.getMessage e) "user-doesnt-exists")
               (comm/json-response (http-code :bad-request)
                                   {:message "User Doesn't Exists"}
                                   (.getMessage e))
               :else (comm/json-response (:internal-server-error http-code)
                                         {:message "Internal Server Error"})))))


(defn delete-tweet
  [{:keys [tweet-id]} user-data]
  (try (tht/delete-tweet tweet-id user-data)
       (comm/json-response (http-code :ok)
                           {:message "success"})
       (catch Exception e
         (if (= (.getMessage e) "user-doesn't-exists")
           (comm/json-response (http-code :bad-request)
                               {:message "User Doesn't Exists"}
                               (.getMessage e))
           (comm/json-response (:internal-server-error http-code)
                               {:message "Internal Server Error"})))))


(defn get-tweet-data
  [{:keys [tweet-id]} user-data]
  (try (let [tweet-data (tht/get-tweet-data tweet-id user-data)]
         (comm/json-response (http-code :ok)
                             {:message "success" :tweet-data tweet-data}))
       (catch Exception e
         (if (= (.getMessage e) "user-doesn't-exists")
           (comm/json-response (http-code :bad-request)
                               {:message "User Doesn't Exists"}
                               (.getMessage e))
           (comm/json-response (:internal-server-error http-code)
                               {:message "Internal Server Error"})))))


(defn post-comment
  [comment-data user-data]
  (try (let [comment-id (tht/post-comment comment-data user-data)]
         (comm/json-response (http-code :ok)
                             {:message "success" :comment-id comment-id}))
       (catch Exception e
         (cond (= (.getMessage e) "parameter-validation-failed")
               (comm/json-response (http-code :bad-request)
                                   {:message "Parent ID and Text Or Media is Mandatory"}
                                   (.getMessage e))
               (= (.getMessage e) "user-doesnt-exists")
               (comm/json-response (http-code :bad-request)
                                   {:message "User Doesn't Exists"}
                                   (.getMessage e))
               :else (comm/json-response (:internal-server-error http-code)
                                         {:message "Internal Server Error"})))))


(defn get-comment-data
  [comment-data user-data]
  (try (let [comment-data (tht/get-comment-data comment-data user-data)]
         (comm/json-response (http-code :ok)
                             {:message "success" :comment-data comment-data}))
       (catch Exception e
         (if (= (.getMessage e) "user-doesn't-exists")
           (comm/json-response (http-code :bad-request)
                               {:message "User Doesn't Exists"}
                               (.getMessage e))
           (comm/json-response (:internal-server-error http-code)
                               {:message "Internal Server Error"})))))


(defn update-comment
  [comment-data user-data]
  (try (tht/update-comment comment-data user-data)
       (comm/json-response (http-code :ok)
                           {:message "success"})
       (catch Exception e
         (cond (= (.getMessage e) "parameter-validation-failed")
               (comm/json-response (http-code :bad-request)
                                   {:message "Parent Id and Text Or Media is Mandatory"}
                                   (.getMessage e))
               (= (.getMessage e) "user-doesnt-exists")
               (comm/json-response (http-code :bad-request)
                                   {:message "User Doesn't Exists"}
                                   (.getMessage e))
               :else (comm/json-response (:internal-server-error http-code)
                                         {:message "Internal Server Error"})))))


(defn delete-comment
  [comment-data user-data]
  (try (tht/delete-comment comment-data user-data)
       (comm/json-response (http-code :ok)
                           {:message "success"})
       (catch Exception e
         (if (= (.getMessage e) "user-doesn't-exists")
           (comm/json-response (http-code :bad-request)
                               {:message "User Doesn't Exists"}
                               (.getMessage e))
           (comm/json-response (:internal-server-error http-code)
                               {:message "Internal Server Error"})))))


(defn get-comment
  [page-data user-data]
  (try (let [comments (tht/get-comment page-data user-data)]
         (comm/json-response (http-code :ok)
                             {:message "success" :comments comments}))
       (catch Exception e
         (cond  (= (.getMessage e) "parameter-validation-failed")
                (comm/json-response (http-code :bad-request)
                                    {:message "Parent Id, limit and page is Mandatory"}
                                    (.getMessage e))
                (= (.getMessage e) "user-doesn't-exists")
                (comm/json-response (http-code :bad-request)
                                    {:message "User Doesn't Exists"}
                                    (.getMessage e))
                :else (comm/json-response (:internal-server-error http-code)
                                          {:message "Internal Server Error"})))))


(defn like-tweet
  [tweet-data user-data]
  (try (tht/like-tweet tweet-data user-data)
       (comm/json-response (http-code :ok)
                           {:message "success"})
       (catch Exception e
         (cond  (= (.getMessage e) "parameter-validation-failed")
                (comm/json-response (http-code :bad-request)
                                    {:message "Parent Id and Tweet Id is Mandatory"}
                                    (.getMessage e))
                (= (.getMessage e) "user-doesn't-exists")
                (comm/json-response (http-code :bad-request)
                                    {:message "User Doesn't Exists"}
                                    (.getMessage e))
                :else (comm/json-response (:internal-server-error http-code)
                                          {:message "Internal Server Error"})))))


(defn unlike-tweet
  [tweet-data user-data]
  (try (tht/unlike-tweet tweet-data user-data)
       (comm/json-response (http-code :ok)
                           {:message "success"})
       (catch Exception e
         (cond  (= (.getMessage e) "parameter-validation-failed")
                (comm/json-response (http-code :bad-request)
                                    {:message "Parent Id and Tweet Id is Mandatory"}
                                    (.getMessage e))
                (= (.getMessage e) "user-doesn't-exists")
                (comm/json-response (http-code :bad-request)
                                    {:message "User Doesn't Exists"}
                                    (.getMessage e))
                :else (comm/json-response (:internal-server-error http-code)
                                          {:message "Internal Server Error"})))))
