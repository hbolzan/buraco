(ns buraco.logic.deck-test
  (:require [buraco.logic.deck :as l.deck]
            [buraco.logic.aux.deck :as a.deck]
            [clojure.test :refer :all]
            [matcher-combinators.matchers :as m]
            [matcher-combinators.test]
            [schema.core :as s]))

(deftest same?-test
  (s/with-fn-validation
    (is (match? (l.deck/same? a.deck/red-ace-of-spades a.deck/black-ace-of-spades) true))
    (is (match? (l.deck/same? a.deck/red-ace-of-spades a.deck/red-two-of-spades) false))))

(deftest strictly-same?-test
  (s/with-fn-validation
    (is (match? (l.deck/strictly-same? a.deck/red-ace-of-spades a.deck/black-ace-of-spades) false))
    (is (match? (l.deck/strictly-same? a.deck/red-ace-of-spades a.deck/red-ace-of-spades) true))))

(deftest pick-and-move-test
  (s/with-fn-validation
    (is (match? (l.deck/pick-and-move {:deck a.deck/deck :pick-pile []} a.deck/red-ace-of-spades)
                {:deck a.deck/expected-deck-after-pick-1 :pick-pile a.deck/expected-pick-pile-1}))
    (is (match? (l.deck/pick-and-move {:deck      a.deck/expected-deck-after-pick-1
                                       :pick-pile a.deck/expected-pick-pile-1}
                                      a.deck/red-two-of-spades)
                {:deck a.deck/expected-deck-after-pick-2 :pick-pile a.deck/expected-pick-pile-2}))))

(deftest deal-next-round-test
  (is (match? (l.deck/deal-next-round [1 2 3 4] [[] []] 2) {:deck [3 4] :hands [[1] [2]]}))
  (is (match? (l.deck/deal-next-round [3 4] [[1] [2]] 2) {:deck [] :hands [[1 3] [2 4]]})))

(deftest deal-test
  (s/with-fn-validation
    (is (match? (l.deck/deal a.deck/shuffled-deck 3 2) {:deck  a.deck/expected-deck-after-deal-a
                                                        :hands a.deck/expected-deal-hands-a}))
    (is (match? (l.deck/deal a.deck/shuffled-deck 2 2) {:deck  a.deck/expected-deck-after-deal-b
                                                        :hands a.deck/expected-deal-hands-b}))))
