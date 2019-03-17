module Kernel exposing (Axiom, Expression(..), Proof(..), Rule, Sequence, Theorem(..), findImplicationCandidates, verifyAlreadyProved, verifyProof, verifyStep)

-- This is the kernel of the proof verifier. It only understands fully formal
-- sentential logic.
-- If this has any bugs in it, then all proofs should be considered invalid.

import Char exposing (Char)
import List exposing (concat, filter, foldl, head, member, reverse)
import Tuple exposing (first)



-- Types


type Expression
    = Not Expression
    | Implies Expression Expression
    | Sentence String


type alias Sequence =
    List Expression


type
    Proof
    -- Proof(Assumptions, Proof, Goal)
    = Proof Sequence Sequence Expression


type
    Theorem
    -- Valid (assumptions) theorem
    = Valid Sequence Expression


type alias Axiom =
    Expression -> Bool


type alias Rule =
    Sequence -> Sequence -> Expression -> Bool


verifyAlreadyProved : Rule
verifyAlreadyProved assumptions previous expression =
    -- Expression is an assumption
    List.member expression assumptions
        -- Expression has already been checked
        || List.member expression previous



-- Verify proof


findImplicationCandidates : List Expression -> Expression -> List Expression
findImplicationCandidates previous expression =
    filter
        (\p ->
            case p of
                Implies left right ->
                    if right == expression then
                        True

                    else
                        False

                _ ->
                    False
        )
        previous


verifyStep : List { rule : Rule, name : a } -> List Expression -> List Expression -> Expression -> Result (Maybe Expression) (List ( a, Theorem ))
verifyStep rules assumptions previous expression =
    foldl
        (\rule ->
            \accum ->
                case accum of
                    Ok _ ->
                        accum

                    Err _ ->
                        if rule.rule assumptions previous expression then
                            Ok [ ( rule.name, Valid assumptions expression ) ]

                        else
                            Err (Just expression)
        )
        (Err Nothing)
        rules


verifyProof : List { rule : Rule, name : a } -> Proof -> Result (Maybe Expression) (List ( a, Theorem ))
verifyProof rules (Proof assumptions proof target) =
    let
        validProof =
            first
                (foldl
                    (\step ->
                        \( carry, previous ) ->
                            case carry of
                                Ok _ ->
                                    ( verifyStep rules assumptions previous step, step :: previous )

                                _ ->
                                    ( carry, previous )
                    )
                    ( Ok [], [] )
                    proof
                )

        validTargets =
            verifyStep rules assumptions proof target
    in
    case ( validProof, validTargets ) of
        ( Ok _, Ok _ ) ->
            validTargets

        ( Err _, _ ) ->
            validProof

        ( _, Err _ ) ->
            validTargets
