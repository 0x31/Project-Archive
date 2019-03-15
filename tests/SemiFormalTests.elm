module SemiFormalTests exposing (suite)

import Expect exposing (Expectation)
import Fuzz exposing (Fuzzer, int, list, string)
import Kernel exposing (Expression(..), verifySLProof)
import Parser exposing (parseString)
import SemiFormal exposing (formalToString, formalizeExpression)
import Test exposing (..)


shouldFormalizeTo left right =
    \_ ->
        case parseString left of
            Ok expression ->
                formalizeExpression expression
                    |> formalToString
                    |> Expect.equal right

            Err msg ->
                Expect.fail msg


suite : Test
suite =
    describe "Kernel"
        [ describe "Kernel.verifySLProof"
            [ test "a" <| shouldFormalizeTo "a" "a"
            , test "A ∧ B" <| shouldFormalizeTo "A ∧ B" "¬(A ⇒ ¬B)"
            , test "A ∨ B" <| shouldFormalizeTo "A ∨ B" "(¬A ⇒ B)"
            , test "A ⇔ B" <| shouldFormalizeTo "A ⇔ B" "¬((A ⇒ B) ⇒ ¬(B ⇒ A))"
            ]
        ]
