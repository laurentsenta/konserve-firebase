(ns running.testbook
  (:require
    [running.test-all]
    [running.tools :as tools]
    [cljs.test :as test :refer-macros [run-all-tests]]
    [figwheel-testbook.core :refer [testbook]]))

(defn ^:export reload-hook []
  (run-all-tests)
  (testbook "root"))

(defn ^:export init []
  (tools/setup-dev!)
  (reload-hook))
