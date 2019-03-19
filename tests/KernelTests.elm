module KernelTests exposing (suite)

import Builders exposing (pImpliesPProof)
import Expect exposing (Expectation)
import Formalizer exposing (formalSequenceToDeduction, formalizeExpression, formalizeProof)
import Fuzz exposing (Fuzzer, int, list, string)
import Kernel exposing (Expression(..))
import Parser exposing (parseProof)
import Result exposing (andThen)
import Run exposing (formalizeAndRunProof)
import SemiFormal
import SententialLogic exposing (verifySLProof)
import Test exposing (..)
import ToString exposing (formalToString, toString)


proof1 =
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


proof2 =
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


proof3 =
    parseProof """
GOAL
¬(A ⇒ (¬B))
ASSUMING
A ∧ B
PROOF
A ∧ B
¬(A ⇒ (¬B))
"""


proof4 =
    Ok (SemiFormal.Proof [] (SemiFormal.Deduction Nothing [ pImpliesPProof (Sentence "p") |> formalSequenceToDeduction ]) (SemiFormal.Implies (SemiFormal.Sentence "p") (SemiFormal.Sentence "p")))


failingProof1 =
    parseProof """
GOAL
p ⇒ q
ASSUMING
PROOF
p ⇒ q
"""


proof5 =
    parseProof """
GOAL
p ⇒ p

ASSUMING

PROOF
| ASSUMING p
| p
p ⇒ p
"""


proof6 =
    parseProof """
GOAL
p ⇒ (p ⇒ (p ⇒ p))

ASSUMING

PROOF
| ASSUMING p
| p ⇒ (p ⇒ p)
p ⇒ (p ⇒ (p ⇒ p))
"""


proof7 =
    parseProof """
GOAL
p ⇒ (p ⇒ p)

ASSUMING
a ⇒ a

PROOF
| ASSUMING p
| | ASSUMING p
| | a ⇒ a
| p ⇒ (a ⇒ a)
p ⇒ (p ⇒ (a ⇒ a))
"""


proof8 =
    parseProof """
GOAL
A ⇒ C
ASSUMING
A ⇒ B
B ⇒ C
PROOF
| ASSUMING A
| B
| C
A ⇒ C
"""


failingProof2 =
    parseProof """
GOAL
B ⇒ A

ASSUMING

PROOF
| ASSUMING B
| B
B ⇒ B
| ASSUMING A
| B
B ⇒ A
"""


proof9 =
    parseProof """
GOAL
B ⇒ (A ⇒ C)
ASSUMING
A ⇒ (B ⇒ C)
PROOF
| ASSUMING B
| | ASSUMING A
| | B ⇒ C
| | C
| A ⇒ C
B ⇒ (A ⇒ C)
"""


proof10 =
    parseProof """
GOAL
B

ASSUMING
A
¬A

PROOF
A ⇒ ((¬B) ⇒ A)
(¬B) ⇒ A
(¬A) ⇒ ((¬B) ⇒ (¬A))
(¬B) ⇒ (¬A)
((¬B) ⇒ (¬A)) ⇒ (((¬B) ⇒ A) ⇒ B)
((¬B) ⇒ A) ⇒ B
B
"""


proof11 =
    parseProof """
This proof is long because it also contains a proof that `p ⇒ p`.

GOAL
A

ASSUMING
¬(¬A)

PROOF
(¬(¬A)) ⇒ ((¬A) ⇒ (¬(¬A)))
(¬A) ⇒ (¬(¬A))
((¬A) ⇒ (¬(¬A))) ⇒ (((¬A) ⇒ (¬A)) ⇒ A)
((¬A) ⇒ (¬A)) ⇒ A
((¬A) ⇒ (((¬A) ⇒ (¬A)) ⇒ (¬A))) ⇒ (((¬A) ⇒ ((¬A) ⇒ (¬A))) ⇒ ((¬A) ⇒ (¬A)))
(¬A) ⇒ (((¬A) ⇒ (¬A)) ⇒ (¬A))
((¬A) ⇒ ((¬A) ⇒ (¬A))) ⇒ ((¬A) ⇒ (¬A))
(¬A) ⇒ ((¬A) ⇒ (¬A))
(¬A) ⇒ (¬A)
A
"""


suite : Test
suite =
    describe "Kernel"
        [ describe "Kernel.verifySLProof"
            [ test "p ⇒ p" <| \_ -> formalizeAndRunProof proof1 |> Expect.ok
            , test "p ⇒ q, q ⇒ r ⊢ p ⇒ r" <| \_ -> formalizeAndRunProof proof2 |> Expect.ok
            , test "A ∧ B ⊢ ¬(A ⇒ (¬B))" <| \_ -> formalizeAndRunProof proof3 |> Expect.ok
            , test "p ⇒ p builder" <| \_ -> formalizeAndRunProof proof4 |> Expect.ok
            , test "assumption within assumption" <| \_ -> formalizeAndRunProof proof5 |> Expect.ok
            , test "axiom within assumption" <| \_ -> formalizeAndRunProof proof6 |> Expect.ok
            , test "parsing assumptions" <| \_ -> formalizeAndRunProof proof7 |> Expect.ok
            , test "A ⇒ B, B ⇒ C ⊢ A ⇒ C" <| \_ -> formalizeAndRunProof proof8 |> Expect.ok
            , test "(A ⇒ (B ⇒ C)) ⊢ (B ⇒ (A ⇒ C))" <| \_ -> formalizeAndRunProof proof9 |> Expect.ok
            , test "inconsistency" <| \_ -> formalizeAndRunProof proof10 |> Expect.ok
            , test "double negation" <| \_ -> formalizeAndRunProof proof11 |> Expect.ok
            , test "p ⇒ q should fail" <| \_ -> formalizeAndRunProof failingProof1 |> Expect.err
            , test "assumption scope" <| \_ -> formalizeAndRunProof failingProof2 |> Expect.err
            ]
        ]
