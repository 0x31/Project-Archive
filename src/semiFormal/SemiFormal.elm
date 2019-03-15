module SemiFormal exposing (Expression(..), Proof(..), Sequence, formalToString, formalizeExpression, formalizeProof, formalizeSequence, toString, unformalizeExpression)

import Kernel


type Expression
    = Not Expression
    | Implies Expression Expression
    | And Expression Expression
    | Or Expression Expression
    | Iff Expression Expression
    | Sentence String


type alias Sequence =
    List Expression


type
    Proof
    -- Proof(Assumptions, Proof, Goal)
    = Proof Sequence Sequence Sequence


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


formalizeSequence : Sequence -> Kernel.Sequence
formalizeSequence =
    List.map formalizeExpression


formalizeProof : Proof -> Kernel.Proof
formalizeProof (Proof assumptions proof goal) =
    Kernel.Proof (formalizeSequence assumptions) (formalizeSequence proof) (formalizeSequence goal)


toString : Expression -> String
toString expression =
    case expression of
        Not a ->
            "¬" ++ toString a

        Implies a b ->
            "(" ++ toString a ++ " ⇒ " ++ toString b ++ ")"

        And a b ->
            "(" ++ toString a ++ " ∧ " ++ toString b ++ ")"

        Or a b ->
            "(" ++ toString a ++ " ∨ " ++ toString b ++ ")"

        Iff a b ->
            "(" ++ toString a ++ " ⇔ " ++ toString b ++ ")"

        Sentence s ->
            s


formalToString : Kernel.Expression -> String
formalToString expression =
    toString (unformalizeExpression expression)
