# Tigris

Tigris provides a stream for escaping json strings as they're being
read from a different stream. So stream-to-stream string encoding.

Only a single string though. It's pretty narrow-use.

It's written in Java with a tiny Clojure wrapper because the
[Clojure version](https://gist.github.com/dakrone/5577106) is pretty
complicated (and buggy!). And because testing Java sucks.

## Usage

In project.clj

```clojure
[tigris "0.1.0"]
```

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
