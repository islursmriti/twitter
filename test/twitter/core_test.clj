(ns twitter.core-test
  (:require [clojure.test :refer [deftest testing]]
            [twitter.core :as core]))


(deftest test-1
  (testing "Problem 42- Factorial Fun"
    (try (core/-main "start")
         (finally (println "worked")))))
