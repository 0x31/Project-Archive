module Parser exposing (parseProof, parseString, split, splitLines)

import Char exposing (Char)
import Debug
import List exposing (concat, filter, foldl, head, member, reverse)
import List.Extra exposing (getAt)
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

        ( Ok (f :: s :: fs), _, _ ) ->
            Err "invalid number of goals"

        ( Err err, _, _ ) ->
            Err err

        ( _, Err err, _ ) ->
            Err err

        ( _, _, Err err ) ->
            Err err

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


countAssumptions : List Token -> Int
countAssumptions tokens =
    case tokens of
        [] ->
            0

        SubdeductionBox :: rest ->
            1 + countAssumptions rest

        _ :: rest ->
            countAssumptions rest


splitSubdeductions : List (List Token) -> Result String (List Deduction)
splitSubdeductions proofBody =
    case splitSubdeductionsHelper2 proofBody 0 of
        Ok ( result, _ ) ->
            Ok result

        Err msg ->
            Err msg



-- FIXME: This probably has some bugs in it.
-- It can probably be simplified a lot.


splitSubdeductionsHelper2 : List (List Token) -> Int -> Result String ( List Deduction, List (List Token) )
splitSubdeductionsHelper2 proofBody assumptions =
    case proofBody of
        [] ->
            Ok ( [], [] )

        f :: fs ->
            let
                assumptionCount =
                    countAssumptions f
            in
            -- If there is one more assumption, parse subdeduction
            if (assumptionCount == assumptions + 1) && (getAt (assumptions + 1) f == Just (Word "ASSUMING")) then
                case splitSubdeductionsHelper2 fs (assumptions + 1) of
                    Ok ( subdeduction, rest ) ->
                        case splitSubdeductionsHelper2 rest assumptions of
                            Ok ( restOfDeduction, rest2 ) ->
                                let
                                    assumptionR =
                                        -- Remove "|" and "ASSUMING"
                                        List.drop (assumptions + 1 + 1) f |> parseExpression
                                in
                                case assumptionR of
                                    Ok assumption ->
                                        Ok ( List.concat [ [ Deduction (Just assumption) subdeduction ], restOfDeduction ], rest2 )

                                    Err msg ->
                                        Err msg

                            Err msg ->
                                Err msg

                    Err msg ->
                        Err msg

            else if assumptionCount == assumptions then
                let
                    proofLineR =
                        -- Remove "|"
                        List.drop assumptions f |> parseExpression
                in
                case ( proofLineR, splitSubdeductionsHelper2 fs assumptions ) of
                    ( Ok parsedF, Ok ( result, rest ) ) ->
                        Ok ( List.concat [ [ Expr parsedF ], result ], rest )

                    _ ->
                        Err (Debug.toString ( proofLineR, splitSubdeductionsHelper2 fs assumptions ))

            else
                Ok ( [], f :: fs )


parseProofBody : List (List Token) -> Result String Deduction
parseProofBody proofBody =
    splitSubdeductions proofBody
        |> Result.andThen (\deductions -> Ok (Deduction Nothing deductions))


type ExpressionTree
    = Node (List ExpressionTree)
    | Symbol Token



-- This is very simplistic parser, and is unable to handle precedence (every
-- operation must be bracketed). e.g. `a ⇒ ¬b` must be `a ⇒ (¬b)`


treeToExpression : ExpressionTree -> Result String Expression
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
                            Err ("unable to parse (0) " ++ Debug.toString b)

                ( Err _, _ ) ->
                    Err ("unable to parse (1) " ++ Debug.toString a)

                ( _, Err _ ) ->
                    Err ("unable to parse (2) " ++ Debug.toString c)

        Node [ a, b ] ->
            case treeToExpression b of
                Ok bExpression ->
                    case a of
                        Symbol Not ->
                            Ok (SemiFormal.Not bExpression)

                        _ ->
                            Err ("unable to parse (3) " ++ Debug.toString a)

                _ ->
                    Err ("unable to parse (4) " ++ Debug.toString b)

        Node [ a ] ->
            treeToExpression a

        Symbol a ->
            case a of
                Word p ->
                    Ok (SemiFormal.Sentence p)

                _ ->
                    Err ("unable to parse (5) " ++ Debug.toString a)

        _ ->
            Err ("unable to parse (6) " ++ Debug.toString tree)



-- parseExpressionTree: String -> ExpressionTree
-- parseExpressionTree input = parseExpressionTreeHelper input ""
-- parseExpressionTreeHelper: String -> String -> (ExpressionTree, String)
-- parseExpressionTreeHelper input carry = case input of
-- '('::rest -> let []


split : Line -> ExpressionTree
split tokens =
    splitByBracket tokens


splitByBracket : Line -> ExpressionTree
splitByBracket string =
    Tuple.first (splitByBracketHelper string [])


splitByBracketHelper : Line -> List ExpressionTree -> ( ExpressionTree, Line )
splitByBracketHelper string currentExpressionTree =
    let
        returnExpressionTree tree =
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
                    splitByBracketHelper newRest (List.concat [ currentExpressionTree, [ child ] ])

        RightBracket :: rest ->
            ( returnExpressionTree currentExpressionTree, rest )

        token :: rest ->
            splitByBracketHelper rest (List.concat [ currentExpressionTree, [ Symbol token ] ])

        [] ->
            ( returnExpressionTree currentExpressionTree, [] )
