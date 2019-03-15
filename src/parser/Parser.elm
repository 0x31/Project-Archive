module Parser exposing (parseProof, parseString, split, splitLines)

-- This is the kernel of the proof verifier. It only understands fully formal
-- sentential logic.
-- If this has any bugs in it, then all proofs should be considered invalid.

import Char exposing (Char)
import Debug exposing (log)
import Kernel exposing (Expression, Proof(..))
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


parseProof : String -> Maybe Proof
parseProof input =
    let
        ( goalLines, assumptionsLines, proofLines ) =
            groupLines (splitLines (tokenize input))

        maybeGoal =
            combine (List.map parseTokens goalLines)

        maybeAssumptions =
            combine (List.map parseTokens assumptionsLines)

        maybeProof =
            combine (List.map parseTokens proofLines)
    in
    case ( maybeGoal, maybeAssumptions, maybeProof ) of
        ( Just goal, Just assumptions, Just proof ) ->
            Just (Proof assumptions proof goal)

        _ ->
            Nothing


type alias Line =
    List Token


type alias Section =
    List Line


groupLines : Section -> ( Section, Section, Section )
groupLines allLines =
    groupLinesHelper allLines [] [] [] False False False


groupLinesHelper : Section -> Section -> Section -> Section -> Bool -> Bool -> Bool -> ( Section, Section, Section )
groupLinesHelper allLines goal assumptions proof seenGoal seenAssumptions seenProof =
    case allLines of
        [] ->
            ( goal, assumptions, proof )

        line :: rest ->
            case line of
                [ Word "GOAL" ] ->
                    groupLinesHelper rest goal assumptions proof True False False

                [ Word "ASSUMING" ] ->
                    groupLinesHelper rest goal assumptions proof False True False

                [ Word "PROOF" ] ->
                    groupLinesHelper rest goal assumptions proof False False True

                _ ->
                    if seenGoal then
                        groupLinesHelper rest (concat [ goal, [ line ] ]) assumptions proof seenGoal seenAssumptions seenProof

                    else if seenAssumptions then
                        groupLinesHelper rest goal (concat [ assumptions, [ line ] ]) proof seenGoal seenAssumptions seenProof

                    else if seenProof then
                        groupLinesHelper rest goal assumptions (concat [ proof, [ line ] ]) seenGoal seenAssumptions seenProof

                    else
                        groupLinesHelper rest goal assumptions proof seenGoal seenAssumptions seenProof


parseString : String -> Maybe Expression
parseString input =
    parseTokens (tokenize input)


splitLines : List Token -> Section
splitLines string =
    List.filter
        (\a -> not (a == []))
        (splitLinesHelper string [] [])


splitLinesHelper : List Token -> Line -> List Line -> List Line
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
            case ( treeToExpression a, treeToExpression c ) of
                ( Just aExpression, Just cExpression ) ->
                    case b of
                        Leaf Implies ->
                            Just (Kernel.Implies aExpression cExpression)

                        _ ->
                            Nothing

                _ ->
                    Nothing

        Node [ a, b ] ->
            case treeToExpression b of
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


split : Line -> Tree
split tokens =
    splitByBracket tokens


splitByBracket : Line -> Tree
splitByBracket string =
    Tuple.first (splitByBracketHelper string [])


splitByBracketHelper : Line -> List Tree -> ( Tree, Line )
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
