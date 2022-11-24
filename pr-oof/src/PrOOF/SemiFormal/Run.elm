module Run exposing (formalizeAndRunProof)

import Formalizer exposing (formalizeProof)
import Kernel
import Result exposing (andThen)
import SemiFormal
import SententialLogic exposing (verifySLProof)
import ToString exposing (formalToString, toString)


printFormalProof : Kernel.Proof -> Kernel.Proof
printFormalProof (Kernel.Proof assumptions proof goal) =
    let
        print1 =
            Debug.log "\n" ""
    in
    let
        print2 =
            Debug.log "GOAL: " (formalToString goal)
    in
    let
        print3 =
            List.foldl
                (\line ->
                    \accum ->
                        Debug.log "| " (formalToString line)
                )
                ""
                proof
    in
    Kernel.Proof assumptions proof goal


printDeduction : SemiFormal.Deduction -> Int -> String
printDeduction deduction indent =
    let
        indentation =
            if indent > -1 then
                String.repeat indent "| "

            else
                ""
    in
    case deduction of
        SemiFormal.Expr expr ->
            Debug.log indentation (toString expr)

        SemiFormal.Deduction maybeAssumption subdeductions ->
            let
                assumptionString =
                    case maybeAssumption of
                        Just assumption ->
                            Debug.log (indentation ++ "| ") ("ASSUMING " ++ toString assumption)

                        Nothing ->
                            "nothing"
            in
            let
                subdeductionsString =
                    List.foldl (\sub -> \accum -> printDeduction sub (indent + 1)) "" subdeductions
            in
            ""


printProof : SemiFormal.Proof -> SemiFormal.Proof
printProof (SemiFormal.Proof assumptions proof goal) =
    let
        print1 =
            Debug.log "\n" ""
    in
    let
        print2 =
            Debug.log "GOAL:\n" (toString goal)
    in
    let
        print3 =
            Debug.log "PROOF:\n" (printDeduction proof -1)
    in
    SemiFormal.Proof assumptions proof goal


formalizeAndRunProof : Result.Result String SemiFormal.Proof -> Result.Result String (List ( SententialLogic.AxiomName, Kernel.Theorem ))
formalizeAndRunProof resultProof =
    resultProof
        |> andThen
            (\proofR ->
                case
                    formalizeProof
                        proofR
                of
                    -- (printProof proofR) of
                    Ok proof ->
                        case
                            verifySLProof
                                proof
                        of
                            -- (printFormalProof proof) of
                            Ok res ->
                                Ok res

                            Err (Just expr) ->
                                Err ("couldn't verify line " ++ formalToString expr)

                            Err Nothing ->
                                Err "couldn't verify proof"

                    Err expr ->
                        Err ("couldn't formalize line '" ++ toString expr ++ "'")
            )
