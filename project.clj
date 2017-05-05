(defproject konserve-firebase "0.0.1"

  :description "A Firebase backend for konserve"
  :url "https://github.com/lsenta/konserve-firebase"

  :dependencies
  [[org.clojure/clojure "1.9.0-alpha16" :scope "provided"]
   [org.clojure/clojurescript "1.9.521" :scope "provided"]

   [cljsjs/firebase "3.7.3-0"]

   [io.replikativ/superv.async "0.2.6"]
   [io.replikativ/konserve "0.4.9"]

   [lein-doo "0.1.7" :scope "test"]]

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

  :doo {:build "test"}

  :profiles
  {:dev
   {:plugins [[lein-doo "0.1.7"]]}}

  :cljsbuild
  {:builds
   [
    ;; -- CLJS: Test --
    ;; Build in a debuggable mode with the doo-runner available
    {:id           "test"
     :source-paths ["env"
                    "src/cljc" "src/cljs"
                    "test/cljc" "test/cljs"]
     :compiler     {:output-to     "target/test/testable.js"
                    :main          konserve-firebase.doo-runner
                    :optimizations :none
                    :pretty-print  true
                    :source-map    true}}
    ;; -- CLJS: Release --
    ;; Build with optimization mode
    {:id           "release"
     :source-paths ["src/cljc"
                    "src/cljs"]
     :compiler     {:output-to     "target/release/js/"
                    :optimizations :advanced
                    :pretty-print  false}}]})
