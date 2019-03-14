module Tokenizer exposing (Token(..), tokenize)

import List exposing (concat)



-- TODO: Support multiple-character-long words


type Token
    = Implies
    | Not
    | LeftBracket
    | RightBracket
    | LineBreak
    | Word String


tokenize : String -> List Token
tokenize string =
    tokenizeHelper (String.toList string) [] []


tokenizeHelper : List Char -> List Char -> List Token -> List Token
tokenizeHelper string memory accumulator =
    let
        newAccumulator =
            if List.length memory > 0 then
                List.concat [ accumulator, [ Word (String.fromList memory) ] ]

            else
                accumulator
    in
    case string of
        f :: rest ->
            case f of
                '⇒' ->
                    tokenizeHelper rest [] (concat [ newAccumulator, [ Implies ] ])

                '(' ->
                    tokenizeHelper rest [] (concat [ newAccumulator, [ LeftBracket ] ])

                ')' ->
                    tokenizeHelper rest [] (concat [ newAccumulator, [ RightBracket ] ])

                '¬' ->
                    tokenizeHelper rest [] (concat [ newAccumulator, [ Not ] ])

                ' ' ->
                    tokenizeHelper rest [] newAccumulator

                '\t' ->
                    tokenizeHelper rest [] newAccumulator

                '\n' ->
                    tokenizeHelper rest [] (concat [ newAccumulator, [ LineBreak ] ])

                _ ->
                    tokenizeHelper rest (concat [ memory, [ f ] ]) accumulator

        [] ->
            newAccumulator
