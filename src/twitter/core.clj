(ns twitter.core
  (:require [ring.adapter.jetty :as jetty]
            [clojure.pprint]
            [twitter.routes :as routes]
            [twitter.db :as db]))


(defn -main
  [& args]
  (println "Starting server...")
  db/start-connection
  (jetty/run-jetty routes/app {:port 3000
                               :join? false}))
