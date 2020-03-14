(ns buraco.logic.deck-test
  (:require [buraco.logic.deck :as l.deck]
            [clojure.test :refer :all]
            [matcher-combinators.matchers :as m]
            [matcher-combinators.test]
            [schema.core :as s]))

(def ace-of-spades-a
  #:card{:back-color :red
         :suit       :spades
         :face       :ace})


(def ace-of-spades-b
  #:card{:back-color :black
         :suit       :spades
         :face       :ace})

(deftest same?-test
  (s/with-fn-validation
    (is (match? (l.deck/same? ace-of-spades-a ace-of-spades-b) true))))

(deftest strictly-same?-test
  (s/with-fn-validation
    (is (match? (l.deck/strictly-same? ace-of-spades-a ace-of-spades-b) false))))
