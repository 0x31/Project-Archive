package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class Tan_Expression extends Expression {

    Expression parameter;

    public Tan_Expression(Expression parameter) {
        this.parameter = parameter;
    }

    @Override
    public String show() {
        return "tan("+parameter.show()+")";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        Value left = parameter.evaluate();

        Double ret = Math.tan(left.value);
        if(Double.isNaN(ret)) throw new EvaluateError("Invalid value for tan");

        return new Value(ret,left);
    }
}
