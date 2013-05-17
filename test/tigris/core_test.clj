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
  (doseq [s ["\u0000"
             "\u0007"
             "\b"
             "\t"
             "\n"
             "\f"
             "\r"
             "\u0019"
             " "
             "\""
             "\\"
             "/"
             "some UTF-8 text ಠ_ಠ."
             "\\\"things!\"\\"
             "unicode? \u0291"]]
    (is (= (json-map-for-str (slurp (stream-for s)))
           (json-for s)))))

(deftest t-large-string
  (doseq [line (line-seq (reader (resource "large-string.txt")))]
    (is (= (json-map-for-str (slurp (stream-for line)))
           (json-for line))
        (pr-str line))))

(deftest t-unicode
  (let [s (slurp (input-stream (file (resource "unicode.txt"))))]
    (is (= (json-map-for-str (slurp (stream-for s)))
           (json-for s)))))
