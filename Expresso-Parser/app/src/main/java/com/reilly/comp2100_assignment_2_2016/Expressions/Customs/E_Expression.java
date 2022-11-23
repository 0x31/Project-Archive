package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class E_Expression extends Expression {

    public E_Expression() {}

    @Override
    public String show() {
        return "e";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        return new Value(Math.E);
    }
}
