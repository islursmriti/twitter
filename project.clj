(defproject twitter "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.9.5"]
                 [ring/ring-jetty-adapter "1.9.5"]
                 [compojure "1.6.2"]
                 [com.novemberain/monger "3.5.0"]
                 [buddy/buddy-auth "2.2.0"]
                 [org.clojure/tools.logging "1.3.0"]
                 [slingshot "0.12.2"]
                 [ring/ring-json "0.5.1"]]
  :main ^:skip-aot twitter.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
:resource-paths ["resources"]
