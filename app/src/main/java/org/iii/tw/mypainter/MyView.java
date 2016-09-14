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

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by user on 2016/9/13.
 */
public class MyView extends View {
    private LinkedList<HashMap<String,Float>> line;  //這邊是製作一條線
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        line = new LinkedList<>();      //建構式物件
//    setOnClickListener(new MyClick());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(4);
        for (int i=1; i<line.size(); i++){
            canvas.drawLine(line.get(i-1).get("x"),line.get(i-1).get("y"),
                    line.get(i).get("x"),line.get(i).get("y"),p);
        }
    }
//    private class MyClick implements View.OnClickListener{
//        @Override
//        public void onClick(View v) {
//            Log.d("brad","onclick");
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        float ex = event.getX();
        float ey = event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN){            //這段判斷滑鼠再做何種動作
            doTouchDown(ex,ey);                                         //傳遞x,y值讓下面float去接收
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            doTouchMove(ex,ey);
        }
//        invalidate();
        return true;
    }
    private void doTouchDown(float x, float y){
        HashMap<String,Float> point =                //收集點的動作
                new HashMap<>();
        point.put("x",x); point.put("y",y);
        line.add(point);
        invalidate();
    }
    private void doTouchMove(float x, float y){
        HashMap<String,Float> point =
                new HashMap<>();
        point.put("x",x); point.put("y",y);
        line.add(point);
        invalidate();
    }
//    @Override
//    public void setOnClickListener(OnClickListener l) {
//        super.setOnClickListener(l);
//    }
}
