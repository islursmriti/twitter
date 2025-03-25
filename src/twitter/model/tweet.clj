(ns twitter.model.tweet
  (:require
   [twitter.utils :as utils]
   [twitter.db :as db]
   [twitter.model.user :refer [get-user]]))


(defn post-tweet
  [text media {:keys [id username]}]
  (get-user id username) ;to check if user is active
  (let [tweet-id (str (:uuid-prefix-tweet utils/data)
                      (random-uuid))
        current-time (java.util.Date/from (java.time.Instant/now))]
    (db/insert (:mongo-coll-tweets utils/data)
               {:id tweet-id :user-id id
                :body {:text text :media media}
                :created-at current-time :updated-at current-time})
    tweet-id))
