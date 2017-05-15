(ns project-euler-clojure.core
  ;; TODO: I might not be using multiset and/or specter
  (:require [clojure.core.logic :refer :all]
            [clojure.core.logic.fd :as fd]
            [com.rpl.specter :as specter]
            [multiset.core :as ms]))

;;; Problem 7
(def primes
  (->> (range)
       ;; drop 0; 1 is not prime.
       (drop 2)
       (remove (fn [n] (some #(zero? (mod n %)) (range 2 (dec n)))))))

(def nth-prime (comp (partial nth primes) dec))

(def problem-7 (nth-prime 10001))

#_
(def digit-factors
  (->> {0 [0]
        1 [1]
        2 [2]
        3 [3]
        4 [2 2]
        5 [5]
        6 [2 3]
        7 [7]
        8 [2 2 2]
        9 [3 3]}
       (specter/transform [specter/MAP-VALS] (partial into (ms/multiset)))))

#_
(defn product-less?
  "True iff the product of the elements of left is less than the
  product of right."
  [left right]
  (let [factor                       (partial mapcat digit-factors)
        [left-factors right-factors] (map factor [left right])
        common-factors               (ms/intersect left-factors right-factors)
        [unique-left-factors
         unique-right-factors]       (map #(ms/minus % common-factors)
                                          [left-factors right-factors])]
    (or (some zero? left-factors)
        (< (reduce * unique-left-factors) (reduce * unique-right-factors)))))

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
      (if (= (count selection) n)
        (recur (inc i)
               (if (< (reduce * best-selection) (reduce * selection))
                 i
                 best-selection-index))
        {:index   best-selection-index
         :digits  best-selection
         :product (reduce * best-selection)}))))

(def problem-8 (max-subseqence-product problem-8-digits 13))

;; Problem 9
(def problem-9
  (->>
   (run 1 [a b c]
     (fd/in a b c (fd/interval 1 1000))
     (fd/eq
      (= (+ (* a a) (* b b)) (* c c))
      (= 1000 (+ a b c))))
   first
   (reduce *)))

;;; Problem 12
(def nonzero-naturals (-> (range) rest))
(def triangulars (map #(reduce + (range 1 (inc %))) nonzero-naturals))

(defn divisible-by? [n d] (= 0 (mod n d)))

(defn count-divisors
  [n]
  (->> (range 1 (inc n))
       (filter #(divisible-by? n %))
       count))

(defn first-divisible-triangular
  "What is the first triangular number with more than c divisors?"
  [c]
  (some #(when (< c (count-divisors %)) %) triangulars))

;;; Problem 15
(declare count-lattice-paths)

(defn- count-lattice-paths-recursive
  [m n]
  (cond
    (= [0 0] [m n]) 1
    (zero? m) (count-lattice-paths m (dec n))
    (zero? n) (count-lattice-paths (dec m) n)
    :else (+ (count-lattice-paths (dec m) n) (count-lattice-paths  m (dec n)))))

(def count-lattice-paths (memoize count-lattice-paths-recursive))

(def problem-15 (count-lattice-paths 20 20))
