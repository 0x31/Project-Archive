module KernelTests exposing (suite)

import Builders exposing (pImpliesPProof)
import Expect exposing (Expectation)
import Formalizer exposing (formalSequenceToDeduction, formalizeExpression, formalizeProof)
import Fuzz exposing (Fuzzer, int, list, string)
import Kernel exposing (Expression(..))
import Parser exposing (parseProof)
import Result exposing (andThen)
import Run exposing (formalizeAndRunProof)
import SemiFormal
import SententialLogic exposing (verifySLProof)
import Test exposing (..)
import ToString exposing (formalToString, toString)


proof1 =
    parseProof """
GOAL
p ⇒ p
ASSUMING
PROOF
(p ⇒ ((p ⇒ p) ⇒ p)) ⇒ ((p ⇒ (p ⇒ p)) ⇒ (p ⇒ p))
p ⇒ ((p ⇒ p) ⇒ p)
(p ⇒ (p ⇒ p)) ⇒ (p ⇒ p)
p ⇒ (p ⇒ p)
p ⇒ p
"""


proof2 =
    parseProof """
GOAL
p ⇒ r
ASSUMING
p ⇒ q
q ⇒ r
PROOF
(q ⇒ r) ⇒ (p ⇒ (q ⇒ r))
q ⇒ r
p ⇒ (q ⇒ r)
(p ⇒ (q ⇒ r)) ⇒ ((p ⇒ q) ⇒ (p ⇒ r))
(p ⇒ q) ⇒ (p ⇒ r)
p ⇒ q
p ⇒ r
"""


proof3 =
    parseProof """
GOAL
¬(A ⇒ (¬B))
ASSUMING
A ∧ B
PROOF
A ∧ B
¬(A ⇒ (¬B))
"""


proof4 =
    Ok (SemiFormal.Proof [] (SemiFormal.Deduction Nothing [ pImpliesPProof (Sentence "p") |> formalSequenceToDeduction ]) (SemiFormal.Implies (SemiFormal.Sentence "p") (SemiFormal.Sentence "p")))


failingProof1 =
    parseProof """
GOAL
p ⇒ q
ASSUMING
PROOF
p ⇒ q
"""


proof5 =
    parseProof """
GOAL
p ⇒ p

ASSUMING

PROOF
| ASSUMING p
| p
p ⇒ p
"""


proof6 =
    parseProof """
GOAL
p ⇒ (p ⇒ (p ⇒ p))

ASSUMING

PROOF
| ASSUMING p
| p ⇒ (p ⇒ p)
p ⇒ (p ⇒ (p ⇒ p))
"""


proof7 =
    parseProof """
GOAL
p ⇒ (p ⇒ p)

ASSUMING
a ⇒ a

PROOF
| ASSUMING p
| | ASSUMING p
| | a ⇒ a
| p ⇒ (a ⇒ a)
p ⇒ (p ⇒ (a ⇒ a))
"""



-- proof8 =
--     parseProof """
-- GOAL
-- B ⇒ (A ⇒ C)
-- ASSUMING
-- A ⇒ (B ⇒ C)
-- PROOF
-- A ⇒ (B ⇒ C)
-- | ASSUMING B
-- | | ASSUMING A
-- | | B ⇒ C
-- | | C
-- | A ⇒ C
-- B ⇒ (A ⇒ C)
-- """


printProof : Kernel.Proof -> Kernel.Proof
printProof (Kernel.Proof assumptions proof goal) =
    let
        print1 =
            Debug.log "\n" ""

        print2 =
            Debug.log "GOAL: " (formalToString goal)

        print3 =
            List.map
                (\line ->
                    Debug.log "| " (formalToString line)
                )
                proof
    in
    Kernel.Proof assumptions proof goal


suite : Test
suite =
    describe "Kernel"
        [ describe "Kernel.verifySLProof"
            [ test "p ⇒ p" <| \_ -> formalizeAndRunProof proof1 |> Expect.ok
            , test "p ⇒ q, q ⇒ r ⊢ p ⇒ r" <| \_ -> formalizeAndRunProof proof2 |> Expect.ok
            , test "A ∧ B ⊢ ¬(A ⇒ (¬B))" <| \_ -> formalizeAndRunProof proof3 |> Expect.ok
            , test "p ⇒ p builder" <| \_ -> formalizeAndRunProof proof4 |> Expect.ok
            , test "assumption within assumption" <| \_ -> formalizeAndRunProof proof5 |> Expect.ok
            , test "axiom within assumption" <| \_ -> formalizeAndRunProof proof6 |> Expect.ok
            , test "parsing assumptions" <| \_ -> formalizeAndRunProof proof7 |> Expect.ok

            -- , test "(A ⇒ (B ⇒ C)) ⊢ (B ⇒ (A ⇒ C))" <| \_ -> formalizeAndRunProof proof8 |> Expect.ok
            , test "p ⇒ q should fail" <| \_ -> formalizeAndRunProof failingProof1 |> Expect.err
            ]
        ]
