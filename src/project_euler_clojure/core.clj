(ns project-euler-clojure.core
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [clojure.core.logic.fd :as fd]))

;;; Problem 7
(def primes
  "Infinite sequence of primes calculated via a seive of
  Eratosthenes."
  (->> (range)
       ;; drop 0; 1 is not prime.
       (drop 2)
       ;; remove every number that is divisible by some divisor d
       ;; (1 < d < n-1)
       (remove (fn [n] (some #(zero? (mod n %)) (range 2 (dec n)))))))

(def nth-prime (comp (partial nth primes) dec))

;;; Problem 8
(def problem-8-digits
  [7 3 1 6 7 1 7 6 5 3 1 3 3 0 6 2 4 9 1 9 2 2 5 1 1 9 6 7 4 4 2 6 5 7
   4 7 4 2 3 5 5 3 4 9 1 9 4 9 3 4 9 6 9 8 3 5 2 0 3 1 2 7 7 4 5 0 6 3
   2 6 2 3 9 5 7 8 3 1 8 0 1 6 9 8 4 8 0 1 8 6 9 4 7 8 8 5 1 8 4 3 8 5
   8 6 1 5 6 0 7 8 9 1 1 2 9 4 9 4 9 5 4 5 9 5 0 1 7 3 7 9 5 8 3 3 1 9
   5 2 8 5 3 2 0 8 8 0 5 5 1 1 1 2 5 4 0 6 9 8 7 4 7 1 5 8 5 2 3 8 6 3
   0 5 0 7 1 5 6 9 3 2 9 0 9 6 3 2 9 5 2 2 7 4 4 3 0 4 3 5 5 7 6 6 8 9
   6 6 4 8 9 5 0 4 4 5 2 4 4 5 2 3 1 6 1 7 3 1 8 5 6 4 0 3 0 9 8 7 1 1
   1 2 1 7 2 2 3 8 3 1 1 3 6 2 2 2 9 8 9 3 4 2 3 3 8 0 3 0 8 1 3 5 3 3
   6 2 7 6 6 1 4 2 8 2 8 0 6 4 4 4 4 8 6 6 4 5 2 3 8 7 4 9 3 0 3 5 8 9
   0 7 2 9 6 2 9 0 4 9 1 5 6 0 4 4 0 7 7 2 3 9 0 7 1 3 8 1 0 5 1 5 8 5
   9 3 0 7 9 6 0 8 6 6 7 0 1 7 2 4 2 7 1 2 1 8 8 3 9 9 8 7 9 7 9 0 8 7
   9 2 2 7 4 9 2 1 9 0 1 6 9 9 7 2 0 8 8 8 0 9 3 7 7 6 6 5 7 2 7 3 3 3
   0 0 1 0 5 3 3 6 7 8 8 1 2 2 0 2 3 5 4 2 1 8 0 9 7 5 1 2 5 4 5 4 0 5
   9 4 7 5 2 2 4 3 5 2 5 8 4 9 0 7 7 1 1 6 7 0 5 5 6 0 1 3 6 0 4 8 3 9
   5 8 6 4 4 6 7 0 6 3 2 4 4 1 5 7 2 2 1 5 5 3 9 7 5 3 6 9 7 8 1 7 9 7
   7 8 4 6 1 7 4 0 6 4 9 5 5 1 4 9 2 9 0 8 6 2 5 6 9 3 2 1 9 7 8 4 6 8
   6 2 2 4 8 2 8 3 9 7 2 2 4 1 3 7 5 6 5 7 0 5 6 0 5 7 4 9 0 2 6 1 4 0
   7 9 7 2 9 6 8 6 5 2 4 1 4 5 3 5 1 0 0 4 7 4 8 2 1 6 6 3 7 0 4 8 4 4
   0 3 1 9 9 8 9 0 0 0 8 8 9 5 2 4 3 4 5 0 6 5 8 5 4 1 2 2 7 5 8 8 6 6
   6 8 8 1 1 6 4 2 7 1 7 1 4 7 9 9 2 4 4 4 2 9 2 8 2 3 0 8 6 3 4 6 5 6
   7 4 8 1 3 9 1 9 1 2 3 1 6 2 8 2 4 5 8 6 1 7 8 6 6 4 5 8 3 5 9 1 2 4
   5 6 6 5 2 9 4 7 6 5 4 5 6 8 2 8 4 8 9 1 2 8 8 3 1 4 2 6 0 7 6 9 0 0
   4 2 2 4 2 1 9 0 2 2 6 7 1 0 5 5 6 2 6 3 2 1 1 1 1 1 0 9 3 7 0 5 4 4
   2 1 7 5 0 6 9 4 1 6 5 8 9 6 0 4 0 8 0 7 1 9 8 4 0 3 8 5 0 9 6 2 4 5
   5 4 4 4 3 6 2 9 8 1 2 3 0 9 8 7 8 7 9 9 2 7 2 4 4 2 8 4 9 0 9 1 8 8
   8 4 5 8 0 1 5 6 1 6 6 0 9 7 9 1 9 1 3 3 8 7 5 4 9 9 2 0 0 5 2 4 0 6
   3 6 8 9 9 1 2 5 6 0 7 1 7 6 0 6 0 5 8 8 6 1 1 6 4 6 7 1 0 9 4 0 5 0
   7 7 5 4 1 0 0 2 2 5 6 9 8 3 1 5 5 2 0 0 0 5 5 9 3 5 7 2 9 7 2 5 7 1
   6 3 6 2 6 9 5 6 1 8 8 2 6 7 0 4 2 8 2 5 2 4 8 3 6 0 0 8 2 3 2 5 7 5
   3 0 4 2 0 7 5 2 9 6 3 4 5 0])

(defn max-subseqence-product
  "Find the n consecutive digits in list of digits coll that have the
  highest product."
  [coll n]
  (loop [i                    0
         best-selection-index 0]
    (let [best-selection (->> coll (drop best-selection-index) (take n))
          selection      (->> coll (drop i) (take n))]
      ;; slide a window through coll until the window goes off the
      ;; end. best-selection-index tracks the sequence of n digits
      ;; with the highest product so far.
      (if (= (count selection) n)
        (recur (inc i)
               (if (< (reduce * best-selection) (reduce * selection))
                 i
                 best-selection-index))
        {:index   best-selection-index
         :digits  best-selection
         :product (reduce * best-selection)}))))

;;; Problem 9

;; This is a bit of a cheat. Problem 9 has a finite search space and
;; its contraints are easily expressed via core.logic's numeric finite
;; domain operators. So instead of specifying an algorithm, we specify
;; the appropriate constraints for a, b, and c, use core.logic to
;; find a solution, then take the product.
(defn problem-9
  []
  (->>
   (run 1 [a b c]
     ;; a, b, and c are natural numbers. Because they represent side
     ;; lengths of a triangle, they must be at least one. Because
     ;; their sum is 1000, none may be greater than 1000.
     (fd/in a b c (fd/interval 1 1000))
     ;; a, b, and c must form a Pythagorean triplet.
     (fd/eq
      (= (+ (* a a) (* b b)) (* c c))
      ;; a, b, and c sum to 1000
      (= 1000 (+ a b c))))
   ;; get the first (only) solution.
   first
   ;; find the product.
   (reduce *)))

;;; Problem 15

;; The number of paths through the lattice is neatly solved using
;; dynamic programming. The paths through an m by n grid can be
;; expressed using a recurrence relation:

;; T(0, 0) = 1 (This is analogous to how 0! = 1 and minimizes the
;; number of base cases)

;; T(m, 0) = T(m - 1, 0)

;; T(0, n) = T(0, n - 1)

;; T(m, n) = T(m - 1, n) + T(m, n - 1)

;; Dynamic programming requires the use of a data structure that
;; memoizes the results of the recurrence relation. Here, we offload
;; that work to Clojure's memoize, so that we do not have to track the
;; memoization data structure ourselves.

(declare count-lattice-paths)

(defn- count-lattice-paths-recursive
  [m n]
  (cond
    (= [0 0] [m n]) 1
    (zero? m)       (count-lattice-paths m (dec n))
    (zero? n)       (count-lattice-paths (dec m) n)
    :else           (+ (count-lattice-paths (dec m) n)
                       (count-lattice-paths  m (dec n)))))

(def count-lattice-paths (memoize count-lattice-paths-recursive))
