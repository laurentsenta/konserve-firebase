(ns konserve-firebase.test.helpers
  (:require [superv.async :refer [S go-try take? alts?]]
    #?(:clj
            [clojure.core.async :refer [<!! timeout]]
       :cljs
       [cljs.core.async :refer [timeout]])
    #?(:clj
            [clojure.test :refer [is]
             :cljs [cljs.test :refer-macros [async is]]]))
  #?(:cljs
     (:require-macros
       ;; self-import makes the macro refer'able from clojurescript
       [konserve-firebase.test.helpers :refer [go-async-timeoutable]])))



;; Tooling
;; http://stackoverflow.com/a/30781278/843194

(defn test-async
  "Asynchronous test awaiting ch to produce a value or close."
  [S ch]
  #?(:clj
     (<!! ch)
     :cljs
     (async done
            (go-try S
              (take? S ch (fn [x]
                            (is (not (instance? js/Error x))
                                (str "Got exception: " x))
                            (done)))))))

(defn test-within
  "Asserts that ch does not close or produce a value within ms. Returns a
  channel from which the value can be taken."
  [S ms ch]
  (go-try S
    (let [t (timeout ms)
          [v ch] (alts? S [ch t])]
      (when (= ch t)
        ;; There was a log here but I don't want to include timbre
        ;; TODO: find a simple clj+cljs alternative
        (is (not= ch t)
            (str "Assert failed because: Test should have finished within " ms "ms.")))
      v)))

#?(:clj
   (defmacro go-async-timeoutable
     [supervisor-sym t & body]
     `(test-async ~supervisor-sym
                  (test-within ~supervisor-sym ~t
                               (go-try ~supervisor-sym
                                 (do ~@body))))))

