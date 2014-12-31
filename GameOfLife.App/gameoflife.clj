(ns gameoflife)

(defrecord Cell [position neighbors])

(defn- build-neighbors [x y]
  (for [new-x [(dec x) x (inc x)] new-y [(dec y) y (inc y)]
    :when (or (not= new-x x) (not= new-y y))] 
    [new-x new-y]))

(defn- build-cells [size]
  (def nums (range size))
  (for [x nums y nums]
    (Cell. [x y] (build-neighbors x y))))

(defn- check-neighbors [coordinates live-cells]
  (count (remove nil? (map live-cells coordinates))))

(defn- alive? [currently-alive count]
  (if currently-alive
    (and (>= count 2) (<= count 3))
    (= count 3)))

; "I want more life, father" - Batty
(defn- calc-life [cells live-cells]
  (for [c cells]
    (let [neighbor-count (check-neighbors (:neighbors c) live-cells)
          position (:position c)]
      (when (alive? (-> position live-cells nil? not) neighbor-count) position))))

(defn- tick [cells live-cells tick-count ticks]
  (if (> tick-count ticks)
    live-cells
    (let [new-cells (calc-life cells live-cells)]
      (tick cells (set new-cells) (inc tick-count) ticks))))

(defn play [game-size live-cells ticks]
  (tick (build-cells game-size) live-cells 1 ticks))

