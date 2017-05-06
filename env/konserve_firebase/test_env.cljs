(ns konserve-firebase.test-env
  (:require-macros
    [konserve-firebase.test.helpers :refer [get-env]]))

;; Go to your project overview and retrieve the web configuration
;; https://console.firebase.google.com/project/my-project/overview
;; This file is gitignore for obvious reasons.
(def firebase-config
  {:apiKey            (get-env "FIREBASE_API_KEY" "AI...")
   :authDomain        (get-env "FIREBASE_AUTH_DOMAIN" "my-project.firebaseapp.com")
   :databaseURL       (get-env "FIREBASE_DATABASE_URL" "https://my-project.firebaseio.com")
   :storageBucket     (get-env "FIREBASE_STORAGE_BUCKET" "my-project.appspot.com")
   :messagingSenderId (get-env "FIREBASE_MESSAGING_SEND_ID" "16...")})


;; This is the prefix for every paths used during testings
;; Setup your firebase database security rules so that this
;; is readable by non-auth'd user.
(def prefix
  "tests-konserve-firebase")
