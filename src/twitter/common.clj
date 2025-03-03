(ns twitter.common
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:import (java.io PushbackReader)))


;; Function to load and parse the default.clj file
(defn load-config []
  (let [env-file (io/resource "env/default.clj")]
    (if env-file
      (with-open [rdr (io/reader env-file)]
        (let [config (edn/read (PushbackReader. rdr))]
          ;; Now the config map is available
          (println "Config: " config)
          ;; Return the config map
          config))
      (println "Could not find resource file"))))


(def config (load-config))
