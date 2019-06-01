package com.parser.Graphical;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.parser.R;

import java.util.List;

public class CustomKeyboardView extends KeyboardView {

    Context context;

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void showWithAnimation(Animation animation) {
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.VISIBLE);
            }
        });

        setAnimation(animation);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        List<Keyboard.Key> keys = getKeyboard().getKeys();

        int qposy=0;
        int oposy=0;

        for (Keyboard.Key key : keys) {

            Paint paint = new Paint();
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize((int) (60.0/1080.0*canvas.getWidth()));
            Typeface plain = Typeface.create("sans-serif-light", Typeface.BOLD);
            paint.setTypeface(plain);
            paint.setColor(Color.parseColor("#969696"));

            //System.out.println(key.codes[0]);
            //if(key.codes[0] == 113) {
            //    qposy = key.y;
            //}
            if(key.codes[0]==49) {
                oposy = key.y+key.height;
            }

            int xgap = (int) (8.0/1080.0*canvas.getWidth());
            int ygap = (int) (12.0/1085.0*canvas.getHeight());
            int shift = 0;
            if(key.y<360) {
                shift = 2;
            }

            Drawable dr = (Drawable) context.getResources().getDrawable(R.color.keyboard2);
            dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
            dr.draw(canvas);

            dr = (Drawable) context.getResources().getDrawable(R.color.keyboard3);
            dr.setBounds(key.x+xgap, key.y+ygap-shift, key.x + key.width-xgap+4, key.y + key.height-ygap+6-shift);
            dr.draw(canvas);


            if (key.codes[0] == 32 || key.codes[0] == -1 || key.codes[0] == -11) {
                dr = (Drawable) context.getResources().getDrawable(R.color.keyboard3darker);
                dr.setBounds(key.x+xgap, key.y+ygap-shift, key.x + key.width-xgap+4, key.y + key.height-ygap+6-shift);
                dr.draw(canvas);

                dr = (Drawable) context.getResources().getDrawable(R.color.keyboard3);
                dr.setBounds(key.x+xgap, key.y+ygap-shift, key.x + key.width-xgap, key.y + key.height-ygap-shift);
                dr.draw(canvas);
                paint.setColor(Color.parseColor("#ffffff"));
            } else if (key.codes[0] == -4) {
                dr = (Drawable) context.getResources().getDrawable(R.color.colorPrimary);
                dr.setBounds(key.x+xgap, key.y+ygap-shift, key.x + key.width-xgap, key.y + key.height-ygap-shift);
                dr.draw(canvas);
                paint.setColor(Color.parseColor("#ffffff"));
            } else if (key.codes[0] == -10) {
                //Log.e("KEY", "Drawing key with code " + key.codes[0]);
                dr = (Drawable) context.getResources().getDrawable(R.color.keyboard2);
                dr.setBounds(key.x, key.y-shift, key.x + key.width, key.y + key.height-shift);
                dr.draw(canvas);
                paint.setColor(Color.parseColor("#ffffff"));
            } else {
                dr = (Drawable) context.getResources().getDrawable(R.color.keyboard);
                dr.setBounds(key.x+xgap, key.y+ygap-shift, key.x + key.width-xgap, key.y + key.height-ygap-shift);
                dr.draw(canvas);
            }


            if (key.label != null) {
                canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                        key.y + (key.height / 2)+30, paint);
            } else {
                key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                key.icon.draw(canvas);
            }
        }

        Drawable dr = (Drawable) context.getResources().getDrawable(R.color.keyboard3);
        dr.setBounds(0,oposy + 0,canvas.getWidth(),oposy + 4);
        dr.draw(canvas);
    }
}