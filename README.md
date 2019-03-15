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
import Kernel exposing (verifySLProof)
import Parser exposing (parseProof)
import SemiFormal exposing (formalizeProof)

parsedProof = parseProof """ \
GOAL                                            \
p ⇒ p                                           \
                                                \
ASSUMING                                        \
                                                \
PROOF                                           \
(p ⇒ ((p ⇒ p) ⇒ p)) ⇒ ((p ⇒ (p ⇒ p)) ⇒ (p ⇒ p)) \
p ⇒ ((p ⇒ p) ⇒ p)                               \
(p ⇒ (p ⇒ p)) ⇒ (p ⇒ p)                         \
p ⇒ (p ⇒ p)                                     \
p ⇒ p                                           \
"""

run = case parsedProof of \
        Ok proof -> Ok (verifySLProof (formalizeProof proof)) \
        Err msg -> Err msg

-- Ok
```
