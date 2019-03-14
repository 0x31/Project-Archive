pr(oof)
=======

This is a program for verifying Propositional/Sentential logic proofs.

TODO
----

* Syntax parsing (parse `"p → p"` to `Implies (Sentence 'p') (Sentence 'p')`)
* Annotate proofs with step details (e.g. `MP 1, 2` or `SL1 "p" "p → p"`)
* Web-based UI


Usage
-----

Proof that `p → p`

(`npm install -g elm && elm repl`)

```elm
import Kernel exposing (verifyProof, Expression(..))

p = Sentence 'p'

proof = \
    [ Implies (Implies p (Implies (Implies p p) p)) (Implies (Implies p (Implies p p)) (Implies p p)) \
    , Implies p (Implies (Implies p p) p) \
    , Implies (Implies p (Implies p p)) (Implies p p) \
    , Implies p (Implies p p) \
    , Implies p p \
    ]

verifyProof [] proof (Implies p p)
-- True
```