package org.iii.tw.mypainter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by user on 2016/9/13.
 */
public class MyView extends View {
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
//    setOnClickListener(new MyClick());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(4);
        canvas.drawLine(0,0,100,100,p);
    }
//    private class MyClick implements View.OnClickListener{
//        @Override
//        public void onClick(View v) {
//            Log.d("brad","onclick");
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        float ex = event.getX();
        float ey = event.getY();
        Log.d("brad", "onTouchEvent" + ex + "x" + ey);
        invalidate();
        return true;
    }

//    @Override
//    public void setOnClickListener(OnClickListener l) {
//        super.setOnClickListener(l);
//    }
}
