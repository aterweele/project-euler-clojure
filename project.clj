(defproject project-euler-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.logic "0.8.11"]
                 ;; representing factorizations. snapshot because I
                 ;; ran into a bug on the stable version.
                 [org.clojars.achim/multiset "0.1.1-SNAPSHOT"]
                 ;; for "map on maps"
                 [com.rpl/specter "1.0.1"]])
