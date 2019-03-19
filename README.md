pr(oof)
=======

[![Build Status](https://travis-ci.org/***REMOVED***/pr-oof.svg?branch=master)](https://travis-ci.org/***REMOVED***/pr-oof)

This is a program for verifying Propositional/Sentential logic proofs.

TODO
----

* Run parser over a file
* Import other proofs to be re-used
* Annotate proofs with step details (e.g. `MP 1, 2` or `SL1 "p" "p → p"`)
* Web-based UI


Usage
-----

(`npm install -g elm && elm repl`)

Proof that `p → p`

```elm
import Parser exposing (parseProof)
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
