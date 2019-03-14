module Testing exposing (proof1, run)

import Array exposing (fromList)
import Debug exposing (log)
import Kernel exposing (..)
import List exposing (concat)
import Parser exposing (parseProof, parseString)


p : Expression
p =
    Sentence "p"


goal1 =
    Implies p p


assumptions1 =
    []


proof1 =
    parseProof """
(p ⇒ ((p ⇒ p) ⇒ p)) ⇒ ((p ⇒ (p ⇒ p)) ⇒ (p ⇒ p))
p ⇒ ((p ⇒ p) ⇒ p)
(p ⇒ (p ⇒ p)) ⇒ (p ⇒ p)
p ⇒ (p ⇒ p)
p ⇒ p
"""


maybeGoal =
    parseString "A ⇒ B"


maybeAssumptions =
    parseProof """
A ⇒ C
C ⇒ B
"""


maybeProof =
    parseProof """
(C ⇒ B) ⇒ (A ⇒ (C ⇒ B))
C ⇒ B
A ⇒ (C ⇒ B)
(A ⇒ (C ⇒ B)) ⇒ ((A ⇒ C) ⇒ (A ⇒ B))
(A ⇒ C) ⇒ (A ⇒ B)
A ⇒ C
A ⇒ B
"""


run =
    case ( maybeGoal, maybeAssumptions, maybeProof ) of
        ( Just goal, Just assumptions, Just proof ) ->
            log "just"
                (verifyTheorem (Proof assumptions proof goal))

        _ ->
            log "nothing" False
