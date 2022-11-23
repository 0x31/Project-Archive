package com.parser.Expressions.Customs;

import com.parser.Expressions.Expression;
import com.parser.Expressions.Value;

/**
 * Created on 9/05/2016.
 */
public class Fact_Expression extends Expression {

    Expression parameter;

    public Fact_Expression(Expression parameter) {
        this.parameter = parameter;
    }

    @Override
    public String show() {
        return parameter.show()+"!";
    }

    @Override
    public Value evaluate() throws EvaluateError {
        Value left = parameter.evaluate();

        if(left.value>12) throw new EvaluateError("Factorial too big");
        Double ret = factorial(left.value);

        return new Value(ret,left);
    }

    public static double factorial(double n) {
        double fact = 1;
        for (double i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}
