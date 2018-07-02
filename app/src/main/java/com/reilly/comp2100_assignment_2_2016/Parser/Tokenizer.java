package com.reilly.comp2100_assignment_2_2016.Parser;

import com.reilly.comp2100_assignment_2_2016.Parser.Token;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ***REMOVED*** on 30/04/2016.
 *
 * Splits the input string into Token objects.
 * It uses a list of expected terminals to check for ints (in multiple bases), floats, and words.
 * If it doesn't recognize anything, it returns the single next character.
 */
public class Tokenizer {
    int pos=0;
    int pointer=1;
    String s;
    List<String> expected;
    int longestExpected=0;
    boolean caseSensitive = false;

    public Tokenizer(List<String> expected, String s) {
        this.s = s;
        this.expected = expected;
        for(String exp : expected) {
            longestExpected = Math.max(exp.length(),longestExpected);
        }
    }

    public Token next() {
        while(pos<s.length() && s.substring(pos).matches("^\\s.*")) {
            pos++;
        }
        String sub = s.substring(pos);
        if(sub.isEmpty()) {
            return null;
        }

        // Built-in patters and corresponding token types
        String[] grammars = new String[] {"float", "hexint", "binint", "octint", "int"};
        String[] patterns = new String[] {"^[0-9]*\\.[0-9]+","^0x[0-9]+","^0b[0-9]+","^0o[0-9]+","^[0-9]+"};
        Token.TokenType[] tokens = new Token.TokenType[] {Token.TokenType.float_token,
                Token.TokenType.hexint_token, Token.TokenType.binint_token,
                Token.TokenType.octint_token, Token.TokenType.integer_token};

        // Match built-ins
        for(int i = 0 ; i < patterns.length; i++) {
            if(expected.contains(grammars[i])) {
                Pattern float_match = Pattern.compile(patterns[i]);
                Matcher float_group = float_match.matcher(sub);
                if (float_group.find()) {
                    String matched = float_group.group();
                    Token t = new Token(tokens[i], grammars[i], matched, matched, pointer, pos);
                    pos += matched.length();
                    pointer += matched.length();
                    return t;
                }
            }
        }

        // Match expected characters
        // This method is not optimised
        // Should do some sort of complete-substring lookup
        String longest = "";
        for(int i = 1 ; i < Math.min(longestExpected,sub.length())+1; i++) {
            String potential = sub.substring(0,i);
            // Check normal case and then lower case
            if(expected.contains(potential)) {
                longest = potential;
            } else if(!caseSensitive && expected.contains(potential.toLowerCase())) {
                longest = potential.toLowerCase();
            }
        }
        if(longest.length()>0) {
            Token t = new Token(Token.TokenType.string_token,longest, longest, longest, pointer,pos);
            pos+= longest.length();
            pointer+= longest.length();
            return t;
        }


        /*
        // Match words
        Pattern word_match = Pattern.compile("^[A-Za-z]+");
        Matcher word_group = word_match.matcher(sub);
        if(word_group.find()) {
            String matched = word_group.group();
            Token t = new Token(Token.TokenType.string_token,matched,matched,pointer,pos);
            pos+=matched.length();
            pointer+=matched.length();
            return t;
        }
        */

        // Match anything else, one character at a time
        String character = s.substring(pos,pos+1);
        String potential = character;
        Pattern word_match = Pattern.compile("^[A-Za-z]+");
        Matcher word_group = word_match.matcher(sub);
        if(word_group.find()) potential = word_group.group();
        Token t = new Token(Token.TokenType.character_token, character, character, potential, pointer,pos);
        pos++;
        pointer++;
        return t;

    }
    public Token peek() {
        Token peek = next();
        if(peek!=null) {
            pos-=peek.value.length();
            pointer-=peek.value.length();
        }
        return peek;
    }
    public boolean hasMore() {
        return pos<s.length();
    }
}
