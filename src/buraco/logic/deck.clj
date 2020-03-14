(ns buraco.logic.deck
  (:require [buraco.models.deck :as m.deck]
            [schema.core :as s]))

(s/defn new-card :- m.deck/Card
  [back-color :- m.deck/Color
   suit :- m.deck/Suit
   face :- m.deck/Face]
  #:card{:back-color back-color
         :suit       suit
         :face       face})

(s/defn joker :- m.deck/Card
  [back-color :- m.deck/Color]
  #:card{:back-color back-color
         :face       :joker})

(s/defn cards-for-suit :- [m.deck/Card]
  [back-color :- m.deck/Color
   suit :- m.deck/Suit]
  (map #(new-card back-color suit %) m.deck/faces))

(s/defn full-deck :- [m.deck/Card]
  [back-color :- m.deck/Color]
  (conj (reduce (fn [deck suit] (into deck (cards-for-suit back-color suit))) [] m.deck/suits)
        (joker back-color)))

(defn same?
  [{suit-a :card/suit face-a :card/face}
   {suit-b :card/suit face-b :card/face}]
  (and (= suit-a suit-b) (= face-a face-b)))

(defn strictly-same?
  [{color-a :card/back-color :as a}
   {color-b :card/back-color :as b}]
  (and (same? a b) (= color-a color-b)))

(defn pick-and-move [{:keys [deck pick-pile]} picked]
  {:deck      (remove #(strictly-same? picked %) deck)
   :pick-pile (conj pick-pile picked)})

(s/defn shuffle-times :- [m.deck/Card]
  [deck :- [m.deck/Card]
   times :- s/Int]
  (reduce (fn [d _] (shuffle d)) deck (range times)))

(s/defn deal-next-round
  [deck hands players-count]
  {:deck (nthrest deck players-count)
   :hands (map (fn [card hand] (conj hand card)) (take players-count deck) hands)})

(s/defn deal :- {:hands [[m.deck/Card]] :deck  [m.deck/Card]}
  [deck :- [m.deck/Card]
   cards-count :- s/Int
   players-count :- s/Int]
  (let [empty-hands (map (fn [_] []) (range players-count))]
    (reduce (fn [{:keys [deck hands]} _] (deal-next-round deck hands players-count))
            {:deck deck :hands empty-hands}
            (range cards-count))))
