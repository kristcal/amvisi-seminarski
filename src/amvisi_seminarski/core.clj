(ns amvisi-seminarski.core
  (:require [clojure.string :as str]))

(def recepti
  [{:naziv "Palacinke"
    :sastojci ["brasno" "mleko" "jaja"]
    :vreme 20}

   {:naziv "Testenina"
    :sastojci ["testenina" "paradajz" "sir"]
    :vreme 30}])

(defn napravi-recept [naziv sastojci vreme]
  {:naziv naziv
   :sastojci sastojci
   :vreme vreme})

(defn dodaj-recept [recepti]
  (println "Unesi naziv recepta:")
  (let [naziv (read-line)]

    (println "Unesi sastojke, odvojene zarezom:")
    (let [sastojci (->> (str/split (read-line) #",")
                        (map str/trim)
                        (remove str/blank?)
                        vec)]

      (println "Unesi vreme pripreme u minutima:")
      (let [vreme (read-line)]

        (if (and (not (str/blank? naziv))
                 (re-matches #"\d+" vreme)
                 (pos? (Integer/parseInt vreme)))

          (conj recepti
                (napravi-recept naziv sastojci (Integer/parseInt vreme)))

          (do
            (println "Neispravan unos. Pokusaj ponovo.")
            recepti))))))

(defn prikazi-recepte [recepti]
  (println "\n--- MOJI RECEPTI ---")

  (if (empty? recepti)
    (println "Nema sacuvanih recepata.")

    (doseq [recept recepti]
      (println "\nNaziv:" (:naziv recept))
      (println "Sastojci:" (:sastojci recept))
      (println "Vreme pripreme:" (:vreme recept) "minuta"))))

;; Pomocna funkcija za normalizaciju teksta
(defn normalizuj [tekst]
  (str/lower-case (str/trim tekst)))

(defn pretrazi-po-nazivu [recepti pojam]
  (let [pojam (normalizuj pojam)]
    (if (str/blank? pojam)
      []
      (filterv #(str/includes? (normalizuj (:naziv %)) pojam)
               recepti))))

(defn pretrazi-po-sastojku [recepti sastojak]
  (let [sastojak (normalizuj sastojak)]
    (if (str/blank? sastojak)
      []
      (filterv (fn [recept]
                 (some #(str/includes? (normalizuj %) sastojak)
                       (:sastojci recept)))
               recepti))))

(defn pretrazi-recepte [recepti pojam]
  (->> (concat (pretrazi-po-nazivu recepti pojam)
               (pretrazi-po-sastojku recepti pojam))
       distinct
       vec))

(defn prikazi-rezultate [rezultati]
  (if (empty? rezultati)
    (println "Nije pronadjen nijedan recept.")
    (prikazi-recepte rezultati)))

(defn prikazi-broj-recepata [recepti]
  (println "Ukupan broj recepata:" (count recepti)))

(defn meni []
  (loop [recepti recepti]

    (println "\n--- MOJ KUVAR ---")
    (println "1. Dodaj recept")
    (println "2. Prikazi recepte")
    (println "3. Pretrazi po nazivu")
    (println "4. Pretrazi po sastojku")
    (println "5. Prikazi broj recepata")
    (println "6. Izlaz")

    (let [izbor (read-line)]
      (cond
        (= izbor "1")
        (recur (dodaj-recept recepti))

        (= izbor "2")
        (do
          (prikazi-recepte recepti)
          (recur recepti))

        (= izbor "3")
        (do
          (println "Unesi naziv recepta koji trazis:")
          (prikazi-rezultate (pretrazi-po-nazivu recepti (read-line)))
          (recur recepti))

        (= izbor "4")
        (do
          (println "Unesi sastojak koji trazis:")
          (prikazi-rezultate (pretrazi-po-sastojku recepti (read-line)))
          (recur recepti))

        (= izbor "5")
        (do
          (prikazi-broj-recepata recepti)
          (recur recepti))

        (= izbor "6")
        (println "Dovidjenja!")

        :else
        (do
          (println "Nepostojeca opcija.")
          (recur recepti))))))

(defn -main [& args]
  (meni))