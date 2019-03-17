module KernelTests exposing (suite)

import Builders exposing (pImpliesPProof)
import Expect exposing (Expectation)
import Formalizer exposing (formalSequenceToDeduction, formalizeExpression, formalizeProof)
import Fuzz exposing (Fuzzer, int, list, string)
import Kernel exposing (Expression(..))
import Parser exposing (parseProof)
import Result exposing (andThen)
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
    Ok (SemiFormal.Proof [] (SemiFormal.Deduction (Just (SemiFormal.Sentence "p")) [ SemiFormal.Expr (SemiFormal.Sentence "p") ]) (SemiFormal.Implies (SemiFormal.Sentence "p") (SemiFormal.Sentence "p")))


proof6 =
    let
        inner =
            SemiFormal.Implies (SemiFormal.Sentence "p") (SemiFormal.Implies (SemiFormal.Sentence "p") (SemiFormal.Sentence "p"))
    in
    Ok (SemiFormal.Proof [] (SemiFormal.Deduction (Just (SemiFormal.Sentence "p")) [ SemiFormal.Expr inner ]) (SemiFormal.Implies (SemiFormal.Sentence "p") inner))


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


runProof : Result.Result String SemiFormal.Proof -> Result.Result String (List ( SententialLogic.AxiomName, Kernel.Theorem ))
runProof resultProof =
    resultProof
        |> andThen
            (\proofR ->
                case formalizeProof proofR of
                    Ok proof ->
                        case verifySLProof proof of
                            -- (printProof proof) of
                            Ok res ->
                                Ok res

                            Err (Just expr) ->
                                Err ("couldn't verify line " ++ formalToString expr)

                            Err Nothing ->
                                Err "couldn't verify proof"

                    Err expr ->
                        Err ("couldn't parse line " ++ toString expr)
            )


suite : Test
suite =
    describe "Kernel"
        [ describe "Kernel.verifySLProof"
            [ test "p ⇒ p" <| \_ -> runProof proof1 |> Expect.ok
            , test "p ⇒ q, q ⇒ r ⊢ p ⇒ r" <| \_ -> runProof proof2 |> Expect.ok
            , test "A ∧ B ⊢ ¬(A ⇒ (¬B))" <| \_ -> runProof proof3 |> Expect.ok
            , test "p ⇒ p builder" <| \_ -> runProof proof4 |> Expect.ok
            , test "assumption within assumption" <| \_ -> runProof proof5 |> Expect.ok
            , test "axiom within assumption" <| \_ -> runProof proof6 |> Expect.ok
            , test "p ⇒ q should fail" <| \_ -> runProof failingProof1 |> Expect.err
            ]
        ]
