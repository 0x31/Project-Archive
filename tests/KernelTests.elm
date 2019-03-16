module KernelTests exposing (suite)

import Expect exposing (Expectation)
import Fuzz exposing (Fuzzer, int, list, string)
import Kernel exposing (Expression(..), verifySLProof)
import Parser exposing (parseProof)
import Result exposing (andThen)
import SemiFormal exposing (formalToString, formalizeExpression, formalizeProof, toString)
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



-- printProof : Kernel.Proof -> Kernel.Proof
-- printProof (Kernel.Proof assumptions proof goal) =
--     let
--         print1 =
--             Debug.log "\n" ""
--         print2 =
--             Debug.log "GOAL: " (formalToString goal)
--         print3 =
--             List.map
--                 (\line ->
--                     Debug.log "| " (formalToString line)
--                 )
--                 proof
--     in
--     Kernel.Proof assumptions proof goal


runProof : Result.Result String SemiFormal.Proof -> Result.Result String ( Kernel.AxiomName, List Kernel.Theorem )
runProof resultProof =
    resultProof
        |> andThen
            (\proofR ->
                case formalizeProof proofR of
                    Ok proof ->
                        case verifySLProof proof of
                            Ok res ->
                                Ok res

                            Err (Just expr) ->
                                Err ("coudln't verify line" ++ formalToString expr)

                            Err Nothing ->
                                Err "coudln't verify proof"

                    Err expr ->
                        Err ("coudln't parse line" ++ toString expr)
            )


suite : Test
suite =
    describe "Kernel"
        [ describe "Kernel.verifySLProof"
            [ test "p ⇒ p" <| \_ -> runProof parsedProof1 |> Expect.ok
            , test "p ⇒ q, q ⇒ r ⊢ p ⇒ r" <| \_ -> runProof parsedProof2 |> Expect.ok
            , test "A ∧ B ⊢ ¬(A ⇒ (¬B))" <| \_ -> runProof parsedProof3 |> Expect.ok
            , test "p ⇒ q should fail" <| \_ -> runProof failingProof1 |> Expect.err
            ]
        ]
