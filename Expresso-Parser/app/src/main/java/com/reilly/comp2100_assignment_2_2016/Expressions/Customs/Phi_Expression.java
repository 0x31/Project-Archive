package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class Phi_Expression extends Expression {

    public Phi_Expression() {}

    @Override
    public String show() {
        return "ϕ";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        return new Value(1.6179775);
    }
}
