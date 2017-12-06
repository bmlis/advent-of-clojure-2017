(ns advent-of-clojure-2017.day4
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def data (->> (io/resource "day4")
               slurp
               str/trim
               str/split-lines
               (map #(str/split % #" "))))

(defn no-repeats
  [seq]
  (= (count seq)
     (count (into [] (set seq)))))

(def result1 (reduce #(if (no-repeats %2) (+ % 1) %) 0 data))

(defn sort-seq-strings
  [seq]
  (map #(clojure.string/join "" (sort %)) seq))

(def data2 (map #(sort-seq-strings %) data))

(def result2 (reduce #(if (no-repeats %2) (+ % 1) %) 0 data2))
