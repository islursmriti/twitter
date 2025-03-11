(ns twitter.core
  (:require [ring.adapter.jetty :as jetty]
            [clojure.pprint]
            [twitter.routes :as routes]
            [twitter.db :as db]
            [clojure.tools.logging :as log]))


(defn -main
  [& args]
  (println "Starting server...")
  (try
    ;; Try to start the connection
    (db/start-connection)
    (jetty/run-jetty routes/app-with-middleware {:port 3000 :join? false})
    (catch Exception e
      ;; Catch and handle any connection or server start errors
      (log/error "Error starting the server:" (.getMessage e)))))
