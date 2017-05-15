(ns project-euler-clojure.main
  (:gen-class)
  (:require [clojure.pprint :as pprint]
            [project-euler-clojure.core :as solutions]))

(defn -main [& _]
  (pprint/pprint {7  (solutions/nth-prime 10001)
                  8  (solutions/max-subseqence-product
                      solutions/problem-8-digits 13)
                  9  (solutions/problem-9)
                  15 (solutions/count-lattice-paths 20 20)}))
