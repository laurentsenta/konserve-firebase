(ns konserve-firebase.helpers
  (:require
    [konserve-firebase.test-env :refer [firebase-config]]
    [cljsjs.firebase]
    [goog.string :as gstring]))

(defn format
  [fmt & args]
  (apply gstring/format fmt args))

(defn now [] (.getTime (js/Date.)))

(defn make-test-id []
  (format "test-%011d-%05d" (now) (rand-int 10000)))

(defn make-db []
  ((.. js/firebase -database)))

(defn start-firebase! []
  (.initializeApp js/firebase
                  (clj->js firebase-config)))

(defn stop-firebase! [app then]
  (.then (.delete app)
         then))
