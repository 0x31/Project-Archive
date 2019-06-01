package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class Acos_Expression extends Expression {

    Expression parameter;

    public Acos_Expression(Expression parameter) {
        this.parameter = parameter;
    }

    @Override
    public String show() {
        return "acos("+parameter.show()+")";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        Value left = parameter.evaluate();

        Double ret = Math.acos(left.value);
        if(Double.isNaN(ret)) throw new EvaluateError("Invalid value for acos");

        return new Value(ret,left);
    }
}
