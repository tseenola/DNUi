package com.tenseenola.dnui.lsn3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tenseenola.dnui.R;

public class CircleProgress extends View {
    private final Paint mRoundBackGroundPaint;
    private final Paint mArcPaint;
    private final Paint mTextPaint;
    private   int mProgressMax;
    private int mCurProgress;
    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        int mProgressColor = typedArray.getColor(R.styleable.CircleProgress_progressColor, Color.RED);
        int mCircleBgColor = typedArray.getColor(R.styleable.CircleProgress_progressBgColor, Color.BLACK);
        float mProgressWidth = typedArray.getDimension(R.styleable.CircleProgress_progressWidth, 55);
        float mTextSize = typedArray.getDimension(R.styleable.CircleProgress_textSize, 16);
        int mTextColor = typedArray.getColor(R.styleable.CircleProgress_textColor, Color.BLACK);
        mProgressMax = typedArray.getInteger(R.styleable.CircleProgress_max, 100);
        typedArray.getBoolean(R.styleable.CircleProgress_isTextShow,true);

        mRoundBackGroundPaint = new Paint();
        mRoundBackGroundPaint.setColor(mCircleBgColor);
        mRoundBackGroundPaint.setAntiAlias(true);
        mRoundBackGroundPaint.setStyle(Paint.Style.STROKE);
        mRoundBackGroundPaint.setStrokeWidth(100);

        mArcPaint = new Paint();
        mArcPaint.setColor(mProgressColor);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setStrokeWidth(100);

        mTextPaint  = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(0);
        mTextPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景圆环
        float radius = 100f;
        float xCenter = getWidth()/2;
        float yCenter = getHeight()/2;
        canvas.drawCircle(xCenter,yCenter,radius,mRoundBackGroundPaint);

        //换圆弧
        RectF oval = new RectF(xCenter-radius,yCenter-radius,xCenter+radius,yCenter+radius);
        canvas.drawArc(oval,0,360 * (mCurProgress/mProgressMax),false,mArcPaint);

        //画中间文字
        int percent = (int) (mCurProgress/(float)mProgressMax * 100);
        String percentStr = String.valueOf(percent) + "%";
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        //yCenter + (fm.bottom - fm.top)/2-fm.bottom 用于求 文字基线 baseline
        canvas.drawText(percentStr,xCenter - mTextPaint.measureText(percentStr)/2,yCenter + (fm.bottom - fm.top)/2-fm.bottom,mTextPaint);
    }
    
    public void setProgress(int pProgress){
        if (pProgress<0){
            throw new IllegalArgumentException("进度Progress不能小于0");
        }
        if (pProgress>mProgressMax){
            mCurProgress = mProgressMax;
        }
        if (pProgress <= mProgressMax){
            mCurProgress = pProgress;
            postInvalidate();
        }
    }
}
