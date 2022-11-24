module SententialLogic exposing (AxiomName(..), sententialLogicRules, verifyModusPonens, verifySL1, verifySL2, verifySL3, verifySLProof)

import Kernel exposing (Expression(..), Rule, verifyAlreadyProved, verifyProof)



-- TODO: Change this so that they contain details about how they are used
-- (e.g. SL1 "a" or MP "a=>b" "a")


type AxiomName
    = AlreadyProved
    | SL1
    | SL2
    | SL3
    | MP



-- Verify the axioms


verifySL1 : Rule
verifySL1 assumptions previous expression =
    case expression of
        Implies a1 (Implies b1 a2) ->
            if a1 == a2 then
                True

            else
                False

        _ ->
            False


verifySL2 : Rule
verifySL2 assumptions previous expression =
    case expression of
        Implies (Implies a1 (Implies b1 c1)) (Implies (Implies a2 b2) (Implies a3 c2)) ->
            if a1 == a2 && a1 == a3 && b1 == b2 && c1 == c2 then
                True

            else
                False

        _ ->
            False


verifySL3 : Rule
verifySL3 assumptions previous expression =
    case expression of
        Implies (Implies (Not a1) (Not b1)) (Implies (Implies (Not a2) b2) a3) ->
            if a1 == a2 && a1 == a3 && b1 == b2 then
                True

            else
                False

        _ ->
            False



-- Verify the rules


verifyModusPonens : Rule
verifyModusPonens assumptions previous expression =
    let
        allPrevious =
            List.concat [ assumptions, previous ]

        implicationCandidates =
            Kernel.findImplicationCandidates allPrevious expression

        findJustification candidates =
            case candidates of
                first :: rest ->
                    case first of
                        Implies a _ ->
                            if List.member a allPrevious then
                                True

                            else
                                findJustification rest

                        _ ->
                            findJustification rest

                empty ->
                    False
    in
    findJustification implicationCandidates


sententialLogicRules =
    [ { rule = verifyAlreadyProved, name = AlreadyProved }
    , { rule = verifySL1, name = SL1 }
    , { rule = verifySL2, name = SL2 }
    , { rule = verifySL3, name = SL3 }
    , { rule = verifyModusPonens, name = MP }
    ]


verifySLProof =
    verifyProof sententialLogicRules
