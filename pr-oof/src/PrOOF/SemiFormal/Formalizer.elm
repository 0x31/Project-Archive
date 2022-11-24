module Formalizer exposing (applyDeductionTheorem, formalSequenceToDeduction, formalizeDeduction, formalizeDeductionHelper, formalizeExpression, formalizeProof, sequenceToDeduction, unformalizeExpression)

import Builders exposing (pImpliesPProof, sl1, sl2)
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


formalSequenceToDeduction : List Kernel.Expression -> Deduction
formalSequenceToDeduction sequence =
    Deduction Nothing (List.map (\expr -> Expr (unformalizeExpression expr)) sequence)


applyDeductionTheorem : Maybe Expression -> Kernel.Sequence -> Kernel.Sequence -> Kernel.Sequence -> Result Expression Kernel.Sequence
applyDeductionTheorem assumptionM assumptions linesSeen sequence =
    case assumptionM of
        Nothing ->
            Ok sequence

        Just assumption ->
            case sequence of
                [] ->
                    Ok []

                f :: fs ->
                    let
                        formalAssumption =
                            formalizeExpression assumption

                        newLinesR =
                            if f == formalAssumption then
                                Ok (deductionTheoremHandleAssumption assumption)

                            else
                                case SententialLogic.verifySLProof (Kernel.Proof assumptions linesSeen f) of
                                    Ok list ->
                                        case List.head (List.reverse list) of
                                            Just ( SententialLogic.SL1, _ ) ->
                                                Ok (deductionTheoremHandleAxiom formalAssumption f)

                                            Just ( SententialLogic.SL2, _ ) ->
                                                Ok (deductionTheoremHandleAxiom formalAssumption f)

                                            Just ( SententialLogic.SL3, _ ) ->
                                                Ok (deductionTheoremHandleAxiom formalAssumption f)

                                            Just ( SententialLogic.AlreadyProved, _ ) ->
                                                Ok (deductionTheoremHandleAxiom formalAssumption f)

                                            Just ( _, _ ) ->
                                                Ok (deductionTheoremHandleAxiom formalAssumption f)

                                            Nothing ->
                                                Err (unformalizeExpression f)

                                    Err _ ->
                                        case verifyModusPonensInAssumption formalAssumption (List.concat [ assumptions, linesSeen ]) f of
                                            Just ( a, needExpanding ) ->
                                                Ok
                                                    (List.concat
                                                        [ List.concat (List.map (deductionTheoremHandleAxiom formalAssumption) needExpanding)
                                                        , deductionTheoremHandleModusPonens formalAssumption a f
                                                        ]
                                                    )

                                            Nothing ->
                                                -- TODO: Check for other forms ([A, H->(A->B)], [A, A->B], [H->A, A->B] )
                                                -- or simply apply H=> to every step (without full deduction theorem)
                                                -- case verifyModusPonens formalAssumption linesSeen f of
                                                Err (unformalizeExpression f)
                    in
                    case newLinesR of
                        Ok newLines ->
                            applyDeductionTheorem (Just assumption) assumptions (List.concat [ linesSeen, newLines ]) fs
                                |> Result.andThen (\r -> Ok (List.concat [ newLines, r ]))

                        Err _ ->
                            Err (unformalizeExpression f)



-- TODO: This code can be cleaned up and made shorter
-- It returns back (
--    the left hand sind of the implication,
--    a list of lines to which the deduction theorem should also be applied to
-- )


verifyModusPonensInAssumption assumption previous expression =
    let
        implicationCandidates =
            List.filter
                (\p ->
                    case p of
                        Kernel.Implies h (Kernel.Implies a b) ->
                            h == assumption && b == expression

                        _ ->
                            False
                )
                previous

        implicationCandidates2 =
            List.filter
                (\p ->
                    case p of
                        Kernel.Implies a b ->
                            b == expression

                        _ ->
                            False
                )
                previous

        findJustification candidates =
            case candidates of
                first :: rest ->
                    case first of
                        Kernel.Implies _ (Kernel.Implies a _) ->
                            if List.member (Kernel.Implies assumption a) previous || a == assumption then
                                Just ( a, [] )

                            else if List.member a previous then
                                Just ( a, [ a ] )

                            else
                                findJustification rest

                        _ ->
                            findJustification rest

                empty ->
                    Nothing

        findJustification2 candidates =
            case candidates of
                first :: rest ->
                    case first of
                        Kernel.Implies a _ ->
                            if List.member (Kernel.Implies assumption a) previous || a == assumption then
                                Just ( a, [ first ] )

                            else if List.member a previous then
                                Just ( a, [ first, a ] )

                            else
                                findJustification rest

                        _ ->
                            findJustification rest

                empty ->
                    Nothing
    in
    case findJustification implicationCandidates of
        Just ( x, l ) ->
            Just ( x, l )

        Nothing ->
            findJustification2 implicationCandidates2


deductionTheoremHandleAxiom h a =
    [ a, sl1 a h, Kernel.Implies h a ]


deductionTheoremHandleModusPonens h a b =
    [ sl2 h a b, Kernel.Implies (Kernel.Implies h a) (Kernel.Implies h b), Kernel.Implies h b ]


deductionTheoremHandleAssumption h =
    pImpliesPProof (formalizeExpression h)


formalizeDeduction : Deduction -> Kernel.Sequence -> Result Expression Kernel.Sequence
formalizeDeduction proofSequence assumptions =
    formalizeDeductionHelper proofSequence assumptions []


formalizeDeductionHelper : Deduction -> Kernel.Sequence -> Kernel.Sequence -> Result Expression Kernel.Sequence
formalizeDeductionHelper proofSequence assumptions linesSeen =
    case proofSequence of
        Deduction maybeAssumption subdeductions ->
            let
                newAssumptions =
                    case maybeAssumption of
                        Just assumption ->
                            List.concat [ assumptions, [ formalizeExpression assumption ] ]

                        Nothing ->
                            assumptions

                fixSubListsR =
                    List.foldl
                        (\subdeduction ->
                            \accumR ->
                                case accumR of
                                    Err err ->
                                        Err err

                                    Ok accum ->
                                        case formalizeDeductionHelper subdeduction newAssumptions (List.concat [ linesSeen, accum ]) of
                                            Ok subCall ->
                                                Ok (List.concat [ accum, subCall ])

                                            Err err ->
                                                Err err
                        )
                        (Ok [])
                        subdeductions

                fixListR =
                    case fixSubListsR of
                        Ok fixSubLists ->
                            applyDeductionTheorem maybeAssumption assumptions linesSeen fixSubLists

                        Err err ->
                            Err err
            in
            fixListR

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
