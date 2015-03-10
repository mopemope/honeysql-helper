(ns honeysql-helper.core
  (:require [honeysql.core :as sql]
            [honeysql.helpers :refer :all]
            [clojure.java.jdbc :as jdbc]
            [taoensso.timbre :as timbre]))

(timbre/refer-timbre)

(defn- get-quot [proto]
  (condp = proto
    "mysql" :mysql
    "sqlserver" :sqlserver
    :ansi))

(defn execute-handler [db sql-and-params]
  (first (jdbc/execute! db sql-and-params)))

(defn insert-handler [db [statement & params]]
  (jdbc/db-do-prepared-return-keys db statement params))

(def ^:dynamic *prepare* identity)
(def ^:dynamic *transform* identity)
(def ^:dynamic *intercept* (fn [& args] nil))

(defn- query* [db sqlmap type params]
  (let [sqlmap* (*prepare* sqlmap)
        s (sql/format sqlmap* :params params
                      :quoting (get-quot (:subprotocol db)))]
    (debug (str "SQLMap: " sqlmap*))
    (debug (str "SQL: " s))
    (if-let [result (*intercept* sqlmap*)]
      result
      (condp = type
        :insert (insert-handler db s)
        :exec (execute-handler db s)
        (*transform* (jdbc/query db s)))
      )
    ))

(defn query [db sqlmap & {:keys [params] :or {params {}}}]
  (query* db sqlmap :query params))

(defn insert! [db sqlmap & {:keys [params] :or {params {}}}]
  (query* db sqlmap :insert params))

(defn execute! [db sqlmap & {:keys [params] :or {params {}}}]
  (query* db sqlmap :exec params))

