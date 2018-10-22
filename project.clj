(defproject tigris "0.1.2"
  :description "Stream-to-stream JSON string encoding"
  :url "https://github.com/dakrone/tigris"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies []
  :profiles {:dev {:dependencies [[cheshire "5.1.1"]
                                  [org.clojure/clojure "1.8.0"]]}}
  :source-paths ["src/clj"]
  :java-source-paths ["src/java"])
