(ns kmet.reakt.test-utils
  "Test helpers shared by the reakt suite.")

(defn captured-err
  "Run F with *err* captured; returns `[result text]`."
  [f]
  (let [writer (java.io.StringWriter.)
        result (binding [*err* writer] (f))]
    [result (str writer)]))

(defn await-collected
  "True when PRED becomes true within ~5 s of forced-GC attempts (~50 ms
   apart). Slow tests only: it calls System/gc and sleeps between attempts."
  [pred]
  (loop [n 100]
    (cond
      (pred) true
      (zero? n) false
      :else (do (System/gc)
                (Thread/sleep 50)
                (recur (dec n))))))
