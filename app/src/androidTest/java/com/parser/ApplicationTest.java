package com.parser;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.widget.Toast;

import com.parser.Expressions.Expression;
import com.parser.Parser.LALRParserGenerator;
import com.parser.Parser.Parser;
import com.parser.Parser.Tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */


public class ApplicationTest extends ApplicationTestCase<Application> {
    File parserFile;
    InputStream inStream;
    java.util.Scanner s;
    String grammar = "";
    Parser parser = null;
    @Override
    protected void setUp(){
        parserFile = new File(getContext().getFilesDir(), "Parser.ser");
        try {
            inStream = getContext().getAssets().open("raw/grammar.txt");
            s = new java.util.Scanner(inStream).useDelimiter("\\A");
            //grammar = (s.hasNext() ? s.next() : "");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(parserFile));
            parser = (Parser) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }


        if(grammar != null && (parser == null || parser.hashCode() != grammar.hashCode())) {


            try {

                System.out.println("Generating grammar...");
                grammar = "$S -> a";
                parser = LALRParserGenerator.generate(grammar);

                OutputStream fw = new FileOutputStream(parserFile.getAbsoluteFile()) {
                };
                ObjectOutputStream os = new ObjectOutputStream(fw);
                os.writeObject(parser);
                os.close();
                fw.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LALRParserGenerator.GrammarError grammarError) {
                Toast.makeText(getContext(), "Couldn't read in grammar", Toast.LENGTH_SHORT).show();
                grammarError.printStackTrace();
            }

        }
        try {
            Tokenizer tokenizer = new Tokenizer(parser.getExpected(), "1+2");
            assertEquals(parser.parse(tokenizer).evaluate().value,"3");

            tokenizer = new Tokenizer(parser.getExpected(), "sin(0)");
            assertEquals(parser.parse(tokenizer).evaluate().value,"0");

        } catch (Parser.SyntaxError syntaxError) {
            syntaxError.printStackTrace();
        } catch (Expression.EvaluateError evaluateError) {
            evaluateError.printStackTrace();
        }


    }
    public ApplicationTest() {
        super(Application.class);
        //String grammar = super.getContext().getString(R.string.grammar);

        // Try to load grammar.txt

    }
}