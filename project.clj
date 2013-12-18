(defproject graph-stuff "0.1.0-SNAPSHOT"
  :description "a webapp to store stuff, and render it in graph form"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.csv "0.1.2"]
                 [ring "1.2.1"]
                 [ring/ring-json "0.2.0"]
                 [http-kit "2.1.13"]
                 [compojure "1.1.6"]
                 ]
  :ring {:handler graph-stuff.core/app}
  :plugins [[lein-ring "0.8.8"]]
  :main graph-stuff.core)
