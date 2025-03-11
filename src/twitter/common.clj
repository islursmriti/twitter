(ns twitter.common
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.tools.logging :as log]
   [cheshire.core :as json])
  (:import
   (java.io PushbackReader)))


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
      (log/error "Could not find resource file"))))


(def config (load-config))


;; Function to make a json response
(defn json-response [status message]
  {:status status :body message})
