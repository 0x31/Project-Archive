package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class Cos_Expression extends Expression {

    Expression parameter;

    public Cos_Expression(Expression parameter) {
        this.parameter = parameter;
    }

    @Override
    public String show() {
        return "cos("+parameter.show()+")";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        Value left = parameter.evaluate();

        Double ret = Math.cos(left.value);
        if(Double.isNaN(ret)) throw new EvaluateError("Invalid value for cos");

        return new Value(ret,left);
    }
}
