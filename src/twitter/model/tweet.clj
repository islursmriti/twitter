(ns twitter.model.tweet
  (:require
   [twitter.utils :as utils]
   [twitter.db :as db]
   [twitter.model.user :refer [get-user]]
   [clojure.edn :as edn]))


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


(defn update-tweet
  [tweet-id text media {:keys [id username]}]
  (get-user id username) ;to check if user is active
  (let [updated-time (java.util.Date/from (java.time.Instant/now))]
    (db/update-query (:mongo-coll-tweets utils/data)
                     {:id tweet-id :user-id id} {:body {:text text :media media}
                                                 :updated-at updated-time})))


(defn delete-tweet
  [tweet-id {:keys [id username]}]
  (get-user id username) ;to check if user is active
  (db/delete (:mongo-coll-tweets utils/data)
             {:id tweet-id :user-id id}))


(defn get-tweet-data
  [tweet-id {:keys [id username]}]
  (get-user id username) ;to check if user who is viewing the tweet is active
  (dissoc (into {} (db/find-one (:mongo-coll-tweets utils/data)
                                {:id tweet-id})) "_id" "user-id"))


(defn post-comment
  [tweet-id parent-id text media {:keys [id username]}]
  (get-user id username) ;to check if user is active
  (let [comment-id (str (:uuid-prefix-comment utils/data)
                        (random-uuid))
        current-time (java.util.Date/from (java.time.Instant/now))]
    (db/insert (:mongo-coll-comments utils/data)
               {:id comment-id :tweet-id tweet-id
                :parent-id parent-id :user-id id
                :body {:text text :media media}
                :created-at current-time :updated-at current-time})
    comment-id))


(defn get-comment-data
  [tweet-id comment-id parent-id {:keys [id username]}]
  (get-user id username) ;to check if user who is viewing the tweet is active
  (dissoc (into {} (db/find-one (:mongo-coll-comments utils/data)
                                {:id comment-id :tweet-id tweet-id :parent-id parent-id})) "_id" "user-id" "id" "tweet-id" "parent-id"))


(defn update-comment
  [tweet-id comment-id parent-id text media {:keys [id username]}]
  (get-user id username) ;to check if user is active
  (let [updated-time (java.util.Date/from (java.time.Instant/now))]
    (db/update-query (:mongo-coll-comments utils/data)
                     {:id comment-id :user-id id
                      :tweet-id tweet-id :parent-id parent-id}
                     {:body {:text text :media media}
                      :updated-at updated-time})))


(defn delete-comment
  [tweet-id comment-id parent-id {:keys [id username]}]
  (get-user id username) ;to check if user is active
  (db/delete (:mongo-coll-comments utils/data)
             {:id comment-id :tweet-id tweet-id
              :parent-id parent-id :user-id id}))


(defn get-comment
  [tweet-id parent-id limit page {:keys [id username]}]
  (get-user id username) ;to check if user who is viewing the tweet is active
  (map #(dissoc (into {} %) "_id" "user-id" "tweet-id" "parent-id")
       (db/find-many (:mongo-coll-comments utils/data)
                     {:tweet-id tweet-id :parent-id parent-id} limit (* (dec page) limit))))
