(ns buraco.models.deck
  (:require [schema.core :as s]))

(def suits #{:diamonds :clubs :hearts :spades})
(def faces #{:ace 2 3 4 5 6 7 9 10 :jack :queen :king})
(def colors #{:red :black})

(s/defschema Suit (apply s/enum suits))
(s/defschema Face (apply s/enum faces))
(s/defschema Color (apply s/enum colors))

(s/defschema RegularCard
  {:card/back-color Color
   :card/face       Face
   :card/suit       Suit})

(s/defschema Joker
  {:card/back-color Color
   :card/face       (s/enum :joker)})

(defn joker? [card]
  (= (:card/face card) :joker))

(s/defschema Card
  (s/conditional joker? Joker
                 #(not (joker? %)) RegularCard))

(comment
  (let [red-ace-of-diamonds
        {:card/back-color :red
         :card/face       :ace
         :card/suit       :diamonds}

        black-king-of-spades
        {:card/back-color :black
         :card/face       :king
         :card/suit       :spades}

        red-joker
        {:card/back-color :red
         :card/face       :joker}
        ]

    (s/validate Card red-ace-of-diamonds)
    (s/validate Card black-king-of-spades)
    (s/validate Card red-joker)
    (s/validate [Card] [red-ace-of-diamonds black-king-of-spades red-joker])

    ))
