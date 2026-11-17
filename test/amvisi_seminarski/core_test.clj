(ns amvisi-seminarski.core-test
  (:require [midje.sweet :refer [fact facts =>]]
             [amvisi-seminarski.core :as core]))

(fact "Kreiranje recepta"
      (core/napravi-recept "Palacinke" ["brasno" "mleko" "jaja"] 20)
      =>
      {:naziv "Palacinke"
       :sastojci ["brasno" "mleko" "jaja"]
       :vreme 20})

(fact "Recept moze imati prazne sastojke"
      (core/napravi-recept "Omlet" [] 10)
      =>
      {:naziv "Omlet"
       :sastojci []
       :vreme 10})

(fact "Provera vremena pripreme"
      (:vreme (core/napravi-recept "Testenina" ["testenina" "sir"] 30))
      =>
      30)

(fact "Pretraga recepta po nazivu"
      (map :naziv (core/pretrazi-recepte core/recepti "palacinke"))
      =>
      ["Palacinke"])

(fact "Pretraga recepta po sastojku"
      (map :naziv (core/pretrazi-recepte core/recepti "mleko"))
      =>
      ["Palacinke"])

(fact "Pretraga recepta po drugom sastojku"
      (map :naziv (core/pretrazi-recepte core/recepti "sir"))
      =>
      ["Testenina"])

(fact "Pretraga bez rezultata"
      (core/pretrazi-recepte core/recepti "pica")
      =>
      empty?)

(fact "Pretraga ne zavisi od velikih i malih slova"
      (map :naziv (core/pretrazi-recepte core/recepti "MLEKO"))
      =>
      ["Palacinke"])

(fact "Dodavanje ispravnog recepta"
      (with-in-str "Omlet\njaja, sir\n10\n"
        (binding [*out* (java.io.StringWriter.)]
          (count (core/dodaj-recept core/recepti))))
      =>
      3)

(fact "Neispravno vreme ne dodaje recept"
      (with-in-str "Omlet\njaja\nabc\n"
        (binding [*out* (java.io.StringWriter.)]
          (count (core/dodaj-recept core/recepti))))
      =>
      2)

(fact "Prazan naziv ne dodaje recept"
      (with-in-str "\njaja\n10\n"
        (binding [*out* (java.io.StringWriter.)]
          (count (core/dodaj-recept core/recepti))))
      =>
      2)