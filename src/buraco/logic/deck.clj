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

(s/defn create :- [m.deck/Card]
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

(defn pick-card [{:keys [deck shuffled]} removed]
  {:deck     (remove #(strictly-same? removed %) deck)
   :shuffled (conj shuffled removed)})

(s/defn shuffle :- [m.deck/Card]
  [deck :- [m.deck/Card]
   rnd-fn]
  (loop [decks    {:deck deck :shuffled []}]
    (if (-> decks :deck count zero?)
      (vec (:shuffled decks))
      (recur (pick-card decks (rnd-fn (:deck decks)))))))

(s/defn shuffle-times :- [m.deck/Card]
  [deck :- [m.deck/Card]
   times :- s/Int
   rnd-fn]
  (reduce (fn [d _] (shuffle d rnd-fn)) deck (range times)))

(s/defn deal :- {:piles [[m.deck/Card]]
                 :deck  [m.deck/Card]}
  [deck :- [m.deck/Card]
   players :- s/Int])

;; (let [a (new-card :red :spades :ace)
;;       b (new-card :black :spades :ace)
;;       c (new-card :black :spades :jack)]
;;   (equal-cards? a b))

;; (let [d (create :red)]
;;   (pick {:deck d :shuffled-deck []} 0))

;; (conj [:a :b :c :d] :e)
;; (nth [:a :b :c :d] 2)
