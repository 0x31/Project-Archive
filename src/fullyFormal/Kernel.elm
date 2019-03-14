module Kernel exposing (Expression(..), Sequence, Theorem(..), verifyTheorem)

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


type Theorem
    = Proof Sequence Sequence Expression


type alias Axiom =
    Expression -> Bool


type alias Rule =
    Sequence -> Sequence -> Expression -> Bool



-- Verify the axioms


verifySL1 : Axiom
verifySL1 expression =
    case expression of
        Implies a1 (Implies b1 a2) ->
            if a1 == a2 then
                True

            else
                False

        _ ->
            False


verifySL2 : Axiom
verifySL2 expression =
    case expression of
        Implies (Implies a1 (Implies b1 c1)) (Implies (Implies a2 b2) (Implies a3 c2)) ->
            if a1 == a2 && a1 == a3 && b1 == b2 && c1 == c2 then
                True

            else
                False

        _ ->
            False


verifySL3 : Axiom
verifySL3 expression =
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


verifyStep assumptions previous expression =
    -- Expression is an assumption
    List.member expression assumptions
        -- Expression has already been checked
        || List.member expression previous
        || verifySL1 expression
        || verifySL2 expression
        || verifySL3 expression
        || verifyModusPonens assumptions previous expression


verifyTheorem : Theorem -> Bool
verifyTheorem (Proof assumptions proof target) =
    first
        (foldl
            (\step ->
                \( carry, previous ) ->
                    if not carry then
                        ( carry, previous )

                    else
                        ( verifyStep assumptions previous step, step :: previous )
            )
            ( True, [] )
            proof
        )
        && verifyStep assumptions proof target
