(ns graph-stuff.core
  (:use compojure.core)
  (:use cheshire.core)
  (:use ring.util.response)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io])
  (:gen-class))

(defn read-csv []
  "Reads running CSV, and returns a map of data"
  ;TODO: make this use https://dl.dropboxusercontent.com/u/2272759/running.csv
  (with-open [in-file (io/reader "/Users/colinwebb/Dropbox/Public/running.csv")]
    (doall
      (csv/read-csv in-file))))

(def running-headers
  ["start_time", "end_time", "distance", "distance_unit"])

(defn read-csv-with-headers []
  (map (fn [x] (zipmap running-headers x)) (read-csv)))

(defn index [req]
  {:body {:running (read-csv-with-headers)}})

(defroutes all-routes
           (GET "/" [] index))

(def app
  (-> (handler/api all-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
