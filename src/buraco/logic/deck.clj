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

(s/defn cards-for-suit :- [m.deck/Card]
  [suit :- m.deck/Suit]
  (map #(new-card back-color suit %) m.deck/faces))

(s/defn new :- [m.deck/Card]
  [back-color :- m.deck/Color]
  (map #(cards-for-suit %) m.deck/suits))
