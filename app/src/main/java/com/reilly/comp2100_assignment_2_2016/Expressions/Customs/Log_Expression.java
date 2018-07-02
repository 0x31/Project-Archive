package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Builtins.Int_builtin;
import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by ***REMOVED*** on 9/05/2016.
 */
public class Log_Expression extends Expression {

    Expression left;
    Expression base;

    public Log_Expression(Expression left, Expression right) {
        this.left = left;
        this.base = right;
    }

    public Log_Expression(Expression left) {
        this.left = left;
        this.base = new Int_builtin("10");
    }

    @Override
    public String show() {
        return "log("+left.show()+", " + base.show()+")";
    }

    @Override
    public Value evaluate() throws EvaluateError {

        Value lv = left.evaluate();
        Value rv = base.evaluate();
        Value[] lr = Value.convert(new Value[] {lv, rv});

        Double ret = (Math.log(lv.value)/Math.log(rv.value));

        return new Value(ret,lr);

    }
}
