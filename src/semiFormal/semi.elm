module Main exposing (modusPonens, sl1, sl2, sl3)

import Kernel exposing (Expression(..))


modusPonens : Expression -> Expression -> Maybe Expression
modusPonens implication lhs =
    case implication of
        Implies iLhs rhs ->
            if iLhs == lhs then
                Just rhs

            else
                Nothing

        _ ->
            Nothing


sl1 : Expression -> Expression -> Expression
sl1 a b =
    Implies a (Implies b a)


sl2 : Expression -> Expression -> Expression -> Expression
sl2 a b c =
    Implies (Implies a (Implies b c)) (Implies (Implies a b) (Implies a c))


sl3 : Expression -> Expression -> Expression
sl3 a b =
    Implies (Implies (Not a) (Not b)) (Implies (Implies (Not a) b) a)



-- let
--     proof =
--         Proof assumptions2 proof2 goal2
-- in
-- -- log "Proof1"
-- -- (verifySLProof assumptions1 proof1 goal1)
-- log "Proof2" (verifySLProof proof)
-- manualProof =
--     let
--         line1 =
--             sl2 p (Implies p p) p
--         line2 =
--             sl1 p (Implies p p)
--         line3 =
--             modusPonens line1 line2
--         line4 =
--             sl1 p p
--         line5 =
--             modusPonens line3 line4
--     in
--     line5 == goal
