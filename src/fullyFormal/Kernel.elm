module Kernel exposing (Expression(..), Proof(..), Sequence, verifySLProof)

-- This is the kernel of the proof verifier. It only understands fully formal
-- sentential logic.
-- If this has any bugs in it, then all proofs should be considered invalid.

import Char exposing (Char)
import Debug exposing (log)
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
    = Proof Sequence Sequence Sequence


type
    Theorem
    -- Valid (assumptions) theorem
    = Valid Sequence Expression


type alias Axiom =
    Expression -> Bool


type alias Rule =
    Sequence -> Sequence -> Expression -> Bool



-- Verify the axioms


verifySL1 : Rule
verifySL1 assumptions previous expression =
    case expression of
        Implies a1 (Implies b1 a2) ->
            if a1 == a2 then
                True

            else
                False

        _ ->
            False


verifySL2 : Rule
verifySL2 assumptions previous expression =
    case expression of
        Implies (Implies a1 (Implies b1 c1)) (Implies (Implies a2 b2) (Implies a3 c2)) ->
            if a1 == a2 && a1 == a3 && b1 == b2 && c1 == c2 then
                True

            else
                False

        _ ->
            False


verifySL3 : Rule
verifySL3 assumptions previous expression =
    case expression of
        Implies (Implies (Not a1) (Not b1)) (Implies (Implies (Not a2) b2) a3) ->
            if a1 == a2 && a1 == a3 && b1 == b2 then
                True

            else
                False

        _ ->
            False



-- Verify the rules


verifyModusPonens : Rule
verifyModusPonens assumptions previous expression =
    let
        allPrevious =
            concat [ assumptions, previous ]

        implicationCandidates =
            findImplicationCandidates allPrevious expression

        findJustification previous1 =
            case previous1 of
                first :: rest ->
                    if member (Implies first expression) implicationCandidates then
                        True

                    else
                        findJustification rest

                empty ->
                    False
    in
    findJustification allPrevious



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


verifyStep rules assumptions previous expression =
    let
        result =
            -- Expression is an assumption
            List.member expression assumptions
                -- Expression has already been checked
                || List.member expression previous
                || foldl (\rule -> \accum -> accum || rule assumptions previous expression) False rules
    in
    if result then
        Ok [ Valid assumptions expression ]

    else
        Err expression


verifyProof : List Rule -> Proof -> Result Expression (List Theorem)
verifyProof rules (Proof assumptions proof targets) =
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
            foldl
                (\step ->
                    \carry ->
                        case carry of
                            Ok _ ->
                                verifyStep rules assumptions proof step

                            _ ->
                                carry
                )
                (Ok [])
                targets
    in
    case ( validProof, validTargets ) of
        ( Ok _, Ok _ ) ->
            validTargets

        ( Err _, _ ) ->
            validProof

        ( _, Err _ ) ->
            validTargets


sententialLogicRules =
    [ verifySL1, verifySL2, verifySL3, verifyModusPonens ]


verifySLProof =
    verifyProof sententialLogicRules
