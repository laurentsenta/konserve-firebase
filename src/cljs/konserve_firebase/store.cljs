(ns konserve-firebase.store
  (:require
    [incognito.edn :refer [read-string-safe]]
    [konserve.core :as k]
    [konserve-firebase.core :as firebase]
    [konserve.protocols :refer [PEDNAsyncKeyValueStore -exists? -get-in -update-in
                                PJSONAsyncKeyValueStore -jget-in -jassoc-in -jupdate-in
                                PBinaryAsyncKeyValueStore -bget -bassoc
                                PStoreSerializer -serialize -deserialize]]
    [cljs.core.async :as async :refer [take! <! >! put! close! chan poll!]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))


(defn -prefixed [prefix key-vec]
  (->> key-vec (concat prefix) (into [])))

(defn with-xf [xf chan]
  (let [out-chan (async/chan)]
    (async/pipeline 1 out-chan xf chan)
    out-chan))

(defrecord FirebaseDBStore [firebase-db prefix locks]
  PEDNAsyncKeyValueStore
  (-exists? [this key]
    (go (-> this (-get-in [key]) nil? not)))
  (-get-in [this key-vec]
    (->> (firebase/get-in! firebase-db (-prefixed prefix key-vec))
         (with-xf (map firebase/with-nil))))
  (-update-in [this key-vec up-fn]
    (firebase/update-in! firebase-db (-prefixed prefix key-vec) up-fn))
  (-dissoc [this key]
    (firebase/dissoc-in! firebase-db (-prefixed prefix [key]))))

(defn new-firebasedb-store [firebase-db & {:keys [prefix] :or {prefix []}}]
  (let [store (map->FirebaseDBStore {:firebase-db firebase-db
                                     :prefix      prefix
                                     :locks       (atom {})})]
    (go store)))

