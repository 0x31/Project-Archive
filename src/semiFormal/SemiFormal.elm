module SemiFormal exposing (Deduction(..), Expression(..), Proof(..), formalToString, formalizeDeduction, formalizeExpression, formalizeProof, sequenceToDeduction, toString, unformalizeExpression)

import Kernel


type
    Expression
    -- Fully formal
    = Not Expression
    | Implies Expression Expression
    | Sentence String
      -- Semi formal
    | And Expression Expression
    | Or Expression Expression
    | Iff Expression Expression



-- | Assuming Expression Expression


type
    Deduction
    -- Deduction assumption subdeductions
    = Deduction (Maybe Expression) (List Deduction)
    | Expr Expression


type
    Proof
    -- Proof(Assumptions, Proof, Goal)
    = Proof (List Expression) Deduction Expression


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
                    case Kernel.verifySLProof (Kernel.Proof [] [] formalF) of
                        Ok ( axiomName, _ ) ->
                            applyDeductionTheorem (Just assumption) sequence

                        Err _ ->
                            Err f


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


toString : Expression -> String
toString expression =
    case expression of
        Not a ->
            "¬" ++ toString a

        Implies a b ->
            "(" ++ toString a ++ " ⇒ " ++ toString b ++ ")"

        Sentence s ->
            s

        And a b ->
            "(" ++ toString a ++ " ∧ " ++ toString b ++ ")"

        Or a b ->
            "(" ++ toString a ++ " ∨ " ++ toString b ++ ")"

        Iff a b ->
            "(" ++ toString a ++ " ⇔ " ++ toString b ++ ")"


formalToString : Kernel.Expression -> String
formalToString expression =
    toString (unformalizeExpression expression)
