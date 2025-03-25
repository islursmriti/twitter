{:mongo-host "127.0.0.1"
 :mongo-port 27017
 :mongo-db "twitter"
 :mongo-coll-usr "users"
 :mongo-coll-tweets "tweets"
 :mongo-coll-likes "likes"
 :mongo-coll-comments "comments"
 :uuid-prefix-user "user_"
 :uuid-prefix-tweet "tweet_"
 :uuid-prefix-comment "comment_"
 :secret-key "helpshift-twitter-project"
 :token-exp-time  86400000;(* 60 60 1000 24)-24 hrs | (* 1000 60) - for 1 min
 }