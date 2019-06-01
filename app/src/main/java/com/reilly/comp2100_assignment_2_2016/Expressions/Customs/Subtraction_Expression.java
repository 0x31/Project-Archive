package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
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
