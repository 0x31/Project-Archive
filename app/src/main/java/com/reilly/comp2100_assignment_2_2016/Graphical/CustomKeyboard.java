package com.reilly.comp2100_assignment_2_2016.Graphical;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.reilly.comp2100_assignment_2_2016.CalculatorMain;
import com.reilly.comp2100_assignment_2_2016.R;

import java.util.Arrays;
import java.util.List;

/**
 * Authored by ***REMOVED*** and Reilly
 */
public class CustomKeyboard {

    /* Custom keyboard.
     * Sources:
     * http://code.tutsplus.com/tutorials/create-a-custom-keyboard-on-android--cms-22615
     * http://www.fampennings.nl/maarten/android/09keyboard/index.htm
     */

    CalculatorMain activity;
    KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener;
    private Keyboard kbd_letters;
    private Keyboard kbd_symbols;
    private Keyboard kbd_letters_top;
    private Keyboard kbd_symbols_top;
    private CustomKeyboardView mKeyboardView;

    int state = 1;



    public CustomKeyboard(final CalculatorMain activity) {

        this.activity = activity;


        kbd_letters = new Keyboard(activity, R.xml.kbd_letters);
        kbd_symbols = new Keyboard(activity,R.xml.kbd_symbols);
        kbd_letters_top = new Keyboard(activity,R.xml.kbd_letters_top);
        kbd_symbols_top = new Keyboard(activity,R.xml.kbd_symbols_top);

        // Lookup the KeyboardView
        mKeyboardView= (CustomKeyboardView) activity.findViewById(R.id.keyboardview);
        // Attach the keyboard to the view
        mKeyboardView.setKeyboard(kbd_symbols);


        mOnKeyboardActionListener  = new KeyboardView.OnKeyboardActionListener() {


            public final static int CodeDone     = -4; // Keyboard.KEYCODE_DELETE
            public final static int CodeHide     = -6; // Keyboard.KEYCODE_CANCEL
            public final static int CodeTop      = -11; // Keyboard.KEYCODE_CANCEL
            public final static int CodeSin      = 55000;
            public final static int CodeCos      = 55001;
            public final static int CodeTan      = 55002;
            public final static int CodeLog      = 55003;
            public final static int CodeLn       = 55004;
            public final static int CodePow      = 55005;
            public final static int CodePi       = 55006;
            public final static int CodeE        = 55007;
            public final static int CodeAsin     = 55008;
            public final static int CodeAcos     = 55009;
            public final static int CodeAtan     = 55010;
            public final static int CodeHex      = 55011;
            public final static int CodeBin      = 55012;
            public final static int CodeOct      = 55013;
            public final static int CodePlot     = 55014;

            public final static int CodeAND      = 55015;
            public final static int CodeOR       = 55016;
            public final static int CodeNOT      = 55017;
            public final static int CodeXOR      = 55018;
            public final static int CodeLeftShift = 55019;
            public final static int CodeRightShift = 55020;

            public final static int CodeMod = 55021;

            public final static int CodePlus     = 43;
            public final static int CodeMinus    = 45;
            public final static int CodeTimes    = 42;
            public final static int CodeDiv      = 47;
            public final static int CodePowe      = 94;

            public final List<Integer> AddSpace = Arrays.asList(
                    new Integer[] {CodeAND, CodeOR, CodeXOR});


            @Override public void onKey(int primaryCode, int[] keyCodes)
            {

                View focusCurrent = activity.getWindow().getCurrentFocus();
                if( focusCurrent==null  ) return;
                EditText edittext = (EditText) focusCurrent;
                Editable editable = edittext.getText();
                int start = edittext.getSelectionStart();
                // Handle key
                if( primaryCode==CodeDone ) {
                    //hideCustomKeyboard();
                    activity.calculateInput(focusCurrent);
                } else if( primaryCode==CodeHide ) {
                    hideCustomKeyboard();
                } else if( primaryCode==Keyboard.KEYCODE_DELETE ) {
                    if( editable!=null && start>0 ) editable.delete(start - 1, start);
                } else if(primaryCode== kbd_letters.KEYCODE_SHIFT) {
                    if(state==0) {
                        state = 1;
                        mKeyboardView.setKeyboard(kbd_symbols);
                    } else if(state==1) {
                        state = 0;
                        mKeyboardView.setKeyboard(kbd_letters);
                    } else if(state==2) {
                        state = 3;
                        mKeyboardView.setKeyboard(kbd_symbols_top);
                    } else if(state==3) {
                        state = 2;
                        mKeyboardView.setKeyboard(kbd_letters_top);
                    }
                    //kbd_letters.setShifted(!kbd_letters.isShifted());
                    //mKeyboardView.invalidateAllKeys();
                } else if(primaryCode== CodeTop) {
                    if(state==0) {
                        state = 2;
                        mKeyboardView.setKeyboard(kbd_letters_top);
                    } else if(state==1) {
                        state = 3;
                        mKeyboardView.setKeyboard(kbd_symbols_top);
                    } else if(state==2) {
                        state = 0;
                        mKeyboardView.setKeyboard(kbd_letters);
                    } else if(state==3) {
                        state = 1;
                        mKeyboardView.setKeyboard(kbd_symbols);
                    }
                    //kbd_letters.setShifted(!kbd_letters.isShifted());
                    //mKeyboardView.invalidateAllKeys();
                }
                // Actual input:
                else if( primaryCode==CodeSin ) {
                    editable.insert(start, "sin(");
                }else if( primaryCode==CodeCos ) {
                    editable.insert(start, "cos(");
                }else if( primaryCode==CodeTan ) {
                    editable.insert(start, "tan(");
                }else if( primaryCode==CodeLog ) {
                    editable.insert(start, "log(");
                }else if( primaryCode==CodeLn ) {
                    editable.insert(start, "ln(");
                }else if( primaryCode==CodePow ) {
                    editable.insert(start, "pow(");
                }else if( primaryCode==CodePi ) {
                    editable.insert(start, "π");
                }else if( primaryCode==CodeE ) {
                    editable.insert(start, "e");
                }else if( primaryCode==CodeAsin ) {
                    editable.insert(start, "asin(");
                }else if( primaryCode==CodeAcos ) {
                    editable.insert(start, "acos(");
                }else if( primaryCode==CodeAtan ) {
                    editable.insert(start, "atan(");
                }else if( primaryCode==CodeHex ) {
                    editable.insert(start, "0x");
                }else if( primaryCode==CodeBin ) {
                    editable.insert(start, "0b");
                }else if( primaryCode==CodeOct ) {
                    editable.insert(start, "0o");
                }else if( primaryCode==CodeAND ) {
                    editable.insert(start, "AND");
                }else if( primaryCode==CodeNOT ) {
                    editable.insert(start, "NOT(");
                }else if( primaryCode==CodeOR ) {
                    editable.insert(start, "OR");
                }else if( primaryCode==CodeXOR ) {
                    editable.insert(start, "XOR");
                }else if( primaryCode==CodeLeftShift ) {
                    editable.insert(start, "<<");
                }else if( primaryCode==CodeRightShift ) {
                    editable.insert(start, ">>");
                }else if( primaryCode==CodePlot ) {
                    editable.insert(start, "y=");
                }else if( primaryCode==CodeMod ) {
                    editable.insert(start, "%");
                }else {// Insert character

                    if(AddSpace.contains(primaryCode)) {
                        if(start>0) {
                            char[] chars = new char[1];
                            editable.getChars(start - 1, start, chars, 0);
                            if(chars[0]!=((char) 32)) {
                                editable.insert(start++, Character.toString((char) 32));
                            }
                        }

                        editable.insert(start++, Character.toString((char) primaryCode));

                        if(start<editable.length()-1) {
                            char[] chars = new char[1];
                            editable.getChars(start, start+1, chars, 0);
                            if(chars[0]!=((char) 32)) {
                                editable.insert(start++, Character.toString((char) 32));
                            }
                        } else {
                            editable.insert(start++, Character.toString((char) 32));
                        }

                    } else {
                        editable.insert(start, Character.toString((char) primaryCode));
                    }
                }
            }

            @Override public void onPress(int arg0) {
            }

            @Override public void onRelease(int primaryCode) {
            }

            @Override public void onText(CharSequence text) {
            }

            @Override public void swipeDown() {
            }

            @Override public void swipeLeft() {
            }

            @Override public void swipeRight() {
            }

            @Override public void swipeUp() {
            }
    };


        // Install the key handler
        mKeyboardView.setOnKeyboardActionListener(this.mOnKeyboardActionListener);



    }


    public void hideSoft(View v) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void openKeyboard(View v)
    {
        hideSoft(v);

        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        if( v!=null)((InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
    }


}
