(ns amvisi-seminarski.core
  (:require [clojure.string :as str]))

(def recepti
  [{:naziv "Palacinke"
    :sastojci ["brasno" "mleko" "jaja"]
    :vreme 20}

   {:naziv "Testenina"
    :sastojci ["testenina" "paradajz" "sir"]
    :vreme 30}])


(defn dodaj-recept [recepti]
  (println "Unesi naziv recepta:")
  (let [naziv (read-line)]

    (println "Unesi sastojke, odvojene zarezom:")
    (let [sastojci (read-line)]

      (println "Unesi vreme pripreme u minutima:")
      (let [vreme (read-line)]

        (conj recepti
              {:naziv naziv
               :sastojci (str/split sastojci #", ")
               :vreme (Integer/parseInt vreme)})))))


(defn prikazi-recepte [recepti]
  (println "\n--- MOJI RECEPTI ---")

  (doseq [recept recepti]
    (println "\nNaziv:" (:naziv recept))
    (println "Sastojci:" (:sastojci recept))
    (println "Vreme pripreme:" (:vreme recept) "minuta")))


(defn meni []
  (loop [recepti recepti]

    (println "\n1. Dodaj recept")
    (println "2. Prikazi recepte")
    (println "3. Izlaz")

    (let [izbor (read-line)]

      (cond
        (= izbor "1")
        (recur (dodaj-recept recepti))

        (= izbor "2")
        (do
          (prikazi-recepte recepti)
          (recur recepti))

        (= izbor "3")
        (println "Dovidjenja!")

        :else
        (do
          (println "Nepostojeca opcija.")
          (recur recepti))))))


(defn -main [& args]
  (meni))
