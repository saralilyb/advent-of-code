(ns net.jtth.aoc.impl
  (:import (java.io FileFilter)))

;; all Clojure fns are callable
(defn create-hello-fn [] (fn [] "hello from clojure"))

;; implement Java interface with reify
(defn create-never-filter
  []
  (reify FileFilter (accept [_ _] false)))

;; extend Java classes with proxy
(defn create-timestamped-object
  []
  (let [s (format "<object created %s>" (str (java.util.Date.)))]
    (proxy [Object] [] (toString [] s))))
