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
    (let [sastojci (read-line)]

      (println "Unesi vreme pripreme u minutima:")
      (let [vreme (read-line)]

        (if (and (not (str/blank? naziv))
                 (re-matches #"\d+" vreme)
                 (pos? (Integer/parseInt vreme)))

          (conj recepti
                (napravi-recept
                 naziv
                 (mapv str/trim (str/split sastojci #","))
                 (Integer/parseInt vreme)))

          (do
            (println "Neispravan unos. Pokusaj ponovo.")
            recepti))))))

(defn prikazi-recepte [recepti]
  (println "\n--- MOJI RECEPTI ---")

  (doseq [recept recepti]
    (println "\nNaziv:" (:naziv recept))
    (println "Sastojci:" (:sastojci recept))
    (println "Vreme pripreme:" (:vreme recept) "minuta")))

(defn pretrazi-recepte [recepti pojam]
  (let [pojam (str/lower-case (str/trim pojam))]

    (filter
     (fn [recept]
       (or
        (str/includes?
         (str/lower-case (:naziv recept))
         pojam)

        (some
         #(str/includes?
           (str/lower-case %)
           pojam)
         (:sastojci recept))))
     recepti)))

(defn prikazi-broj-recepata [recepti]
  (println "Ukupan broj recepata:" (count recepti)))

(defn meni []
  (loop [recepti recepti]

    (println "\n--- MOJ KUVAR ---")
    (println "1. Dodaj recept")
    (println "2. Prikazi recepte")
    (println "3. Pretrazi recept po nazivu ili sastojku")
    (println "4. Prikazi broj recepata")
    (println "5. Izlaz")

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
          (println "Unesi naziv ili sastojak:")
          (let [pojam (read-line)
                rezultati (pretrazi-recepte recepti pojam)]

            (if (seq rezultati)
              (prikazi-recepte rezultati)
              (println "Nije pronadjen nijedan recept."))

            (recur recepti)))

        (= izbor "4")
        (do
          (prikazi-broj-recepata recepti)
          (recur recepti))

        (= izbor "5")
        (println "Dovidjenja!")

        :else
        (do
          (println "Nepostojeca opcija.")
          (recur recepti))))))


(defn -main [& args]
  (meni))
