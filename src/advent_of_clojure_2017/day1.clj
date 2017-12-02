(ns advent-of-clojure-2017.day1
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def data (->> (io/resource "day1")
                   slurp
                   str/trim
                   (map #(Character/digit % 10))))

(defn consecutive-pairs
  [seq]
  (partition 2 1 [(first seq)] seq))

(defn first-if-equal
  ([digit1 digit2]
   (if (= digit1 digit2)
     digit1
     0)))

(def result1 (->> data
                  consecutive-pairs
                  (reduce #(+ %1 (apply first-if-equal %2)) 0)))

(defn split-in-two
  [seq]
  (partition (/ (count seq) 2) seq))


(def result2 (->> data
                  split-in-two
                  (apply map first-if-equal)
                  (reduce #(+ % (* 2 %2)) 0)))
