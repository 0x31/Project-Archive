module Parser exposing (parseProof, parseString, split, splitLines)

import Char exposing (Char)
import Debug
import List exposing (concat, filter, foldl, head, member, reverse)
import Result.Extra exposing (combine)
import SemiFormal exposing (Deduction(..), Expression, Proof(..))
import Tokenizer exposing (Token(..), tokenize)
import Tuple exposing (first)


parseProof : String -> Result String Proof
parseProof input =
    let
        ( goalLines, assumptionsLines, proofLines ) =
            groupLines (splitLines (tokenize input))

        maybeGoal =
            combine (List.map parseExpression goalLines)

        maybeAssumptions =
            combine (List.map parseExpression assumptionsLines)

        maybeProof =
            parseProofBody proofLines
    in
    case ( maybeGoal, maybeAssumptions, maybeProof ) of
        ( Ok [ goal ], Ok assumptions, Ok proof ) ->
            Ok (Proof assumptions (Deduction Nothing [ proof ]) goal)

        ( Ok _, _, _ ) ->
            Err "invalid number of goals"

        _ ->
            Err "unable to parse"


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


parseString : String -> Result String Expression
parseString input =
    parseExpression (tokenize input)


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


parseExpression : List Token -> Result String Expression
parseExpression tokens =
    treeToExpression (split tokens)


parseProofBody : List (List Token) -> Result String Deduction
parseProofBody proofBody =
    combine (List.map parseExpression proofBody)
        |> Result.andThen (\list -> Ok (List.map (\expr -> Expr expr) list))
        |> Result.andThen (\result -> Ok (Deduction Nothing result))


type Tree
    = Node (List Tree)
    | Symbol Token



-- This is very simplistic parser, and is unable to handle precedence (every
-- operation must be bracketed). e.g. `a ⇒ ¬b` must be `a ⇒ (¬b)`


treeToExpression : Tree -> Result String Expression
treeToExpression tree =
    case tree of
        Node [ a, b, c ] ->
            case ( treeToExpression a, treeToExpression c ) of
                ( Ok aExpression, Ok cExpression ) ->
                    case b of
                        Symbol Implies ->
                            Ok (SemiFormal.Implies aExpression cExpression)

                        Symbol And ->
                            Ok (SemiFormal.And aExpression cExpression)

                        Symbol Or ->
                            Ok (SemiFormal.Or aExpression cExpression)

                        Symbol Iff ->
                            Ok (SemiFormal.Iff aExpression cExpression)

                        _ ->
                            Err ("unable to parse " ++ Debug.toString b)

                ( Err _, _ ) ->
                    Err ("unable to parse " ++ Debug.toString a)

                ( _, Err _ ) ->
                    Err ("unable to parse " ++ Debug.toString c)

        Node [ a, b ] ->
            case treeToExpression b of
                Ok bExpression ->
                    case a of
                        Symbol Not ->
                            Ok (SemiFormal.Not bExpression)

                        _ ->
                            Err ("unable to parse " ++ Debug.toString a)

                _ ->
                    Err ("unable to parse " ++ Debug.toString b)

        Node [ a ] ->
            treeToExpression a

        Symbol a ->
            case a of
                Word p ->
                    Ok (SemiFormal.Sentence p)

                _ ->
                    Err ("unable to parse " ++ Debug.toString a)

        _ ->
            Err ("unable to parse " ++ Debug.toString tree)



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
            splitByBracketHelper rest (List.concat [ currentTree, [ Symbol token ] ])

        [] ->
            ( returnTree currentTree, [] )
