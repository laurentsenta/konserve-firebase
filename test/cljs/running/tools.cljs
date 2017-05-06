(ns running.tools
  (:require
    [devtools.core :as devtools]))

(defn setup-dev! []
  (enable-console-print!)
  (devtools/install! [:hints :async :formatters]))
