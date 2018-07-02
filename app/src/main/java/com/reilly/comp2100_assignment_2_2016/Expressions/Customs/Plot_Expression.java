package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by ***REMOVED*** on 9/05/2016.
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
