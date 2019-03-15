module Testing exposing (maybeProof1, maybeProof2, run)

import Kernel exposing (verifySLProof)
import Parser exposing (parseProof)


maybeProof1 =
    parseProof """
GOAL
p ⇒ p

ASSUMING

PROOF
(p ⇒ ((p ⇒ p) ⇒ p)) ⇒ ((p ⇒ (p ⇒ p)) ⇒ (p ⇒ p))
p ⇒ ((p ⇒ p) ⇒ p)
(p ⇒ (p ⇒ p)) ⇒ (p ⇒ p)
p ⇒ (p ⇒ p)
p ⇒ p
"""


maybeProof2 =
    parseProof """
GOAL
p ⇒ r

ASSUMING
p ⇒ q
q ⇒ r

PROOF
(q ⇒ r) ⇒ (p ⇒ (q ⇒ r))
q ⇒ r
p ⇒ (q ⇒ r)
(p ⇒ (q ⇒ r)) ⇒ ((p ⇒ q) ⇒ (p ⇒ r))
(p ⇒ q) ⇒ (p ⇒ r)
p ⇒ q
p ⇒ r
"""


run =
    case maybeProof2 of
        Just proof ->
            Just (verifySLProof proof)

        _ ->
            Nothing
