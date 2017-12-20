package com.xu.ruleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class SlideRuleView extends View {
    private static final int rule5Length =60;//刻度线为5倍时的长度
    private static final int ruleOtherLength = 33;//其它刻度线的长度
    private static final int rule10Length = 100;//可都是10倍时的长度
    private Paint paint;
    private TextPaint textPaint;
    private int ruleLength;//尺子的长度,其实就是view的长度,(其实就是屏幕的宽度)
    private int mTextSize = 36;// 字体大小
    private int onePost;//第一个刻度线的位置
    private int twoPost;//第二个刻度线的位置
    private int threePost;//第三个刻度线的位置
    private int fourPost;//第四个刻度线的位置
    private int fivePost;//第五个刻度线的位置
    private int sixPost;//第六个刻度线的位置
    private int sevenPost;//第七个刻度线的位置
    private int ruleLeft;//尺子刻度线x轴开始画的位置
    private int ruleRight;
    private int length;
    private int xDown;
    private int i;
    private int i1;

    public SlideRuleView(Context context) {
        this(context, null);
    }

    public SlideRuleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideRuleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        int widthPixels = outMetrics.widthPixels;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        ruleLength = widthPixels;
        length = ruleLength / 6;
        onePost = 0;
        twoPost = length;
        threePost = 2 * length;
        fourPost = 3 * length;
        fivePost = 4 * length;
        sixPost = 5 * length;
        sevenPost = 6 * length;
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        textPaint.setTextSize(mTextSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

  /*  @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //5倍刻度线,不同刻度线长度不一样
        int i1 = (twoPost - onePost) / 5;//这是刻度之间的平均间隔
        int interval = 0;//第二个与第一个之间的每个刻度的X轴的坐标

        int i2 = (threePost - twoPost) / 5;//第三个与第二个
        int interval2 = twoPost+i2;

        int i3 = (fourPost - threePost) / 5;//第四个与第三个
        int interval3 = threePost+i3;

        int i4 = (fivePost - fourPost) / 5;//第五个与第四个
        int interval4 = fourPost+i4;

        int i5 = (sixPost - fivePost) / 5;//第六个与第五个
        int interval5 = fivePost+i5;

        int i6 = (sevenPost - sixPost) / 5;//第七个与第六个
        int interval6 = sixPost+i6;
        for (int j = 0; j <= 30; j++) {

            if (j >= 0 && j <= 5) {
                interval = DrawLine(canvas, i1, interval, j);
            } else if (j >= 6 && j <= 10) {
                interval2 = DrawLine(canvas, i2, interval2, j);

            } else if (j >= 11 && j <= 15) {
                interval3 = DrawLine(canvas, i3, interval3, j);

            } else if (j >= 16 && j <= 20) {
                interval4 = DrawLine(canvas, i4, interval4, j);

            } else if (j >= 21 && j <= 25) {
                interval5 = DrawLine(canvas, i5, interval5, j);

            } else if (j >= 26 && j <= 30) {
                interval6 = DrawLine(canvas, i6, interval6, j);

            }

        }

    }

    private int DrawLine(Canvas canvas, int i1, int interval, int j) {
        int rule5mode = j % 5;
        int rule10mode = j % 10;
        int i = j / 10;
        if (rule5mode == 0 && j != 0&&rule10mode!=0) {
            //5倍刻度线
            canvas.drawLine(interval, 0, interval, rule5Length, paint);
        } else if (j == 0 || rule10mode == 0&&rule5mode==0) {
            if (j == 0) {
                //10倍刻度线
                canvas.drawLine(0, 0, 0, rule10Length, paint);
                textPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("0 mm",0,rule10Length+25,textPaint);
                textPaint.setTextAlign(Paint.Align.CENTER);
            } else {
                canvas.drawLine(interval, 0, interval, rule10Length, paint);
                canvas.drawText(i+"",interval,rule10Length+25,textPaint);
            }
        } else if (rule5mode != 0 && rule10mode != 0 && j != 0) {
            //其它刻度线
            canvas.drawLine(interval, 0, interval, ruleOtherLength, paint);
        }
        interval += i1;
        return interval;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getX();
                i = xDown / length;
                i1 = xDown % length;

                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int value = (x-xDown)/20;

                if (i==0){
                    if(i1>length/2){
                        twoPost=twoPost+value;
                    }

                }else if (i==1){
                    if(i1>length/2){
                        threePost = threePost+value;
                    }else{
                        twoPost=twoPost+value;
                    }
                }else if (i==2){
                    if(i1>length/2){
                        fourPost = fourPost+value;
                    }else{
                        threePost = threePost+value;
                    }
                }else if (i==3){
                    if(i1>length/2){
                        fivePost = fivePost+value;
                    }else{
                        fourPost = fourPost+value;
                    }
                }else if (i==4){
                    if(i1>length/2){
                        sixPost = sixPost+value;
                    }else{
                        fivePost = fivePost+value;
                    }
                }else if (i==5){
                    if(i1>length/2){
                        sevenPost = sevenPost+value;
                    }else{
                        sixPost = sixPost+value;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    public void setAllPost(){

    }
    public void getAallPost(){

    }
}
