package com.parser;

import com.parser.Parser.Tokenizer;
import com.parser.Parser.Token;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.parser.ParseGeneratorTest.loadParser;
import static org.junit.Assert.assertEquals;

/**
 * Created on 9/05/2016.
 */
public class TokenizerTest {
    // TODO Fix test
    @Test
    public void parseGenerator_testOne() throws Exception {

        loadParser(this.getClass().getClassLoader(), "testGrammar.txt");

        List<String> inp = new ArrayList<>();
        List<String[]> tks = new ArrayList<>();


        inp.add("1.0");   tks.add(new String[]{"float"});
        inp.add("1");     tks.add(new String[]{"int"});
        inp.add("1+1.0"); tks.add(new String[]{"int","+","float"});

        for(int i = 0; i<inp.size(); i++) {
            String s = inp.get(i);

            System.out.println("Testing: "+s);
            Tokenizer tokenizer = new Tokenizer(Arrays.asList(new String[]{"int", "float"}), s);
            int j = 0;
            while (tokenizer.hasMore()) {
                Token next = tokenizer.next();
                System.out.println("Token "+j+": "+next.getValue()+" is a "+next.getTokenString());
                assertEquals("Bad token", tks.get(i)[j++],next.getTokenString());
            }
            System.out.println("");
        }


    }



}
