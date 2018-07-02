package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by ***REMOVED*** on 9/05/2016.
 */
public class Subtraction_Expression extends Expression {

    Expression left;
    Expression right;

    public Subtraction_Expression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String show() {
        if(left.show().equals("ε")) {
            return "-"+right.show();
        } else {
            return left.show() + " - " + right.show();
        }
    }

    @Override
    public Value evaluate() throws EvaluateError {
        Value lv = left.evaluate();
        Value rv = right.evaluate();
        Value[] lr = Value.convert(new Value[] {lv, rv});

        Double ret = lr[0].value - lr[1].value;

        return new Value(ret,lr);
    }
}
