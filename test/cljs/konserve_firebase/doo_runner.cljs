(ns konserve-firebase.doo-runner
  (:require
    ;; Import all tests here:
    [konserve-firebase.core-test]
    [konserve-firebase.store-test]

    [doo.runner :refer-macros [doo-tests]]))

;; I'd rather use doo-tests-all but it seems
;; common for library maintainers to export their
;; tests, garbaging' client code.
(doo-tests
  'konserve-firebase.core-test
  'konserve-firebase.store-test)

