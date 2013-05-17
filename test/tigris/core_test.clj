(ns tigris.core-test
  (:require [clojure.java.io :refer :all]
            [clojure.test :refer :all]
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
  (let [s1 "this is \"some\" text with quotes."
        s2 "this is some \\ text with backslashes."
        s3 "some UTF-8 text ಠ_ಠ."
        s4 "\\\"things!\"\\"]
    (is (= (json-map-for-str (slurp (stream-for s1)))
           (json-for s1)))
    (is (= (json-map-for-str (slurp (stream-for s2)))
           (json-for s2)))
    (is (= (json-map-for-str (slurp (stream-for s3)))
           (json-for s3)))
    (is (= (json-map-for-str (slurp (stream-for s4)))
           (json-for s4)))))

(deftest t-large-string
  (doseq [line (line-seq (reader (resource "large-string.txt")))]
    (is (= (json-map-for-str (slurp (stream-for line)))
           (json-for line)))))
