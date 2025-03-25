(ns twitter.handler.tweet
  (:require [twitter.model.tweet :as tmt]
            [cheshire.core :as json]))


(defn post-tweet
  [{:strs [text media]} user-data]
  (if (every? empty? [text media])
    (throw (Exception. "parameter-validation-failed"))
    (tmt/post-tweet text (json/parse-string media) user-data)))
