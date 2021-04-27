package com.tenseenola.dnui.lsn4;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by lenovo on 2021/4/27.
 * 描述：
 *
 */
public class ArrowMenue extends View {
    protected int withCount = 3;//宽度被划分为多少份
    protected int heightCount =4;//高度被划分为多少份
    protected int mPerWith;//每一份宽度多少
    protected int mPerHeight;//每一份高度多少
    protected Paint mPaint;
    private int mProgress;

    public ArrowMenue(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPerWith = getWidth()/withCount;
        mPerHeight = getHeight()/heightCount;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画中间一条线
        drawCenterLine(canvas);
        //画最上面一条线
        drawTopLine(canvas);
        //画下面一条线
        drawBottom(canvas);
    }


    /**S
     * @param canvas
     */
    private void drawTopLine(Canvas canvas) {
        if (mProgress<=100){
            //顺时针旋转
            canvas.save();
            canvas.rotate( mProgress/100f*150,mPerWith*2,mPerHeight);
            canvas.drawLine(mPerWith ,mPerHeight,mPerWith*2,mPerHeight,mPaint);
            canvas.restore();
        }else {
            canvas.save();
            float rat = (mProgress-100)/100f;
            float transX = -mPerWith * rat;
            float transY =  mPerHeight * rat;
            canvas.translate(transX, transY) ;
            canvas.drawLine(mPerWith*2,mPerHeight*1,mPerWith*3 ,0,mPaint);
            canvas.restore();
        }
    }

    /**
     * @param canvas
     */
    private void drawCenterLine(Canvas canvas) {
        canvas.drawLine(mPerWith,mPerHeight*2,mPerWith*2,mPerHeight*2,mPaint);
    }

    /**
     * @param canvas
     */
    private void drawBottom(Canvas canvas) {
        if (mProgress <=100) {
            //逆时针旋转
            canvas.save();
            canvas.rotate(-mProgress/100f*150,mPerWith*2,mPerHeight*3);
            canvas.drawLine(mPerWith,mPerHeight*3,mPerWith*2,mPerHeight*3,mPaint);
            canvas.restore();
        }else {
            canvas.save();
            float rat = (mProgress-100)/100f;
            float transX = -mPerWith * rat;
            float transY = -mPerHeight * rat;
            canvas.translate(transX,transY);
            canvas.drawLine(mPerWith*2,mPerHeight*3,mPerWith*3 ,mPerHeight*4,mPaint);
            canvas.restore();
        }
    }


    public void open(){
        ValueAnimator lValueAnimator = ValueAnimator.ofInt(0, 200);
        lValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        lValueAnimator.setInterpolator(new FastOutSlowInInterpolator());
        lValueAnimator.setDuration(2*1000);
        lValueAnimator.start();
    }

    public void close(){
        ValueAnimator lValueAnimator = ValueAnimator.ofInt(200, 0);
        lValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        lValueAnimator.setInterpolator(new FastOutSlowInInterpolator());
        lValueAnimator.setDuration(2*1000);
        lValueAnimator.start();
    }
}
