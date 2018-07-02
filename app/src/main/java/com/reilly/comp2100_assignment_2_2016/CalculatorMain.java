package com.reilly.comp2100_assignment_2_2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Unit;
import com.reilly.comp2100_assignment_2_2016.Expressions.Value;
import com.reilly.comp2100_assignment_2_2016.Graphical.CustomKeyboard;
import com.reilly.comp2100_assignment_2_2016.Graphical.HistoryAdapter;
import com.reilly.comp2100_assignment_2_2016.Parser.LALRParserGenerator;
import com.reilly.comp2100_assignment_2_2016.Parser.Parser;
import com.reilly.comp2100_assignment_2_2016.Parser.Tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * Authored by ***REMOVED*** and Reilly
 */

public class CalculatorMain extends AppCompatActivity {

    EditText inputText;
    Parser parser;
    CustomKeyboard customKeyboard;
    ListView list;
    HistoryAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_main);

        // Show loading screen
        ProgressBar bar = (ProgressBar) this.findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);

        // Setup custom keyboard
        customKeyboard = new CustomKeyboard(this);

        // Set-up input text
        inputText = (EditText)findViewById(R.id.inputText);
        final float maxTextSize = inputText.getTextSize();
        EditText.OnEditorActionListener enterListener = new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN) return false;
                calculateInput(v);
                return true;
            }
        };
        inputText.addTextChangedListener(new TextWatcher() {
             public void afterTextChanged(Editable s) {
                 // Refit Text
                 int availableWidth = inputText.getWidth();
                 float trySize = maxTextSize;
                 float minTextSize = trySize-20;
                 inputText.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
                 while ((trySize > minTextSize)
                         && (inputText.getPaint().measureText(inputText.getText().toString())+10 > availableWidth)) {
                     trySize -= 1;
                     //inputText.setPadding(inputText.getPaddingLeft(),(int) (1300/trySize),inputText.getPaddingRight(),inputText.getPaddingBottom());
                     if (trySize <= minTextSize) { trySize++; break; }
                     inputText.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
                 }
                 inputText.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
             }
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
             public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        inputText.setOnEditorActionListener(enterListener);

        // Hide default keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Open history file and create history adapter
        String historyFileName = getString(R.string.history_file_name);
        File historyFile = new File(this.getFilesDir(), historyFileName);
        adapter = new HistoryAdapter(this, historyFile);
        list=(ListView) findViewById(R.id.historyList);
        list.setAdapter(adapter);
        adapter.setHistoryView(list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                inputText.setText(adapter.histories.get(position).replaceAll(" =[^=]*$",""));
                inputText.setSelection(inputText.length());
            }
        });

        /* Run grammar and parser initialisation on seperate thread, so that the loading screen will
         * show
         */
        Thread thread = new Thread() {
            @Override
            public void run() {

                String grammar = "";
                File parserFile = new File(CalculatorMain.this.getFilesDir(), getString(R.string.parser_file_name));

                try { // Load serialised Parser
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(parserFile));
                    parser = (Parser) in.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try { // Load grammar from assets
                    InputStream inStream = getAssets().open(getString(R.string.grammar_file_name));
                    java.util.Scanner s = new java.util.Scanner(inStream).useDelimiter("\\A");
                    grammar = (s.hasNext() ? s.next() : "");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Generate parser from grammar
                if(parser == null || parser.hashCode() != grammar.hashCode()) {
                    try {
                        parser = LALRParserGenerator.generate(grammar);
                        OutputStream fw = new FileOutputStream(parserFile.getAbsoluteFile());
                        ObjectOutputStream os = new ObjectOutputStream(fw);
                        os.writeObject(parser);
                        os.close(); fw.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (LALRParserGenerator.GrammarError grammarError) {
                        toastError("Error","Couldn't read in grammar");
                        grammarError.printStackTrace();
                    }

                }

                // Instead of a callback, run function here
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.splashscreen).setVisibility(View.GONE);
                        adapter.notifyDataSetInvalidated();
                    }
                });

            }
        };

        thread.start();

    }

    /**
     * Called when inputButton is clicked. Gets the text from inputTest and puts back it's evaluation.
     * @param v
     */
    public void calculateInput(View v) {

        // Check that we loaded okay
        if(parser==null) toastError("Error", "Restart calculator");

        // Get input from inputText
        String input = inputText.getText().toString();

        // Check that input isn't empty
        if(input.isEmpty()) return;

        // Evaluate input
        String output = input;

        // Catch ParseExceptions

        //output = ExpressionEvaluator.evaluateString(input);
        try {

            Value resultV = evaluate(input);
            double result = resultV.value;

            DecimalFormat format = new DecimalFormat("#.#####");
            output = format.format(result);

            // Deal with negative zero
            if(output.equals("-0") || output.equals("-∞")) output = output.substring(1);

            if(input.replace(" ","").substring(0,Math.min(2,input.length())).equals("y=")) {
                customKeyboard.hideCustomKeyboard();
            }

            if(resultV.unit!= Unit.Number.UNIT) {
                output+=resultV.unit.getShort();
            }

            // Save to history
            adapter.addToHistory(input, output);

        } catch (Parser.SyntaxError syntaxError) {
            toastError("SyntaxError", syntaxError.getMessage());
            if(syntaxError.pointer>-1) {
                int split0 = 0;
                int split1 = syntaxError.pointer;
                int split2 = syntaxError.pointer + syntaxError.length;
                int split3 = input.length();
                output = input.substring(split0,split1)+"<font color='#ff0000'>"+input.substring(split1,split2)+"</font>"+input.substring(split2,split3);
            }

            syntaxError.printStackTrace();
        } catch (Expression.EvaluateError evaluateError) {
            toastError("EvaluateError",evaluateError.getMessage());
            evaluateError.printStackTrace();
        }

        // Show output
        inputText.setText(Html.fromHtml(output));

        // Set cursor to end of output
        inputText.setSelection(inputText.length());

    }

    public Value evaluate(String input) throws Parser.SyntaxError, Expression.EvaluateError {

        if(parser == null) throw new Parser.SyntaxError("Parser failed to load");

        // Create tokenizer
        Tokenizer tokenizer = new Tokenizer(parser.getExpected(),input);

        // Parse tokenizer
        Expression tree = parser.parse(tokenizer);

        // Return evaluation
        return tree.evaluate();
    }

    public void toastError(String error, String message) {
        Toast.makeText(CalculatorMain.this, error+": "+message, Toast.LENGTH_SHORT).show();
    }

    public void openKeyboard(View v)  {
        customKeyboard.openKeyboard(v);
    }

}
