# Tigris

Tigris provides a stream for escaping json strings as they're being
read from a different stream. So stream-to-stream string encoding.

Only strings though.

## Usage

Tigris provides one method: `str-escaping-input-stream`.

```clojure
(ns myns.foo
  (:require [clojure.java.io :refer :all]
            [tigris.core :refer :all]))

(def f (input-stream (file "/tmp/largestring.txt")))

(def json-stream (str-escaping-input-stream f))

(slurp json-stream)
...
```

## License

Copyright © 2013 Matthew Lee Hinman

Distributed under the Eclipse Public License, the same as Clojure.
