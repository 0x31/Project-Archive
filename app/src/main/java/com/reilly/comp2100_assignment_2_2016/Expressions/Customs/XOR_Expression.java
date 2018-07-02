package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by reill_000 on 14/05/2016.
 */
public class XOR_Expression extends Expression {

    Expression left;
    Expression right;

    public XOR_Expression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public String show() {
        return left.show()+ " XOR " + right.show();
    }

    @Override
    public Value evaluate() throws EvaluateError {
        Value lv = left.evaluate();
        Value rv = right.evaluate();
        Value[] lr = Value.convert(new Value[] {lv, rv});

        Double ret = (double) (((long)lr[0].value) ^ ((long)lr[1].value));

        return new Value(ret,lr);
    }
}
