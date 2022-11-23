package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class Plot_Expression extends Expression {

    Expression parameter;

    public Plot_Expression(Expression parameter) {
        this.parameter = parameter;
    }

    @Override
    public String show() {
        return "plot("+parameter.show()+")";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        return new Value(0.0);
    }
}
