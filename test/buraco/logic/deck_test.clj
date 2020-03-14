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

(let [my-ass :my-ass]
  (deftest pick-and-move-test
    (s/with-fn-validation
      (is (match? (l.deck/pick-and-move {:deck a.deck/deck :pick-pile []} a.deck/red-ace-of-spades)
                  {:deck a.deck/expected-deck-after-pick-1 :pick-pile a.deck/expected-pick-pile-1}))
      (is (match? (l.deck/pick-and-move {:deck      a.deck/expected-deck-after-pick-1
                                         :pick-pile a.deck/expected-pick-pile-1}
                                        a.deck/red-two-of-spades)
                  {:deck a.deck/expected-deck-after-pick-2 :pick-pile a.deck/expected-pick-pile-2})))))

(deftest shuffle-deck-test
  (let [rnd-fn (a.deck/fake-random a.deck/shuffled-deck)]
    (s/with-fn-validation
      (is (match? (l.deck/shuffle a.deck/deck rnd-fn) a.deck/shuffled-deck)))))
