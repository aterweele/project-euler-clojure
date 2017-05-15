(ns project-euler-clojure.core-test
  (:require [clojure.test :refer :all]
            [project-euler-clojure.core :refer :all]))

(deftest problem-7-test
  (testing "6th prime"
    (is (= 13 (nth-prime 6)))))

(deftest problem-8-test
  (testing "4 digits with the greatest product"
    (let [{:keys [digits product]} (max-subseqence-product problem-8-digits 4)]
      (are [expected actual] (= expected actual)
        [9 9 8 9] digits
        5832      product))))

(deftest problem-15-test
  (testing "2x2 lattice"
    (is (= 6 (count-lattice-paths 2 2)))))
