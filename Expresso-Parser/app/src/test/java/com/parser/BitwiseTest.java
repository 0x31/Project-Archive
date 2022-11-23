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
public class BitwiseTest {
    @Test
    public void parseGenerator_testOne() throws Exception {

        Parser parser = loadParser(this.getClass().getClassLoader(), "testGrammar.txt");


        List<String> exp = new ArrayList<>();
        List<String> inp = new ArrayList<>();
        // Input          Expected Output
        inp.add("4 AND 2"); exp.add("0");
        inp.add("4 AND 4"); exp.add("4");
        inp.add("1 OR 2"); exp.add("3");
        inp.add("9 OR 12"); exp.add("13");
        inp.add("9 XOR 12"); exp.add("5");
        inp.add("20 XOR 14"); exp.add("26");
        inp.add("20 << 4"); exp.add("320");
        inp.add("4 << 7"); exp.add("512");
        inp.add("2 >> 1"); exp.add("1");
        inp.add("21 >> 5"); exp.add("0");


        for (int i = 0; i< inp.size(); i++) {

            String in = inp.get(i);
            Tokenizer tokenizer = new Tokenizer(parser.getExpected(), in);

            String real_out = formatter( parser.parse(tokenizer).evaluate().value );
            String expt_out = exp.get(i);

            assertEquals(expt_out, real_out);

        }


    }
}
