(ns twitter.handler.tweet
  (:require [twitter.model.tweet :as tmt]
            [cheshire.core :as json]))


(defn post-tweet
  [{:strs [text media]} user-data]
  (if (every? empty? [text media])
    (throw (Exception. "parameter-validation-failed"))
    (tmt/post-tweet text (json/parse-string media) user-data)))


(defn update-tweet
  [{:keys [tweet-id] :strs [text media]} user-data]
  (if (or (every? empty? [text media]) (empty? tweet-id))
    (throw (Exception. "parameter-validation-failed"))
    (tmt/update-tweet tweet-id text (json/parse-string media) user-data)))


(defn delete-tweet
  [tweet-id user-data]
  (tmt/delete-tweet tweet-id user-data))


(defn get-tweet-data
  [tweet-id user-data]
  (tmt/get-tweet-data tweet-id user-data))


(defn post-comment
  [{:keys [tweet-id] :strs [parent-id text media]} user-data]
  (if (or (every? empty? [text media]) (some empty? [parent-id tweet-id]))
    (throw (Exception. "parameter-validation-failed"))
    (tmt/post-comment tweet-id parent-id text (json/parse-string media) user-data)))


(defn get-comment-data
  [{:keys [tweet-id comment-id] :strs [parent-id]} user-data]
  (tmt/get-comment-data tweet-id comment-id parent-id user-data))


(defn update-comment
  [{:keys [tweet-id comment-id] :strs [parent-id text media]} user-data]
  (if (or (every? empty? [text media]) (empty? parent-id))
    (throw (Exception. "parameter-validation-failed"))
    (tmt/update-comment tweet-id comment-id parent-id text (json/parse-string media) user-data)))


(defn delete-comment
  [{:keys [tweet-id comment-id] :strs [parent-id]} user-data]
  (tmt/delete-comment tweet-id comment-id parent-id user-data))


(defn get-comment
  [{:keys [tweet-id] :strs [parent-id limit page]} user-data]
  (if  (some empty? [parent-id limit page])
    (throw (Exception. "parameter-validation-failed"))
    (tmt/get-comment tweet-id parent-id (Integer/parseInt limit) (Integer/parseInt page) user-data)))
