(ns twitter.db
  (:require [monger.core :as mg]
            [monger.db :as mg-db]
            [monger.query :as mq]
            [twitter.common :as comm]
            [slingshot.slingshot :refer [try+ throw+]]
            [clojure.tools.logging :as log]))


(println "Mongo host from comm/config: " (:mongo-host comm/config))


(defn start-connection []
  ;; Connect to the MongoDB server (default localhost:27017)
  (mg/connect [{:host (:mongo-host comm/config), :port (:mongo-port comm/config)}]))


;test
(comment (def db-connect (start-connection))
         (defn get-db []
           (mg/get-db db-connect (:mongo-db comm/config)))
         (defn list-collections []
           (let [db (get-db)]
             (mg-db/get-collection-names db)))


;; lists collections from the 'twitter' db
         (println "Collections in twitter DB:" (list-collections))
         ;; disconnect from MongoDB after you are done
         (mg/disconnect db-connect))

