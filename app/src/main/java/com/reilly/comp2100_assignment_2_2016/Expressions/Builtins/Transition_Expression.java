package com.reilly.comp2100_assignment_2_2016.Expressions.Builtins;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by ***REMOVED*** on 9/05/2016.
 */
public class Transition_Expression extends Expression {

    Expression child;

    public Transition_Expression(Expression arg) {
        this.child = arg;
    }

    @Override
    public String show() {
        return child.show();
    }

    @Override
    public Value evaluate() throws EvaluateError {
        return child.evaluate();
    }
}
