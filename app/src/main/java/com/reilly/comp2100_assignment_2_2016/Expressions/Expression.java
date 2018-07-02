package com.reilly.comp2100_assignment_2_2016.Expressions;

/**
 * Created by ***REMOVED*** on 28/04/2016.
 */

public abstract class Expression {

    static public class EvaluateError extends Exception {
        public EvaluateError(String msg) {super(msg);}
    }

    /*  This method prints an expression as a string
        (which could be parsed back into a expression) */
    public abstract String show();
    /* This method evaluates the expression */
    public abstract Value evaluate() throws EvaluateError;

}
