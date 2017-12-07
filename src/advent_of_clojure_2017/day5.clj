(ns advent-of-clojure-2017.day5
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def data (->> (io/resource "day5")
               slurp
               str/trim
               str/split-lines
               (map read-string)
               (into [])))

(defn part1
  [seq index iterations]
  (if (>= index (count seq))
    iterations
    (recur (update seq index inc) (+ index (nth seq index)) (+ iterations 1))))

(def result1 (part1 data 0 0)) ;; 396086

(defn part2
  [seq index iterations]
  (if (>= index (count seq))
    iterations
    (recur (update seq index #(if (>= (nth seq index) 3)
                                (dec %)
                                (inc %)))
           (+ index (nth seq index))
           (+ iterations 1))))

(def result2 (part2 data 0 0)) ;; 28675390
