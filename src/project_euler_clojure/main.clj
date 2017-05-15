(ns project-euler-clojure.main
  (:gen-class)
  (:require [clojure.pprint :as pprint]
            [project-euler-clojure.core :as solutions]))

(defn -main [& _] (pprint/pprint solutions/euler-solutions))
