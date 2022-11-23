package com.parser.Expressions.Builtins;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class Int_builtin extends Expression {

    String value;

    public Int_builtin(String value) {
        this.value = value;
    }

    @Override
    public String show() {
        return value;
    }

    @Override
    public Value evaluate() throws EvaluateError {
        try {
            return new Value(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            throw new EvaluateError("Invalid number");
        }
    }
}
