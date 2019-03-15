module KernelTests exposing (suite)

import Expect exposing (Expectation)
import Fuzz exposing (Fuzzer, int, list, string)
import Kernel exposing (Expression(..), verifySLProof)
import Parser exposing (parseProof)
import SemiFormal exposing (formalizeProof)
import Test exposing (..)


parsedProof1 =
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


parsedProof2 =
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


parsedProof3 =
    parseProof """
GOAL
¬(A ⇒ (¬B))

ASSUMING
A ∧ B


PROOF
A ∧ B
¬(A ⇒ (¬B))
"""


failingProof1 =
    parseProof """
GOAL
p ⇒ q

ASSUMING

PROOF
p ⇒ q
"""


shouldPass resultProof =
    \_ ->
        case resultProof of
            Ok proof ->
                verifySLProof (formalizeProof proof)
                    |> Expect.ok

            Err msg ->
                Expect.fail msg


shouldFail resultProof =
    \_ ->
        case resultProof of
            Ok proof ->
                verifySLProof (formalizeProof proof)
                    |> Expect.equal (Err (Implies (Sentence "p") (Sentence "q")))

            Err msg ->
                Expect.fail msg


suite : Test
suite =
    describe "Kernel"
        [ describe "Kernel.verifySLProof"
            [ test "p ⇒ p" <| shouldPass parsedProof1
            , test "p ⇒ q, q ⇒ r ⊢ p ⇒ r" <| shouldPass parsedProof2
            , test "A ∧ B ⊢ ¬(A ⇒ (¬B))" <| shouldPass parsedProof3
            , test "p ⇒ q should fail" <| shouldFail failingProof1
            ]
        ]
