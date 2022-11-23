package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class Asin_Expression extends Expression {

    Expression parameter;

    public Asin_Expression(Expression parameter) {
        this.parameter = parameter;
    }

    @Override
    public String show() {
        return "asin("+parameter.show()+")";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        Value left = parameter.evaluate();

        Double ret = Math.asin(left.value);
        if(Double.isNaN(ret)) throw new EvaluateError("Invalid value for asin");

        return new Value(ret,left);
    }
}
