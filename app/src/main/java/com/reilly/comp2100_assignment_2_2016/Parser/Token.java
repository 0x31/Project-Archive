package com.reilly.comp2100_assignment_2_2016.Parser;

/**
 * Created by ***REMOVED*** on 9/05/2016.
 */
public class Token {
    public enum TokenType {
        integer_token,hexint_token,binint_token,octint_token,float_token,string_token,character_token;
    }

    TokenType tokenType;
    String tokenString;
    String value;
    String potential;
    int pointer;
    int position;

    public Token(TokenType tokenType, String tokenString, String value, String potential, int pointer, int position) {
        this.tokenString = tokenString;
        this.tokenType = tokenType;
        this.value = value;
        this.potential = potential;
        this.pointer = pointer;
        this.position = position;
    }

    public String getTokenString() {
        return tokenString;
    }

    public String getValue() {
        return value;
    }
}
