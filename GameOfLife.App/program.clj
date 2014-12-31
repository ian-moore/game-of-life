(ns program
  (:require 
      [clojure.string]
      [gameoflife])
  (:import [gameoflife Cell])
  (:gen-class))

(defn parse-row [input row-num]
  (def row-cells (map int (clojure.string/split input #"(?!^|$)")))
  (doall (for [x (range (count row-cells)) 
    :when (= (nth row-cells x) 1)]
    [x row-num])))

(defn read-cells [input row-num cells]
  (if (= input "GO")
    (set cells)
    (let [c (concat cells (parse-row input row-num))]
      (read-cells (read-line) (inc row-num) c))))

(defn get-coordinates [size]
  (def nums (range size))
  (for [x nums y nums] [x y]))

(defn build-cells [size live-cells]
  (def nums (range size))
  (for [x nums y nums]
      (gameoflife/Cell. [x y] (contains? live-cells [x y]))))

(defn -main [& args]
  (println "GO")
  (def first-input (read-line))
  (when (= first-input "STOP") (System.Environment/Exit 1))
  (def game-size (int first-input))
  (def num-ticks (int (read-line)))
  (def live-cells (read-cells (read-line) 0 []))
  (def coordinates (get-coordinates game-size))
  ;(def cells (build-cells game-size live-cells))
  ;(println cells)
  ;(println live-cells)
  ;(println coordinates)
  ;(gameoflife/play coordinates live-cells num-ticks)
  (println (time (gameoflife/play game-size live-cells num-ticks)))
  (System.Console/ReadKey))