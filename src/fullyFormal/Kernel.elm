module Kernel exposing (AxiomName, Expression(..), Proof(..), Sequence, Theorem, verifySLProof)

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
    = Proof Sequence Sequence Expression


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


verifyStep : List { rule : Rule, name : AxiomName } -> List Expression -> List Expression -> Expression -> Result (Maybe Expression) ( AxiomName, List Theorem )
verifyStep rules assumptions previous expression =
    foldl
        (\rule ->
            \accum ->
                case accum of
                    Ok _ ->
                        accum

                    Err _ ->
                        if rule.rule assumptions previous expression then
                            Ok ( rule.name, [ Valid assumptions expression ] )

                        else
                            Err (Just expression)
        )
        (Err Nothing)
        rules


verifyProof : List { rule : Rule, name : AxiomName } -> Proof -> Result (Maybe Expression) ( AxiomName, List Theorem )
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
                    ( Ok ( AlreadyProved, [] ), [] )
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


type AxiomName
    = AlreadyProved
    | SL1
    | SL2
    | SL3
    | MP


sententialLogicRules =
    [ { rule = verifyAlreadyProved, name = AlreadyProved }
    , { rule = verifySL1, name = SL1 }
    , { rule = verifySL2, name = SL2 }
    , { rule = verifySL3, name = SL3 }
    , { rule = verifyModusPonens, name = MP }
    ]


verifySLProof =
    verifyProof sententialLogicRules
