(ns twitter.authentication
  (:require [buddy.sign.jwt :as jwt]
            [twitter.common :as comm]
            [clojure.tools.logging :as log]))


(def secret-key (:secret-key comm/config))


(defn generate-jwt
  [claims]
  (let [payload (assoc claims :exp (+ (System/currentTimeMillis) (:token-exp-time comm/config)))]
    (jwt/sign payload secret-key)))


(defn decode-jwt
  [token]
  (try
    (jwt/unsign token secret-key)
    (catch Exception e
      (log/error "Authentication failed! Token may be invalid." (.getMessage e)))))


(defn is-token-expired?
  [claims]
  (let [expiration-time (:exp claims)
        current-time (System/currentTimeMillis)]
    (if (nil? expiration-time)
      false
      (> current-time expiration-time))))  ; Check if current time is greater than expiration time


;test
(comment (defn authenticate
           [token]
           (let [claims (decode-jwt token)]
             (when claims
               (if (is-token-expired? claims)
                 (log/error "Authentication failed! Token has expired.")
                 (log/info "Authenticated user with ID:" (:user-id claims))))))


;; Generate JWT test
         (def data {:user-id 123 :username "example"})
         (def token (generate-jwt data))
         (log/info "JWT Token:" token)


;; Authenticate User test
         (authenticate token))
