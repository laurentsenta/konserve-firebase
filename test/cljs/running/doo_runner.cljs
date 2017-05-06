(ns running.doo-runner
  (:require
    [running.test-all]
    [doo.runner :refer-macros [doo-tests]]))

(enable-console-print!)

;; I'd rather use doo-tests-all but it seems
;; common for library maintainers to export their
;; tests, garbaging' client code.
(doo-tests
  'konserve-firebase.debug-test
  'konserve-firebase.core-test
  'konserve-firebase.store-test)

