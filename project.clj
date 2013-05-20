(defproject tigris "0.1.1"
  :description "Stream-to-stream JSON string encoding"
  :url "https://github.com/dakrone/tigris"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:dependencies [[cheshire "5.1.1"]]}}
  :source-paths ["src/clj"]
  :java-source-paths ["src/java"])
