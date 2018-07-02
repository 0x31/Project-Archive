package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by ***REMOVED*** on 9/05/2016.
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
