module Parser exposing (parseProof, parseString, split, splitLines)

-- This is the kernel of the proof verifier. It only understands fully formal
-- sentential logic.
-- If this has any bugs in it, then all proofs should be considered invalid.

import Char exposing (Char)
import Debug exposing (log)
import Kernel exposing (Expression, Theorem(..))
import List exposing (concat, filter, foldl, head, member, reverse)
import Tokenizer exposing (Token(..), tokenize)
import Tuple exposing (first)


combine : List (Maybe a) -> Maybe (List a)
combine maybeList =
    let
        step element acc =
            case element of
                Nothing ->
                    Nothing

                Just x ->
                    Maybe.map ((::) x) acc
    in
    List.foldr step (Just []) maybeList


parseProof : String -> Maybe (List Expression)
parseProof input =
    combine (List.map parseTokens (splitLines (tokenize input)))


parseString : String -> Maybe Expression
parseString input =
    parseTokens (tokenize input)


splitLines : List Token -> List (List Token)
splitLines string =
    List.filter
        (\a -> not (a == []))
        (splitLinesHelper string [] [])


splitLinesHelper : List Token -> List Token -> List (List Token) -> List (List Token)
splitLinesHelper string memory accum =
    case string of
        f :: fs ->
            if f == LineBreak then
                splitLinesHelper fs [] (concat [ accum, [ memory ] ])

            else
                splitLinesHelper fs (concat [ memory, [ f ] ]) accum

        [] ->
            concat [ accum, [ memory ] ]


parseTokens : List Token -> Maybe Expression
parseTokens tokens =
    treeToExpression (split tokens)


type Tree
    = Node (List Tree)
    | Leaf Token


treeToExpression : Tree -> Maybe Expression
treeToExpression tree =
    case tree of
        Node [ a, b, c ] ->
            let
                maybeAExpression =
                    treeToExpression a

                maybeCExpression =
                    treeToExpression c
            in
            case ( maybeAExpression, maybeCExpression ) of
                ( Just aExpression, Just cExpression ) ->
                    case b of
                        Leaf Implies ->
                            Just (Kernel.Implies aExpression cExpression)

                        _ ->
                            Nothing

                _ ->
                    Nothing

        Node [ a, b ] ->
            let
                maybeBExpression =
                    treeToExpression b
            in
            case maybeBExpression of
                Just bExpression ->
                    case a of
                        Leaf Not ->
                            Just (Kernel.Not bExpression)

                        _ ->
                            Nothing

                _ ->
                    Nothing

        Node [ a ] ->
            treeToExpression a

        Leaf a ->
            case a of
                Word p ->
                    Just (Kernel.Sentence p)

                _ ->
                    Nothing

        _ ->
            Nothing



-- parseTree: String -> Tree
-- parseTree input = parseTreeHelper input ""
-- parseTreeHelper: String -> String -> (Tree, String)
-- parseTreeHelper input carry = case input of
-- '('::rest -> let []


split : List Token -> Tree
split tokens =
    splitByBracket tokens


splitByBracket : List Token -> Tree
splitByBracket string =
    Tuple.first (splitByBracketHelper string [])


splitByBracketHelper : List Token -> List Tree -> ( Tree, List Token )
splitByBracketHelper string currentTree =
    let
        returnTree tree =
            case tree of
                (Node f) :: [] ->
                    Node f

                _ ->
                    Node tree
    in
    case string of
        LeftBracket :: rest ->
            case splitByBracketHelper rest [] of
                ( child, newRest ) ->
                    splitByBracketHelper newRest (List.concat [ currentTree, [ child ] ])

        RightBracket :: rest ->
            ( returnTree currentTree, rest )

        token :: rest ->
            splitByBracketHelper rest (List.concat [ currentTree, [ Leaf token ] ])

        [] ->
            ( returnTree currentTree, [] )
