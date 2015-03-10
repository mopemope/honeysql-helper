(defproject org.clojars.mopemope/honeysql-helper "0.1.0-SNAPSHOT"
  :description "Simple Honey SQL Helper"
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [honeysql "0.4.3"]
                 [com.taoensso/timbre "3.4.0"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [environ "1.0.0"]]
  :min-lein-version "2.0.0"
  :plugins [[lein-environ "1.0.0"]
            [lein-ancient "0.6.4"]
            [ragtime/ragtime.lein "0.3.8"]])
