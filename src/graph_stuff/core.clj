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

(defn read-csv
  "Reads CSV, and returns a map of data"
  [filename]
  (with-open [in-file (io/reader filename)]
    (doall
      (csv/read-csv in-file))))

(defn read-csv-with-headers [headers filename]
  (map (fn [x] (zipmap headers x)) (read-csv filename)))

(def running-headers
  ["start_time", "end_time", "distance", "distance_unit"])

(defn index [req]
  {:body {:running (read-csv-with-headers running-headers "https://dl.dropboxusercontent.com/u/2272759/running.csv")}})

(defroutes all-routes
           (GET "/" [] index))

(defn wrap-cors
  "Allow requests from all origins"
  [handler]
  (fn [request]
    (let [response (handler request)]
      (update-in response
                 [:headers "Access-Control-Allow-Origin"]
                 (fn [_] "*")))))

(def app
  (-> (handler/api all-routes)
      (wrap-cors)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
