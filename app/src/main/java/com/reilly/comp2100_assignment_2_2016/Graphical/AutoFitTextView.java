package com.reilly.comp2100_assignment_2_2016.Graphical;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.widget.TextView;

import com.reilly.comp2100_assignment_2_2016.CalculatorMain;

/**
 * AutoFitTextView code taken from:
 * http://stackoverflow.com/questions/9713669/auto-scale-text-size
 * Provided by user `adneal`, modified by ***REMOVED***.
 *
 * */
public class AutoFitTextView extends TextView {

    int screenwidth;
    Context context;

    public AutoFitTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AutoFitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {

        Display display = ((CalculatorMain) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.screenwidth = size.x;
        this.screenwidth -= 100;


        maxTextSize = this.getTextSize();
        minTextSize = (float) (maxTextSize*0.5);
    }

    private void refitText(String text, int textWidth) {
        if (textWidth > 0 && !text.contains("...")) {

            int availableWidth = screenwidth;
            float trySize = maxTextSize;
            //System.out.println("Trying first: "+trySize);

            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);

            while ((trySize > minTextSize)
                    && (this.getPaint().measureText(text) > availableWidth)) {
                trySize -= 1;
                //System.out.println(trySize);
                //this.setPadding(this.getPaddingLeft(),(int) (1300/trySize),this.getPaddingRight(),this.getPaddingBottom());
                if (trySize <= minTextSize) { break; }
                this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
            }


            if(trySize==minTextSize+1) {
                String thisText = this.getText().toString();
                int split = thisText.lastIndexOf("=");
                String left = thisText.substring(0,split);
                String right = thisText.substring(split);
                this.setText(left.substring(0,Math.min(16,left.length()))+"..."+right);
                trySize = minTextSize+1;
            }

            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize-2);
        }
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start,
                                 final int before, final int after) {
        refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(this.getText().toString(), w);
        }
    }

    //@Override
    //protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    //    refitText(this.getText().toString(), parentWidth);
    //}


    private float minTextSize;
    private float maxTextSize;

}