package com.parser.Expressions;

/**
 * Created on 28/04/2016.
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
