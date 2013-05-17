(ns tigris.core-test
  (:require [clojure.test :refer :all]
            [cheshire.core :refer :all]
            [tigris.core :refer :all])
  (:import (java.io ByteArrayInputStream)))

(defn stream-for
  [s]
  {:pre [(string? s)]}
  (str-escaping-input-stream (ByteArrayInputStream. (.getBytes s))))

(defn json-for
  [s]
  {:pre [(string? s)]}
  (encode {:thing s}))

(defn json-map-for-str
  "helper for generating a valid json map for a string"
  [s]
  (str "{\"thing\":\"" s "\"}"))

(deftest t-basic
  (let [s1 "this is \"some\" text with quotes."]
    (is (= (json-map-for-str (slurp (stream-for s1)))
           (json-for s1)))))
