package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

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
