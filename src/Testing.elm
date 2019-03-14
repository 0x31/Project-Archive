module Testing exposing (run)

import Array exposing (fromList)
import Debug exposing (log)
import Kernel exposing (..)
import List exposing (concat)


p : Expression
p =
    Sentence 'p'


goal1 =
    Implies p p


assumptions1 =
    []


proof1 =
    [ Implies (Implies p (Implies (Implies p p) p)) (Implies (Implies p (Implies p p)) (Implies p p))
    , Implies p (Implies (Implies p p) p)
    , Implies (Implies p (Implies p p)) (Implies p p)
    , Implies p (Implies p p)
    , Implies p p
    ]


goal2 =
    p


assumptions2 =
    [ p ]


proof2 =
    concat [ proof1, [ p ] ]


run =
    -- log "Proof1"
    -- (verifyProof assumptions1 proof1 goal1)
    log
        "Proof2"
        (verifyProof assumptions2 proof2 goal2)



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
-- modusPonens : Expression -> Expression -> Maybe Expression
-- modusPonens implication lhs =
--     case implication of
--         Implies iLhs rhs ->
--             if iLhs == lhs then
--                 Just rhs
--             else
--                 Nothing
--         _ ->
--             Nothing
-- sl1 : Expression -> Expression -> Expression
-- sl1 a b =
--     Implies a (Implies b a)
-- sl2 : Expression -> Expression -> Expression -> Expression
-- sl2 a b c =
--     Implies (Implies a (Implies b c)) (Implies (Implies a b) (Implies a c))
-- sl3 : Expression -> Expression -> Expression
-- sl3 a b =
--     Implies (Implies (Not a) (Not b)) (Implies (Implies (Not a) b) a)
