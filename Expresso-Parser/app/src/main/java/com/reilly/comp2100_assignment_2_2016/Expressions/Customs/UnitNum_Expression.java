package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class UnitNum_Expression extends Expression {

    Expression left;
    Expression right;

    public UnitNum_Expression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String show() {
        return left.show()+" + " + right.show();
    }

    @Override
    public Value evaluate() throws EvaluateError {
        return new Value(left.evaluate().value,right.show());
    }
}
