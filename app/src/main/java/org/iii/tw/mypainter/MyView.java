package org.iii.tw.mypainter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * Created by user on 2016/9/13.
 */
public class MyView extends View {
    private LinkedList<LinkedList<HashMap<String,Float>>> lines;  //這邊是製作一條線   ,之後改多條
    private Resources res;
    private boolean isInit;   //這邊基本型別是flase
    private int viewW,viewH;
    private Bitmap bmpBall, bmpBg;
    private Matrix matrix;
    private Timer timer;
    private float ballX, ballY, ballW, ballH, dx, dy;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        lines = new LinkedList<>();      //建構式物件
        res = context.getResources();
        matrix = new Matrix();          //圖等比例的動作
        timer = new Timer();
//    setOnClickListener(new MyClick());
    }

    Timer getTimer(){return timer;}     //控制MyView週期生命

    private void init(){
        viewW = getWidth();  viewH = getHeight();        //取得view得寬高
        ballW = viewW/8f; ballH = ballW;

        bmpBg = BitmapFactory.decodeResource(res, R.drawable.bg);                 //抓背景的圖  0,0的位置
        bmpBg = resizeBitmap(bmpBg, viewW,viewH);
        bmpBall = BitmapFactory.decodeResource(res, R.drawable.ball);                 //抓球的圖  0,0的位置
        bmpBall = resizeBitmap(bmpBall, ballW,ballH);

        dx = dy = 10;     //球移動的速度
        timer.schedule(new RefreshView(), 0, 40);    //畫面0.04秒移動一次
        timer.schedule(new BallTask(), 1000, 100);   //球每0.1秒移動10的速度
        isInit = true;
    }
    private Bitmap resizeBitmap(Bitmap src, float newW, float newH){

        matrix.reset();
        matrix.postScale(newW/src.getWidth(), newH/src.getHeight());              //新值= 原值*sx  所以sx=新/原
        bmpBall = Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,false);    //抓取整張圖的從00到最後
        return bmpBall;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInit)init();

        canvas.drawBitmap(bmpBg, 0, 0, null);
        canvas.drawBitmap(bmpBall, ballX, ballY, null);



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

    private class RefreshView extends TimerTask{
        @Override
        public void run() {
            //invalidate();      //這裡是內部引用
            postInvalidate();    //因為另外建立一個執行序 所以從外部引用
        }
    }

    private class BallTask extends TimerTask{
        @Override
        public void run() {
            if(ballX<0 || ballX+ballW>viewW) dx *= -1; //球碰到上下的畫面反彈
            if(ballY<0 || ballY+ballH>viewH) dx *= -1; //球碰到左右的畫面反彈
            ballX += dx; ballY += dy;
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
