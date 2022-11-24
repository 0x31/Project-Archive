pr(oof)
=======

This is a program for verifying propositional/sentential logic proofs.

It is currently broken into three parts:

* The kernel (`src/FullyFormal`) - minimal proof verifier

TODO
----

* Use `elm/parser` for a more robust & maintainable parser
* Load proofs from files
* Import other proofs to be re-used
* Annotate proofs with step details (e.g. `MP 1, 2` or `SL1 "p" "p → p"`)
* Web-based UI


Usage
-----

(`npm install -g elm && elm repl`)

Proof that `p → p`

```elm
import ProofParser exposing (parseProof)
import Run exposing (formalizeAndRunProof)

""" \
GOAL         \
p ⇒ p        \
             \
ASSUMING     \
             \
PROOF        \
| ASSUMING p \
| p          \
p ⇒ p        \
""" \
|> parseProof \
|> formalizeAndRunProof

-- Ok (_, Theorem [] (Implies (Sentence "p") (Sentence "p")))
```
