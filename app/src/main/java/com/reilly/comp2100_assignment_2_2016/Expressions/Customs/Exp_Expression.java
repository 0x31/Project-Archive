package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by ***REMOVED*** on 9/05/2016.
 */
public class Exp_Expression extends Expression {

    Expression parameter;

    public Exp_Expression(Expression parameter) {
        this.parameter = parameter;
    }

    @Override
    public String show() {
        return "e^("+parameter.show()+")";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        Value left = parameter.evaluate();

        Double ret = Math.exp(left.value);
        if(Double.isNaN(ret)) throw new EvaluateError("Invalid value for exp");

        return new Value(ret,left);
    }
}
