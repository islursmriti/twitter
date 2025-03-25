(ns twitter.db
  (:require [monger.core :as mg]
            [monger.db :as mg-db]
            [monger.collection :as mc]
            [monger.operators :as mgo]
            [monger.query :as mq]
            [twitter.common :as comm]
            [slingshot.slingshot :refer [try+ throw+]]
            [clojure.tools.logging :as log]))


(defn start-connection []
  ;; Connect to the MongoDB server (default localhost:27017)
  (mg/connect [{:host (:mongo-host comm/config), :port (:mongo-port comm/config)}]))


(def db (mg/get-db (start-connection) (:mongo-db comm/config)))


(defn insert [coll-name query]
  (mc/insert db coll-name query))


(defn find-one [coll-name query]
  (mc/find-one db coll-name query))


(defn find-many [coll-name query]
  (mc/find db coll-name query))


(defn update-query [coll-name condition query]
  (mc/update db coll-name condition {mgo/$set query}))


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

