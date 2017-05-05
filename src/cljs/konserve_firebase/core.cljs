(ns konserve-firebase.core
  (:refer-clojure :exclude [get-in! assoc! update-in! assoc-in! exists? dissoc dissoc-in!])
  (:require [incognito.edn :refer [read-string-safe]]
            [cljsjs.firebase]
            [clojure.string :as str]
            [cljs.core.async :as async :refer [take! <! >! put! close! chan poll!]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))


;; Serialization & Encodings
;; -------------------------

(defn -frb-encode-rec [x]
  (cond
    (nil? x) nil
    (number? x) x
    (string? x) x
    (keyword? x) (str "~:" (name x))
    (= (type x) UUID) (str "~u" x)
    (vector? x) (->> x (map -frb-encode-rec) (into []))
    (map? x) (->> x (map (fn [[k v]] [(-frb-encode-rec k) (-frb-encode-rec v)])) (into {}))
    :else (throw (js/Error. (str "Encode Unsuported val=" x ", nil?=" (nil? x) ", type=" (type x))))))

(defn -frb-encode [x]
  (-> x -frb-encode-rec clj->js))

(defn -frb-decode-rec [x]
  (cond
    (number? x) x
    (string? x) (if-let [[_ suffix] (re-matches #"^~:(.*)$" x)]
                  (keyword suffix)
                  (if-let [[_ suffix] (re-matches #"^~u(.*)$" x)]
                    (uuid suffix)
                    x))
    (vector? x) (->> x (map -frb-decode-rec) (into []))
    (map? x) (->> x (map (fn [[k v]] [(-frb-decode-rec k) (-frb-decode-rec v)])) (into {}))
    :else (throw (js/Error. (str "Unsuported type=" (type x))))))

(defn -frb-decode [x]
  (-> x js->clj -frb-decode-rec))


;; Path Manipulation
;; -----------------

(defn -raise-on-slash [t]
  (when (and (string? t) (re-find #"/" t))
    (throw (js/Error. (str "slashes in token t=" t))))
  t)

(defn -token->str [t]
  ;; /!\ Need to be compatible with frb-encode too
  (-raise-on-slash
    (cond
      (= (type t) UUID) (str "~u" t)
      (keyword? t) (str "~:" (name t))
      (string? t) t
      (number? t) t
      :else (throw (js/Error. (str "Unknown token val= `" t "', type= `" (type t) "', nil?: `" (nil? t) "'"))))))

(defn ->path
  ([path]
   (->> path
        (map -token->str)
        (str/join "/"))))

;; Operations
;; ----------

(defn with-nil [x]
  (if (= :nil x) nil x))

(defn get-in! [db path]
  (let [path (->path path)
        ref (-> db (.ref path))
        p (.once ref "value")
        c (chan)]
    (.then p
           (fn [snapshot]
             (let [val (.val snapshot)]
               (go (>! c (if (nil? val)
                           :nil
                           (-frb-decode val)))
                   (async/close! c))))
           (fn [e]
             (go (>! c e)
                 (async/close! c))))
    c))

(defn assoc-in! [db path value]
  (let [path (->path path)
        ref (-> db (.ref path))
        r (.set ref (-frb-encode value))
        c (chan)]
    (.then r
           (fn []
             (go (>! c :success)
                 (async/close! c)))
           (fn [e]
             (go (>! c e)
                 (async/close! c))))
    c))

(defn assoc! [db key value]
  (assoc-in! db [key] value))

(defn get! [db key]
  (get-in! db [key]))

(defn update-in! [db path f]
  ;; TODO: Use a firebase transaction here
  (go (let [v (<! (get-in! db path))]
        (<! (assoc-in! db path (f v))))))

(defn dissoc-in! [db path]
  (assoc-in! db path nil))

