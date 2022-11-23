package com.parser;

import com.parser.Parser.Parser;
import com.parser.Parser.Tokenizer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.parser.ParseGeneratorTest.formatter;
import static com.parser.ParseGeneratorTest.loadParser;
import static org.junit.Assert.assertEquals;

/**
 * Created on 9/05/2016.
 */
public class BasicArithmeticTest {

    @Test
    public void parseGenerator_testOne() throws Exception {

        Parser parser = loadParser(this.getClass().getClassLoader(), "testGrammar.txt");


        List<String> exp = new ArrayList<>();
        List<String> inp = new ArrayList<>();

        inp.add("1"); exp.add("1");
        inp.add("-1"); exp.add("-1");
        inp.add("1+1"); exp.add("2");
        inp.add("2-1"); exp.add("1");
        inp.add("2*3"); exp.add("6");
        inp.add("9/3"); exp.add("3");
        inp.add("(5+5)"); exp.add("10");
        inp.add("2+3*4/5*(10*4)"); exp.add("98");

        for (int i = 0; i<inp.size(); i++) {

            String in = inp.get(i);
            Tokenizer tokenizer = new Tokenizer(parser.getExpected(), in);

            String real_out = formatter( parser.parse(tokenizer).evaluate().value );
            String expt_out = exp.get(i);

            assertEquals(expt_out, real_out);

        }


    }

}
