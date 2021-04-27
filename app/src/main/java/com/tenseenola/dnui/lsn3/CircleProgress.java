package com.tenseenola.dnui.lsn3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tenseenola.dnui.R;

/**
 * 圆形进度条
 */
public class CircleProgress extends View {
    private float radius;
    private Paint mRoundBackGroundPaint;
    private Paint mArcPaint;
    private Paint mTextPaint;
    private boolean mIsShowText;
    private int mProgressMax;
    private int mCurProgress;

    public Paint getRoundBackGroundPaint() {
        return mRoundBackGroundPaint;
    }

    public void setRoundBackGroundPaint(Paint pRoundBackGroundPaint) {
        mRoundBackGroundPaint = pRoundBackGroundPaint;
    }

    public Paint getArcPaint() {
        return mArcPaint;
    }

    public void setArcPaint(Paint pArcPaint) {
        mArcPaint = pArcPaint;
    }

    public Paint getTextPaint() {
        return mTextPaint;
    }

    public void setTextPaint(Paint pTextPaint) {
        mTextPaint = pTextPaint;
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        //圆环背景颜色
        int mCircleBgColor = typedArray.getColor(R.styleable.CircleProgress_progressBgColor, Color.parseColor("#FFBB86FC"));
        //圆环颜色
        int mProgressColor = typedArray.getColor(R.styleable.CircleProgress_progressColor, Color.parseColor("#008577"));
        //圆环的宽度
        float mProgressWidth = typedArray.getDimension(R.styleable.CircleProgress_progressWidth, 25);
        //中间文字大小
        float mTextSize = typedArray.getDimension(R.styleable.CircleProgress_textSize, 28);
        //文字颜色
        int mTextColor = typedArray.getColor(R.styleable.CircleProgress_textColor, Color.BLACK);
        //最大进度值
        mProgressMax = typedArray.getInteger(R.styleable.CircleProgress_max, 100);
        //是否显示中间的文字
        mIsShowText = typedArray.getBoolean(R.styleable.CircleProgress_isTextShow, true);
        radius = typedArray.getDimension(R.styleable.CircleProgress_radius, 50);
        typedArray.recycle();
        //初始化圆环背景画笔
        mRoundBackGroundPaint = new Paint();
        mRoundBackGroundPaint.setColor(mCircleBgColor);
        mRoundBackGroundPaint.setAntiAlias(true);
        mRoundBackGroundPaint.setStyle(Paint.Style.STROKE);
        mRoundBackGroundPaint.setStrokeWidth(mProgressWidth);
        //初始化圆弧画笔
        mArcPaint = new Paint();
        mArcPaint.setColor(mProgressColor);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setStrokeWidth(mProgressWidth);
        //初始化文字画笔
        mTextPaint  = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(0);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景圆环
        //float radius = 100f;
        float xCenter = getWidth()/2;
        float yCenter = getHeight()/2;
        canvas.drawCircle(xCenter,yCenter,radius,mRoundBackGroundPaint);

        //换圆弧
        RectF oval = new RectF(xCenter-radius,yCenter-radius,xCenter+radius,yCenter+radius);
        canvas.drawArc(oval,0,360 * (mCurProgress/(float)mProgressMax),false,mArcPaint);

        //画中间文字
        if (!mIsShowText) {
            return;
        }
        int percent = (int) (mCurProgress/(float)mProgressMax * 100);
        String percentStr = String.valueOf(percent) + "%";
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        //yCenter + (fm.bottom - fm.top)/2-fm.bottom 用于求 文字基线 baseline
        canvas.drawText(percentStr,xCenter - mTextPaint.measureText(percentStr)/2,yCenter + (fm.bottom - fm.top)/2-fm.bottom,mTextPaint);
    }

    /**
     * 设置当前进度
     * @param pProgress
     */
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
