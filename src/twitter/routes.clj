(ns twitter.routes
  (:require [compojure.core :as cj]
            [compojure.route :as cjr]
            [twitter.authentication :as auth]
            [twitter.common :as comm]
            [twitter.status-codes :refer [http-code]]))


(defn wrap-authentication [handler]
  (fn [request]
    (let [auth-header (get-in request [:headers "authorization"])]
      (if auth-header
        (let [token (subs auth-header 7)
              claims (auth/decode-jwt token)]  ; Extract token after "Bearer "
          (if (and claims (not (auth/is-token-expired? claims)))
            (handler request)  ; Pass the request to the handler if valid
            (comm/json-response (http-code :bad-request)
                                "Authentication failed! Token expired or invalid.")))
        (comm/json-response (http-code :bad-request)
                            "Authorization header missing.")))))


(cj/defroutes app
  (cj/GET "/" [] "Hello World")
  (cjr/not-found "Page not found"))


(def app-with-middleware
  (wrap-authentication app))
