package com.parser.Parser;

import com.parser.Expressions.Expression;

import java.util.List;

/**
 * Created on 9/05/2016.
 */


public interface Parser  {

    class SyntaxError extends Exception {
        public int pointer = -1;
        public int length = -1;
        public SyntaxError(String msg) {super(msg);}
        public SyntaxError(String msg,int pointer,int length) {super(msg);this.pointer=pointer;this.length=length;}
    }

    Expression parse(Tokenizer tokenizer) throws SyntaxError;

    List<String> getExpected();

}
