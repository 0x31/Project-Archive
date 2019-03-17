module ToString exposing (toString, formalToString)

import SemiFormal exposing (Expression(..))
import Formalizer exposing (unformalizeExpression)
import Kernel

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
