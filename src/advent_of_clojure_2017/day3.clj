(ns advent-of-clojure-2017.day3
  (:gen-class)
  (:require
   [clojure.string :as str]))

(defn first-squared-bigger
  [val]
  (first (keep-indexed #(if (>= (* %2 %2) val)
                          {:index % :searched val :val %2})
                       (iterate #(+ % 2) 1))))

(defn abs [n] (max n (- n)))

(def left 1)
(def top 2)
(def right 3)
(def bottom 0)

(defn side-max
  [index squared seed]
  (- squared (* (- seed 1) index)))

(defn horizontal-side
  [index seed-squared seed]
  (let [end (side-max index seed-squared seed)]
    (range end (- end seed) -1)))

(defn vertical-side
  [index seed-squared seed]
  (let [end (side-max index seed-squared seed)]
    (range (- end 1) (- end seed -1) -1)))

(defn middle-index [seq] (- (/ (+ (count seq) 1) 2) 1))

(defn index [val seq] (first (keep-indexed #(if (= %2 val) %1) seq)))

(defn offset
  [side val]
  (abs (- (middle-index side)
          (index val side))))

(defn get-side
  [squared seed val]
  (if (>= val (side-max left squared seed))
    (horizontal-side bottom squared seed)
    (if (> val (side-max top squared seed))
      (vertical-side left squared seed)
      (if (>= val (side-max right squared seed))
        (horizontal-side top squared seed)
        (vertical-side right squared seed)))))

(defn distance
  [seed]
  (let [seed-val (:val seed)
        side (get-side (* seed-val seed-val) seed-val (:searched seed))]
    (println (str seed-val " " side seed))
    (+ (:index seed)
       (offset side (:searched seed)))))

(def result1 (->> 289326
                  first-squared-bigger
                  distance)) ;; 419


;;;;;; PART 2

(defn get-with-default
  [map key]
  (or (get map key) 0))

(def neighbours-offsets
  [[-1 1] [0 1] [1 1]
   [-1 0] [1 0]
   [-1 -1] [0 -1] [1 -1]])

(defn transition
  [[x y] [offset-x offset-y]]
  [(+ x offset-x) (+ y offset-y)])

(defn neighbours-coordinates
  [coordinates]
  (map #(transition coordinates %) neighbours-offsets))

(defn neighbours-sum
  [vals coordinates]
  (reduce #(+ % (get-with-default vals %2))
          0 (neighbours-coordinates coordinates)))

(def directions (cycle [[0 1] [-1 0] [0 -1] [1 0]]))

(defn rotate-left [seq] (cycle (take 4 (next seq))))

(defn rotate-right [seq] (cycle (take 4 (nthnext seq 3))))

(defn spiral
  [vals current compass searched]
  (let [change-direction (zero? (get-with-default vals (transition current (first compass))))
        current-value (neighbours-sum vals current)]
    (if (> current-value searched)
      current-value
      (recur (assoc vals current current-value)
             (if change-direction
               (transition current (first compass))
               (transition current (first (rotate-right compass))))
             (if change-direction
               (rotate-left compass)
               compass)
             searched))))

(def result2 (spiral {[0 0] 1} [1 0] directions 289326)) ;; 295229
