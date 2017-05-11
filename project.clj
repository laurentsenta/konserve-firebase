(defproject lsenta/konserve-firebase "0.0.2"

  :description "A Firebase backend for konserve"
  :url "https://github.com/lsenta/konserve-firebase"
  :license {:name "MIT License"}

  :repositories [["releases" {:url      "https://clojars.org/repo/"
                              :username :env/clojars_account
                              :password :env/clojars_password}]
                 ["snapshots" {:url      "https://clojars.org/repo/"
                               :username :env/clojars_account
                               :password :env/clojars_password}]]


  :dependencies
  [[org.clojure/clojure "1.9.0-alpha16" :scope "provided"]
   [org.clojure/clojurescript "1.9.521" :scope "provided"]

   [cljsjs/firebase "3.7.3-0"]

   [funcool/cuerdas "2.0.3"]
   [io.replikativ/superv.async "0.2.6"]
   [io.replikativ/konserve "0.4.9"]

   [lsenta/figwheel-testbook "0.0.2" :scope "test"]
   [lein-doo "0.1.7" :scope "test"]
   [binaryage/devtools "0.9.2" :scope "test"]
   [binaryage/dirac "1.2.2" :scope "test"]]

  :source-paths
  ["src/cljc" "src/cljs"]
  :test-paths
  ["test/cljc" "test/cljs"]
  :resource-paths
  ["resources"]

  :jar-exclusions
  [#"env/"]

  :plugins [[lein-cljsbuild "1.1.6"]
            [lein-doo "0.1.7"]]

  :hooks [leiningen.cljsbuild]

  :clean-targets
  ^{:protect false} [:target-path :compile-path "resources/public/js-test"]

  :doo {:build "test"}

  :figwheel
  {:server-port 8200}

  :profiles
  {:dev
   {:plugins [[lein-doo "0.1.7"]
              [lein-figwheel "0.5.10"]]}}

  :cljsbuild
  {:builds
   [
    ;; -- CLJS: Test --
    ;; Build in a debuggable mode with the doo-runner available
    {:id           "test"
     :source-paths ["env"
                    "src/cljc" "src/cljs"
                    "test/cljc" "test/cljs"]
     :compiler     {:output-to     "resources/public/js-test/testable.js"
                    :output-dir    "resources/public/js-test/testable"
                    :main          running.doo-runner
                    :optimizations :none
                    :pretty-print  true
                    :source-map    true}}
    ;; -- CLJS: testbook --
    ;; The small test runner on top of figwheel
    ;; generates to testbook.js
    {:id           "testbook"
     :figwheel     {:on-jsload running.testbook/reload-hook}
     :source-paths ["env/"
                    "src/cljc" "src/cljs"
                    "test/cljc" "test/cljs"]
     :incremental  true
     :compiler     {:main          running.testbook
                    :asset-path    "/js-test/testbook"
                    :output-to     "resources/public/js-test/testbook.js"
                    :output-dir    "resources/public/js-test/testbook"
                    :optimizations :none
                    :source-map    true
                    :pretty-print  true}}
    ;; -- CLJS: Release --
    ;; Build with optimization mode
    {:id           "release"
     :source-paths ["src/cljc"
                    "src/cljs"]
     :compiler     {:output-to     "target/release/js/konserve_firebase.js"
                    :output-dir    "target/release/js/"
                    :optimizations :advanced
                    :pretty-print  false}}]})
