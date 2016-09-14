package org.iii.tw.mypainter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.ResourceBundle;

/**
 * Created by user on 2016/9/13.
 */
public class MyView extends View {
    private LinkedList<LinkedList<HashMap<String,Float>>> lines;  //這邊是製作一條線   ,之後改多條
    private Resources res;
    private boolean isInit;   //這邊基本型別是flase
    private int viewW,viewH;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        lines = new LinkedList<>();      //建構式物件
        res = context.getResources();
//    setOnClickListener(new MyClick());
    }

    private void init(){
        viewW = getWidth();  viewH = getHeight();
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!isInit)init();

        Bitmap bmpBall = BitmapFactory.decodeResource(res,R.drawable.ball);         //抓球的圖  0,0的位置
        canvas.drawBitmap(bmpBall,0,0,null);



        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(4);
        for(LinkedList<HashMap<String,Float>>line:lines){                //由一改多
        for (int i=1; i<line.size(); i++) {
            canvas.drawLine(line.get(i - 1).get("x"), line.get(i - 1).get("y"),
                    line.get(i).get("x"), line.get(i).get("y"), p);
            }
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
        LinkedList<HashMap<String,Float>>line =
                new LinkedList<>();
        lines.add(line);
        addPoint(x, y);                        //分到下面
    }
    private void doTouchMove(float x, float y){
        addPoint(x, y);
    }
    private void addPoint(float x, float y){
        HashMap<String,Float> point =                 //收集點的動作
                new HashMap<>();
        point.put("x",x); point.put("y",y);
        lines.getLast().add(point);          //最後一條線收集最後的點
        invalidate();
    }
//    @Override
//    public void setOnClickListener(OnClickListener l) {
//        super.setOnClickListener(l);
//    }
}
