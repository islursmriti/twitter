(ns twitter.routes
  (:require [compojure.core :as compojure]
            [compojure.route :as compojure-route]))


(compojure/defroutes app
  (compojure/GET "/" [] "Hello World")
  (compojure-route/not-found "Page not found"))
