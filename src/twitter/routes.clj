(ns twitter.routes
  (:require [compojure.core :as cj]
            [compojure.route :as cjr]
            [ring.middleware.json :refer [wrap-json-params]]
            [ring.middleware.params :refer [wrap-params]]
            [twitter.authentication :as auth]
            [twitter.common :as comm]
            [twitter.status-codes :refer [http-code]]
            [twitter.xhr.user :as txu]))


(defn wrap-authentication [handler]
  (fn [request]
    (let [auth-header (get-in request [:headers "authorization"])]
      (if auth-header
        (let [token (subs auth-header 7)
              claims (auth/decode-jwt token)]  ; Extract token after "Bearer "
          (if (and claims (not (auth/is-token-expired? claims)))
            (handler request)  ; Pass the request to the handler if valid
            (comm/json-response (http-code :bad-request)
                                {:message "Authentication failed! Token expired or invalid."})))
        (comm/json-response (http-code :bad-request)
                            {:message "Authorization header missing."})))))


(cj/defroutes app
  (cj/GET "/" [] "Hello World")
  (cjr/not-found "Page not found"))


(cj/defroutes non-auth-routes
  (cj/POST "/user/register" {params :params}  (txu/register params)))


(def all-routes
  (cj/routes
   (-> non-auth-routes wrap-json-params wrap-params)
   (wrap-authentication
    (-> app wrap-json-params wrap-params))))
