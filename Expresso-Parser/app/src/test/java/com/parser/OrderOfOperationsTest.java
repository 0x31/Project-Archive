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
public class OrderOfOperationsTest {

    @Test
    public void parseGenerator_testOne() throws Exception {

        Parser parser = loadParser(this.getClass().getClassLoader(), "testGrammar.txt");


        List<String> exp = new ArrayList<>();
        List<String> inp = new ArrayList<>();

        inp.add("1-2+3"); exp.add("2");
        inp.add("1+3-2"); exp.add("2");
        inp.add("2*3+4"); exp.add("10");
        inp.add("2*(3+4)"); exp.add("14");
        inp.add("2+3*4"); exp.add("14");
        inp.add("4+2*3"); exp.add("10");
        inp.add("2/3*3"); exp.add("2");




        for (int i = 0; i<inp.size(); i++) {

            String in = inp.get(i);
            Tokenizer tokenizer = new Tokenizer(parser.getExpected(), in);

            String real_out = formatter( parser.parse(tokenizer).evaluate().value );
            String expt_out = exp.get(i);

            assertEquals(expt_out, real_out);

        }


    }

}
