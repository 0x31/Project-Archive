module Builders exposing (modusPonens, pImpliesPProof, sl1, sl2, sl3)

import SemiFormal exposing (Expression(..))


modusPonens : Expression -> Expression -> Result String Expression
modusPonens implication lhs =
    case implication of
        Implies iLhs rhs ->
            if iLhs == lhs then
                Ok rhs

            else
                Err "second argument of MP must match the first argument's lhs"

        _ ->
            Err "first argument of MP must be implication"


sl1 : Expression -> Expression -> Expression
sl1 a b =
    Implies a (Implies b a)


sl2 : Expression -> Expression -> Expression -> Expression
sl2 a b c =
    Implies (Implies a (Implies b c)) (Implies (Implies a b) (Implies a c))


sl3 : Expression -> Expression -> Expression
sl3 a b =
    Implies (Implies (Not a) (Not b)) (Implies (Implies (Not a) b) a)


pImpliesPProof : Expression -> List Expression
pImpliesPProof p =
    let
        pp =
            Implies p p

        pIpp =
            Implies p pp
    in
    [ sl2 p pp p
    , sl1 p pp
    , Implies pIpp pp
    , pIpp
    , pp
    ]
