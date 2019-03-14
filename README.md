pr(oof)
=======

This is a program for verifying Propositional/Sentential logic proofs.

TODO
----

* Run parser over a file
* Annotate proofs with step details (e.g. `MP 1, 2` or `SL1 "p" "p → p"`)
* Web-based UI


Usage
-----

(`npm install -g elm && elm repl`)

Proof that `p → p`

```elm
import Kernel exposing (verifyTheorem, Expression(..), Theorem(..))

p = Sentence "p'"

proof = \
    [ Implies (Implies p (Implies (Implies p p) p)) (Implies (Implies p (Implies p p)) (Implies p p)) \
    , Implies p (Implies (Implies p p) p) \
    , Implies (Implies p (Implies p p)) (Implies p p) \
    , Implies p (Implies p p) \
    , Implies p p \
    ]

verifyTheorem (Proof [] proof (Implies p p))

-- True
```

Syntax parsing:


```elm
import Kernel exposing (verifyTheorem, Expression(..), Theorem(..))
import Parser exposing (parseString, parseProof)


maybeGoal = \
    parseString "A ⇒ B"


maybeAssumptions = \
    parseProof """ \
A ⇒ C  \
C ⇒ B  \
"""


maybeProof = \
    parseProof """                  \
(C ⇒ B) ⇒ (A ⇒ (C ⇒ B))             \
C ⇒ B                               \
A ⇒ (C ⇒ B)                         \
(A ⇒ (C ⇒ B)) ⇒ ((A ⇒ C) ⇒ (A ⇒ B)) \
(A ⇒ C) ⇒ (A ⇒ B)                   \
A ⇒ C                               \
A ⇒ B                               \
"""


run = \
    case ( maybeGoal, maybeAssumptions, maybeProof ) of \
        ( Just goal, Just assumptions, Just proof ) -> verifyTheorem (Proof assumptions proof goal) \
        _ -> False \


```