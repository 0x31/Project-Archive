module SemiFormal exposing (Deduction(..), Expression(..), Proof(..))

import Kernel
import SententialLogic


type
    Expression
    -- Fully formal
    = Not Expression
    | Implies Expression Expression
    | Sentence String
      -- Semi formal
    | And Expression Expression
    | Or Expression Expression
    | Iff Expression Expression



-- | Assuming Expression Expression


type
    Deduction
    -- Deduction assumption subdeductions
    = Deduction (Maybe Expression) (List Deduction)
    | Expr Expression


type
    Proof
    -- Proof(Assumptions, Proof, Goal)
    = Proof (List Expression) Deduction Expression
