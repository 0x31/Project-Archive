package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by ***REMOVED*** on 9/05/2016.
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
