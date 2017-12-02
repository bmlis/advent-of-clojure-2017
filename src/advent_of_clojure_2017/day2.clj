(ns advent-of-clojure-2017.day2
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def raw-data (slurp (io/resource "day2")))

(defn row-to-ints [row] (map #(Integer/parseInt %) row))

(defn rows-to-ints [rows] (map #(row-to-ints %) rows))

(defn parse-data
  [raw]
  (->> raw
       str/split-lines
       (map #(re-seq #"[^\t]+" %))
       rows-to-ints))

(def result1 (->> raw-data
                  parse-data
                  (reduce #(+ % (- (apply max %2) (apply min %2))) 0)))

(defn find-divisible
  [[head & tail]]
  (let [divisor (first (filter #(zero? (mod head %)) tail))]
    (if (or divisor (empty? tail))
      (/ head divisor)
      (recur tail))))

(def result2 (->> raw-data
                  parse-data
                  (map #(sort > %))
                  (reduce #(+ % (find-divisible %2)) 0)))
