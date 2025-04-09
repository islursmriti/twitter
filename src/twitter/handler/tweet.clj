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
