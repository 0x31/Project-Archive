package com.parser.Expressions.Customs;

import com.parser.Expressions.Builtins.Int_builtin;
import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
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
