package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by reill_000 on 14/05/2016.
 */
public class Unit_Expression extends Expression {

    Expression child;

    public Unit_Expression(Expression child) {
        this.child = child;
    }
    @Override
    public String show() {
        return child.show();
    }

    @Override
    public Value evaluate() throws EvaluateError {
        return new Value(0);
    }
}
