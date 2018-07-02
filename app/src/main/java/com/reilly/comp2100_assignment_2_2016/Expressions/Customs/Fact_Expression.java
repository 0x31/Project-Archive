package com.reilly.comp2100_assignment_2_2016.Expressions.Customs;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;

/**
 * Created by ***REMOVED*** on 9/05/2016.
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
