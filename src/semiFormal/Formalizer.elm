module Formalizer exposing (applyDeductionTheorem, formalizeDeduction, formalizeDeductionHelper, formalizeExpression, formalizeProof, sequenceToDeduction, unformalizeExpression)

import Kernel
import SemiFormal exposing (Deduction(..), Expression(..), Proof(..))
import SententialLogic


formalizeExpression : Expression -> Kernel.Expression
formalizeExpression expression =
    case expression of
        Not e ->
            Kernel.Not (formalizeExpression e)

        Implies lhs rhs ->
            Kernel.Implies (formalizeExpression lhs) (formalizeExpression rhs)

        And lhs rhs ->
            Kernel.Not (Kernel.Implies (formalizeExpression lhs) (Kernel.Not (formalizeExpression rhs)))

        Or lhs rhs ->
            Kernel.Implies (Kernel.Not (formalizeExpression lhs)) (formalizeExpression rhs)

        Iff lhs rhs ->
            formalizeExpression (And (Implies lhs rhs) (Implies rhs lhs))

        Sentence s ->
            Kernel.Sentence s


unformalizeExpression : Kernel.Expression -> Expression
unformalizeExpression expression =
    case expression of
        Kernel.Not e ->
            Not (unformalizeExpression e)

        Kernel.Implies lhs rhs ->
            Implies (unformalizeExpression lhs) (unformalizeExpression rhs)

        Kernel.Sentence s ->
            Sentence s


sequenceToDeduction : List Expression -> Deduction
sequenceToDeduction sequence =
    Deduction Nothing (List.map (\expr -> Expr expr) sequence)


applyDeductionTheorem : Maybe Expression -> List Expression -> Result Expression (List Expression)
applyDeductionTheorem assumptionM sequence =
    case assumptionM of
        Nothing ->
            Ok sequence

        Just assumption ->
            case sequence of
                [] ->
                    Ok []

                f :: fs ->
                    let
                        formalF =
                            formalizeExpression f
                    in
                    case SententialLogic.verifySLProof (Kernel.Proof [] [] formalF) of
                        Ok _ ->
                            -- ( SententialLogic.SL1, _ ) ->
                            applyDeductionTheorem (Just assumption) sequence

                        Err _ ->
                            Err f


formalizeDeduction : Deduction -> Kernel.Sequence -> Result Expression Kernel.Sequence
formalizeDeduction proofSequence assuptions =
    formalizeDeductionHelper proofSequence assuptions []


formalizeDeductionHelper : Deduction -> Kernel.Sequence -> Kernel.Sequence -> Result Expression Kernel.Sequence
formalizeDeductionHelper proofSequence assuptions linesSeen =
    case proofSequence of
        Deduction assumption subdeductions ->
            let
                fixSubListsR =
                    List.foldl
                        (\subdeduction ->
                            \accumR ->
                                case accumR of
                                    Err err ->
                                        Err err

                                    Ok accum ->
                                        case formalizeDeductionHelper subdeduction (List.concat [ assuptions, accum ]) (List.concat [ linesSeen, accum ]) of
                                            Ok subCall ->
                                                Ok (List.concat [ accum, subCall ])

                                            Err err ->
                                                Err err
                        )
                        (Ok [])
                        subdeductions

                fixListR =
                    case fixSubListsR of
                        Err err ->
                            Err err

                        Ok fixSubLists ->
                            applyDeductionTheorem assumption (List.map unformalizeExpression fixSubLists)
            in
            case fixListR of
                Ok fixList ->
                    Ok (List.map formalizeExpression fixList)

                Err err ->
                    Err err

        Expr expression ->
            Ok [ formalizeExpression expression ]


formalizeProof : Proof -> Result Expression Kernel.Proof
formalizeProof (Proof assumptions proof goal) =
    let
        formalAssuptions =
            List.map formalizeExpression assumptions

        formalProofR =
            formalizeDeduction proof formalAssuptions
    in
    case formalProofR of
        Err err ->
            Err err

        Ok formalProof ->
            Ok (Kernel.Proof formalAssuptions formalProof (formalizeExpression goal))

