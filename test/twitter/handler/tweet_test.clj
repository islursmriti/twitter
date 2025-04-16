(ns twitter.handler.tweet-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [twitter.handler.tweet :as tht]))


(deftest post-tweet
  (testing "post tweet route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id (tht/post-tweet {"text" "Hi from test" "media" "[\"image.jpg\"]"} user)]
      (is (not-empty tweet-id)))))


(deftest get-tweet-data
  (testing "get tweet route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"
          tweet-data (tht/get-tweet-data tweet-id user)]
      (is (= (get-in tweet-data ["body" "text"]) "Hi from test"))
      (is (= (get tweet-data "id") tweet-id)))))


(deftest update-tweet
  (testing "update tweet route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"]
      (is (= (.isUpdateOfExisting (tht/update-tweet {:tweet-id tweet-id "text" "Hi update from test"} user)) true)))))


(deftest delete-tweet
  (testing "delete tweet route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"]
      (is (= (.getN (tht/delete-tweet tweet-id user)) 1)))))


(deftest post-comment
  (testing "post comment route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"
          parent-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"
          comment-id (tht/post-comment {:tweet-id tweet-id "parent-id" parent-id
                                        "text" "Hi test tweet" "media" "[\"image.jpg\", \"hi.png\"]"} user)]
      (is (not-empty comment-id)))))


(deftest get-comment-data
  (testing "get comment data route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"
          comment-id "comment_43224c0e-c5df-4f9e-be3f-a811c0d8b93c"
          parent-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"
          comment-data (tht/get-comment-data {:tweet-id tweet-id :comment-id comment-id "parent-id" parent-id}
                                             user)]
      (is (= (get-in comment-data ["body" "text"]) "Hi test tweet")))))


(deftest update-comment
  (testing "update comment route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"
          comment-id "comment_43224c0e-c5df-4f9e-be3f-a811c0d8b93c"
          parent-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"]
      (is (= (.isUpdateOfExisting (tht/update-comment {:tweet-id tweet-id :comment-id comment-id
                                                       "parent-id" parent-id "text" "Hi update from test comment"} user)) true)))))


(deftest delete-comment
  (testing "delete commment route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"
          comment-id "comment_43224c0e-c5df-4f9e-be3f-a811c0d8b93c"
          parent-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"]
      (is (= (.getN (tht/delete-comment {:tweet-id tweet-id :comment-id comment-id "parent-id" parent-id} user)) 1)))))


(deftest get-comment
  (testing "get comment route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_606ba4f2-772a-4d22-9482-a0e94558ef60"
          parent-id "tweet_606ba4f2-772a-4d22-9482-a0e94558ef60"
          limit "3"
          page "1"
          comment-data (tht/get-comment {:tweet-id tweet-id "parent-id" parent-id "limit" limit "page" page}
                                        user)]
      (is (<= (count comment-data) (Integer/parseInt limit))))))


(deftest like-tweet
  (testing "like tweet route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"
          parent-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"]
      (is (some? (tht/like-tweet {:tweet-id tweet-id "parent-id" parent-id} user))))))


(deftest unlike-tweet
  (testing "like tweet route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"
          parent-id "tweet_1bac1b6d-30fe-41df-aef0-ada1cc2b2311"]
      (is (= (.getN (tht/unlike-tweet {:tweet-id tweet-id "parent-id" parent-id} user)) 1)))))


(deftest get-likes
  (testing "like tweet route"
    (let [user {:id "user_0cb38e43-cad0-4c55-84d2-e8317146471f" :username "test"}
          tweet-id "tweet_8d93a26d-366b-46cf-b559-eedb97f38128"
          parent-id "comment_f5c02244-ac28-400c-a682-ed1be4edb18c"
          limit "2"
          page "1"]
      (is (<= (count (tht/get-likes {:tweet-id tweet-id "parent-id" parent-id
                                     "limit" limit "page" page} user)) (Integer/parseInt limit))))))
