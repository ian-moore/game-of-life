(ns program
  (:require 
      [clojure.string]
      [gameoflife])
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
      (recur (read-line) (inc row-num) c))))

(defn render-cell [x y live-cells]
  (if (nil? (live-cells [x y])) "0" "1"))

(defn render [x y game-size final-cells]
  (print (render-cell x y final-cells))
  (when (= x (dec game-size)) (println))
  (def next-x (if (not= (inc x) game-size) (inc x) 0))
  (def next-y (if (= next-x 0) (inc y) y))
  (when (not= next-y game-size) (recur next-x next-y game-size final-cells)))

(defn run-game []
  (def first-input (read-line))
  (when (= first-input "STOP") (System.Environment/Exit 1))
  (def game-size (int first-input))
  (def num-ticks (int (read-line)))
  (def live-cells (read-cells (read-line) 0 []))
  (def final-cells (gameoflife/play game-size live-cells num-ticks))
  (render 0 0 game-size final-cells)
  (println "GO")
  (recur))

(defn -main [& args]
  (println "GO")
  (run-game))