package com.parser.Graphical;


import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.*;

import com.parser.CalculatorMain;
import com.parser.Expressions.Expression;
import com.parser.Parser.Parser;
import com.parser.R;

import java.lang.Math;
import java.lang.Override;

/**
 * Based on sample code by Google.
 *
 * Source: https://developer.android.com/training/custom-views/custom-drawing.html
 * Custom view.
 */
public class Plot extends ViewGroup {

    Bitmap plotBMP;
    Context context;

    /**
     * Class constructor taking only a context. Use this constructor to create
     * {@link Plot} objects from your own code.
     *
     * @param context
     */
    public Plot(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * Class constructor taking a context and an attribute set. This constructor
     * is used by the layout engine to construct a {@link Plot} from a set of
     * XML attributes.
     *
     * @param context
     * @param attrs   An attribute set which can contain attributes from
     */
    public Plot(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }


    /**
     * Initialize the control. This code is in a separate method so that it can be
     * called from both constructors.
     */
    private void init() {

    }

    public void addPlot(Bitmap plotBMP) {
        this.plotBMP = plotBMP;
        this.invalidate();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(plotBMP!=null) {
            final Paint axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            axisPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
            axisPaint.setStyle(Paint.Style.STROKE);
            axisPaint.setStrokeWidth(6);
            canvas.drawBitmap(plotBMP, 0, 0, axisPaint);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getRootView().getWidth() - (getPaddingLeft() + getPaddingRight());
        setMeasuredDimension(width-100,width+50);
    }



    public Bitmap plot(String line, int width, int height, Activity context) {

        if(!(height>0 && width>0)) {
            return null;
        }

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp;
        try {
            bmp = Bitmap.createBitmap(width, height, conf); // this creates a MUTABLE bitmap
        } catch(OutOfMemoryError e) {
            System.out.println("WARNING: OutOfMemoryError when initialising plot");
            return null;
        }
        Canvas canvas = new Canvas(bmp);


        final Paint axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setColor(context.getResources().getColor(R.color.black));
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setStrokeWidth(6);

        canvas.drawLine(width/2,0,width/2,height,axisPaint);
        canvas.drawLine(0,height/2,width,height/2,axisPaint);

        axisPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        axisPaint.setStrokeWidth(10);

        int pointCount = 51;
        int errorCount = 0;

        float[] xs = new float[pointCount];
        float []ys = new float[pointCount];
        float miny = 0;
        float maxy = 0;
        float minx = 0;
        float maxx = 0;

        for (int x = 0; x < pointCount; x++) {
            xs[x] = (float) (-5.0 + ((double) x) * 10.0 / (pointCount - 1.0));
            String point = line.replace("x", "(" + Double.toString(xs[x]) + ")");
            ys[x] = 0;
            try {
                ys[x] = -(float) (((CalculatorMain) context).evaluate(point).value);
            } catch (Parser.SyntaxError syntaxError) {
                errorCount++;
                continue;
            } catch (Expression.EvaluateError evaluateError) {
                errorCount++;
                continue;
            }
            minx = Math.min(minx, xs[x]);
            maxx = Math.max(maxx, xs[x]);
            if(Float.isNaN(ys[x]) || Float.isInfinite(ys[x])) {
                continue;
            }
            miny = Math.min(miny, ys[x]);
            maxy = Math.max(maxy, ys[x]);
        }

        /* Add a border to the plot */
        miny = (float) (miny-(maxy-miny)*0.02);
        maxy = (float) (maxy+(maxy-miny)*0.02);
        //minx = (float) (minx-(maxx-minx)*0.5);
        //maxx = (float) (maxx+(maxx-minx)*0.05);

        float oldX = 0;
        float oldY = 0;
        int first = 0;
        for (int x = 0; x < pointCount; x++) {
            if(Float.isNaN(ys[x]) || Float.isInfinite(ys[x])) { first++; continue; }
            float newX = (xs[x] - minx) * (width / (maxx - minx));
            float newY = (ys[x] - miny) * (height / (maxy - miny));
            if(x>first) {
                canvas.drawLine(oldX,oldY,newX,newY,axisPaint);
            }
            oldX = newX; oldY = newY;
            canvas.drawPoint(newX, newY, axisPaint);
        }

        if(errorCount==pointCount) return null;
        return bmp;
    }




}
