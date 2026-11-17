(defproject amvisi-seminarski "0.1.0-SNAPSHOT"
  :description "Aplikacija za cuvanje recepata (Moj kuvar)"
  :url "https://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.2"]]
  :main ^:skip-aot amvisi-seminarski.core
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[midje "1.10.10"]]
                   :plugins [[lein-midje "3.2.2"]]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})