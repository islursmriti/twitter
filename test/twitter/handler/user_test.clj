(ns twitter.handler.user-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [twitter.authentication :refer [decode-jwt]]
   [twitter.handler.user :as thu]))


(deftest register
  (testing "register route"
    (let [token (thu/register {"username" "test" "email" "test@gmail.com" "password" "islur"
                               "profile" "{\"bio\": \"i am test user\", \"profile-image\": \"abc.jpg\"}"})]
      (is (= (:username (decode-jwt token)) "test")))))


(deftest login
  (testing "login route"
    (let [token (thu/login {"username" "test" "email" "test@gmail.com" "password" "islur"})]
      (is (= (:username (decode-jwt token)) "test")))))


(deftest get-user
  (testing "get user route"
    (let [result (thu/get-user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"})]
      (is (= (get result "username") "test")))))


(deftest update-user
  (testing "update user route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}]
      (is (= (.isUpdateOfExisting (thu/update-user user {"email" "test1@gmail.com"})) true))
      (is (= (.isUpdateOfExisting (thu/update-user user {"email" "test@gmail.com"
                                                         "profile" "{\"bio\": \"hi\", \"profile-image\": \"abc.png\"}"})) true)))))


(deftest delete-user
  (testing "delete user route"
    (is (= (.isUpdateOfExisting (thu/delete-user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}))
           true))))
