(ns gameoflife)

(defn- build-cells [size]
  (let [nums (range size)]
    (for [x nums y nums] [x y])))

(defn- check-neighbors [coordinates live-cells]
  (count (remove nil? (map live-cells coordinates))))

(defn- alive? [currently-alive count]
  (if currently-alive
    (and (>= count 2) (<= count 3))
    (= count 3)))

(defn- neighbors [x game-size]
  [(if (not= (dec x) -1) (dec x) (dec game-size)) x (if (not= (inc x) game-size) (inc x) 0)])

(defn- get-neighbors [[x y] game-size]
  (for [new-x (neighbors x game-size) new-y (neighbors y game-size)
    :when (or (not= new-x x) (not= new-y y))]
    [new-x new-y]))

; "I want more life, father" - Batty
(defn- calc-life [cells live-cells game-size]
  (for [c cells]
    (let [neighbor-cells (get-neighbors c game-size)
          neighbor-count (check-neighbors neighbor-cells live-cells)]
      (when (alive? (-> c live-cells nil? not) neighbor-count) c))))

(defn- tick [cells live-cells tick-count ticks game-size]
  (if (> tick-count ticks)
    live-cells
    (let [new-cells (calc-life cells live-cells game-size)]
      (recur cells (set new-cells) (inc tick-count) ticks game-size))))

(defn play [game-size live-cells ticks]
  (tick (build-cells game-size) live-cells 1 ticks game-size))

