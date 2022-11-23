package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class FromBase_Expression extends Expression {

    Expression left;
    Expression base;

    public FromBase_Expression(Expression left, Expression right) {
        this.left = left;
        this.base = right;
    }

    @Override
    public String show() {
        return "base("+left.show()+", " + base.show()+")";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        try {

            Value lv = left.evaluate();
            Value rv = base.evaluate();
            Value[] lr = Value.convert(new Value[] {lv, rv});

            Double ret = (double) (Integer.parseInt(Integer.toString((int) lv.value), (int) rv.value));

            return new Value(ret,lr);

        } catch (NumberFormatException e) {
            throw new EvaluateError("Invalid base conversion: "+this.show());
        }
    }
}
