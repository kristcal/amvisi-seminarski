(ns amvisi-seminarski.core-test
  (:require [clojure.test :refer :all]
            [amvisi-seminarski.core :as core]))

(deftest test-napravi-recept
  (is (= {:naziv "Palacinke"
          :sastojci ["brasno" "mleko" "jaja"]
          :vreme 20}
         (core/napravi-recept "Palacinke"
                              ["brasno" "mleko" "jaja"]
                              20))))

(deftest test-prazni-sastojci
  (is (= {:naziv "Omlet"
          :sastojci []
          :vreme 10}
         (core/napravi-recept "Omlet" [] 10))))

(deftest test-vreme-pripreme
  (is (= 30
         (:vreme (core/napravi-recept "Testenina"
                                      ["testenina" "sir"]
                                      30)))))