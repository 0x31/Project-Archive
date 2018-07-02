package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by ***REMOVED*** on 9/05/2016.
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
