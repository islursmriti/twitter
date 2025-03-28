(ns twitter.model.tweet
  (:require
   [twitter.common :as comm]
   [twitter.db :as db]
   [twitter.model.user :refer [get-user]]))


(defn post-tweet
  [text media {:keys [id username]}]
  (get-user id username) ;to check if user is active
  (let [tweet-id (str (:uuid-prefix-tweet comm/config)
                      (random-uuid))
        current-time (java.util.Date/from (java.time.Instant/now))]
    (db/insert (:mongo-coll-tweets comm/config)
               {:id tweet-id :user-id id
                :body {:text text :media media}
                :created-at current-time :updated-at current-time})
    tweet-id))
