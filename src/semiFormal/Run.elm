module Run exposing (formalizeAndRunProof)

import Formalizer exposing (formalizeProof)
import Kernel
import Result exposing (andThen)
import SemiFormal
import SententialLogic exposing (verifySLProof)
import ToString exposing (formalToString, toString)


formalizeAndRunProof : Result.Result String SemiFormal.Proof -> Result.Result String (List ( SententialLogic.AxiomName, Kernel.Theorem ))
formalizeAndRunProof resultProof =
    resultProof
        |> andThen
            (\proofR ->
                case formalizeProof proofR of
                    Ok proof ->
                        case verifySLProof proof of
                            -- (printProof proof) of
                            Ok res ->
                                Ok res

                            Err (Just expr) ->
                                Err ("couldn't verify line " ++ formalToString expr)

                            Err Nothing ->
                                Err "couldn't verify proof"

                    Err expr ->
                        Err ("couldn't parse line " ++ toString expr)
            )
