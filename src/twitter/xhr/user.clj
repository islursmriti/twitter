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


(defn login
  [params]
  (try (let [token (thu/login params)]
         (comm/json-response (http-code :ok)
                             {:message "success", :token token}))
       (catch Exception e
         (cond (= (.getMessage e) "parameter-validation-failed")
               (comm/json-response (http-code :bad-request)
                                   {:message "Password And Email/Username is Mandatory"}
                                   (.getMessage e))
               (= (.getMessage e) "invalid-arguments")
               (comm/json-response (http-code :bad-request)
                                   {:message "Username/Email Or Password Is Invalid"}
                                   (.getMessage e))
               :else (comm/json-response (:internal-server-error http-code)
                                         {:message "Internal Server Error"})))))


(defn get-user
  [user-data]
  (try (let [user-data-from-db (thu/get-user user-data)]
         (comm/json-response (http-code :ok)
                             user-data-from-db))
       (catch Exception e
         (cond (= (.getMessage e) "user-doesn't-exists")
               (comm/json-response (http-code :bad-request)
                                   {:message "User Doesn't Exists"}
                                   (.getMessage e))
               :else (comm/json-response (:internal-server-error http-code)
                                         {:message "Internal Server Error"})))))


(defn update-user
  [user-data-from-token user-data-from-params]
  (try (thu/update-user user-data-from-token user-data-from-params)
       (comm/json-response (http-code :ok)
                           {:message "success"})
       (catch Exception e
         (cond (= (.getMessage e) "user-doesn't-exists")
               (comm/json-response (http-code :bad-request)
                                   {:message "User Doesn't Exists"}
                                   (.getMessage e))
               :else (comm/json-response (:internal-server-error http-code)
                                         {:message "Internal Server Error"})))))


(defn delete-user
  [user-data]
  (try (thu/delete-user user-data)
       (comm/json-response (http-code :ok)
                           {:message "success"})
       (catch Exception e
         (comm/json-response (:internal-server-error http-code)
                             {:message "Internal Server Error"}))))
