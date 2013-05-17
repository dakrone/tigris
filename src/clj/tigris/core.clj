(ns tigris.core
  (:import (org.writequit.tigris JSONStringEscapingInputStream)))

(defn str-escaping-input-stream
  "Given an InputStream to read a string, return an InputStream that will escape
  the string as it is being read."
  [is]
  (JSONStringEscapingInputStream. is))
