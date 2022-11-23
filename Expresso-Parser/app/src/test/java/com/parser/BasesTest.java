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
 * Created by reill_000 on 16/05/2016.
 */
public class BasesTest {
    @Test
    public void parseGenerator_testOne() throws Exception {

        Parser parser = loadParser(this.getClass().getClassLoader(), "testGrammar.txt");


        List<String> exp = new ArrayList<>();
        List<String> inp = new ArrayList<>();
        // Input          Expected Output
        inp.add("0b10"); exp.add("2");
        inp.add("toBase(3,2)"); exp.add("11");
        inp.add("fromBase(11,2)"); exp.add("3");


        for (int i = 0; i< inp.size(); i++) {

            String in = inp.get(i);
            Tokenizer tokenizer = new Tokenizer(parser.getExpected(), in);

            String real_out = formatter( parser.parse(tokenizer).evaluate().value );
            String expt_out = exp.get(i);

            assertEquals(expt_out, real_out);

        }


    }
}
