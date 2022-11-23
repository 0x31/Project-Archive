package com.parser.Expressions.Builtins;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
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
