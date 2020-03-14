(ns buraco.logic.aux.deck)

(defn fake-random [fake-rnd-seq]
  (let [pending* (atom fake-rnd-seq)]
    (fn [_]
      (let [next-rnd (first @pending*)]
        (swap! pending* #(rest %))
        next-rnd))))

(defn- new-card [color suit face]
  (if (= face :joker)
    #:card{:back-color color :face face}
    #:card{:back-color color :suit suit :face face}))

(def black-ace-of-spades (new-card :black :spades :ace))
(def black-two-of-spades (new-card :black :spades 2))
(def black-joker (new-card :black nil :joker))

(def red-ace-of-spades (new-card :red :spades :ace))
(def red-two-of-spades (new-card :red :spades 2))
(def red-joker (new-card :red nil :joker))

(def deck
  [black-ace-of-spades
   black-two-of-spades
   black-joker
   red-ace-of-spades
   red-two-of-spades
   red-joker])

(def shuffled-deck
  [black-joker
   red-ace-of-spades
   red-joker
   red-two-of-spades
   black-ace-of-spades
   black-two-of-spades])

(def expected-deal-hands-a
  [[black-joker red-joker black-ace-of-spades]
   [red-ace-of-spades red-two-of-spades black-two-of-spades]])

(def expected-deck-after-deal-a [])

(def expected-deal-hands-b
  [[black-joker red-joker]
   [red-ace-of-spades red-two-of-spades]])

(def expected-deck-after-deal-b [black-ace-of-spades black-two-of-spades])

(def expected-deck-after-pick-1
  [black-ace-of-spades
   black-two-of-spades
   black-joker
   red-two-of-spades
   red-joker])

(def expected-pick-pile-1
  [red-ace-of-spades])

(def expected-deck-after-pick-2
  [black-ace-of-spades
   black-two-of-spades
   black-joker
   red-joker])

(def expected-pick-pile-2
  [red-ace-of-spades
   red-two-of-spades])
