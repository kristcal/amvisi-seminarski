(ns amvisi-seminarski.core
  (:require [clojure.string :as str])
  (:gen-class))

(def transakcije
  [{:opis "IP nalog: KIRIJA MARKO MARKOVIC" :odliv 20000}
   {:opis "MAXI PRODAVNICA" :odliv 3450}
   {:opis "BUS PLUS DOPUNA" :odliv 1500}
   {:opis "IDEA MARKET" :odliv 2100}
   {:opis "IP nalog: STEDNJA JA UNICREDIT" :odliv 10000}])

(def pravila
  {:prenos ["ja unicredit"]
   :racuni ["kirija" "struja"]
   :hrana  ["maxi" "idea"]
   :prevoz ["bus plus"]})

(defn kategorija [t]
  (let [opis (str/lower-case (:opis t))]
    (or (some (fn [[naziv reci]]
                (when (some #(str/includes? opis %) reci) naziv))
              pravila)
        :ostalo)))

(defn trosak? [t]
  (not= :prenos (kategorija t)))

(defn potrosnja-po-kategoriji [transakcije]
  (reduce (fn [rezultat t]
            (update rezultat (kategorija t) (fnil + 0) (:odliv t)))
          {}
          (filter trosak? transakcije)))

;; Sledece faze (pseudokod):
;; - ucitaj pravi izvod iz PDF/CSV fajla
;; - umesto pravila, nauci kategorije naivnim Bajesovim klasifikatorom
;; - pronadji neobicno velike troskove i predvidi potrosnju

(defn -main [& args]
  (println "Potrosnja po kategorijama:" (potrosnja-po-kategoriji transakcije)))