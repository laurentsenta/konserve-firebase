;; Sometime cljs/doo tests will break down
;; for various, cryptic reasons.
;;
;; This is a dummy test namespace to help debugging
;; without having to create and include a new namespace
;; every time.
(ns konserve-firebase.debug-test
  (:require-macros
    [cljs.test :refer [deftest testing is async use-fixtures]]))

(deftest ta
  (is (= (+ 1 1) 2)))

(deftest tb
  (is (= (+ 1 2) 3)))

