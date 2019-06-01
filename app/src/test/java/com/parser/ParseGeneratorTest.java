package com.parser;

import android.content.Context;
import android.widget.Toast;

import com.parser.Parser.LALRParserGenerator;
import com.parser.Parser.Parser;
import com.parser.Parser.Tokenizer;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created on 9/05/2016.
 */
public class ParseGeneratorTest {

    @Test
    public void parseGenerator_testOne() throws Exception {

        List<String> exp = new ArrayList<>();
        List<String> inp = new ArrayList<>();

        inp.add("1+1"); exp.add("2");


        Parser parser = loadParser(this.getClass().getClassLoader(), "testGrammar.txt");


        for (int i = 0; i<inp.size(); i++) {

            String in = inp.get(i);
            Tokenizer tokenizer = new Tokenizer(parser.getExpected(), in);

            String real_out = formatter(parser.parse(tokenizer).evaluate().value);
            String expt_out = exp.get(i);

            assertEquals(expt_out, real_out);

        }



    }



    static public Parser loadParser(ClassLoader classLoader, String fileName) throws LALRParserGenerator.GrammarError {

        // File loading reference: http://stackoverflow.com/questions/29341744/android-studio-unit-testing-read-data-input-file

        String grammar = "";
        Parser parser;

        InputStream inStream = classLoader.getResourceAsStream(fileName);
        //assertThat(file, notNullValue());
        java.util.Scanner s = new java.util.Scanner(inStream).useDelimiter("\\A");
        grammar = (s.hasNext() ? s.next() : "");
        //grammar = (s.hasNext() ? s.next() : "");

        parser = LALRParserGenerator.generate(grammar);

        return parser;

    }

    public static String formatter(double in) {
        String out = (new DecimalFormat("#.#####")).format(in);
        if(out.equals("-0")) out="0";
        return out;
    }
}
