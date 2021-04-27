package com.tenseenola.dnui.lsn9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class GarbageView extends View {
    public static final int WITH_COUNT = 8;//宽度分为8份，垃圾桶占用中间4份
    public static final int HEIGHT_COUNT = 4;//高度分为4份，垃圾桶占用中间2份
    private final Paint mPaint;
    private int mWidh;//画布总宽度
    private int mHeight;//画布总高度
    private int mPerWidth;
    private int mPerHeight;

    private float mLeftTopX;
    private float mLeftBottomX;
    private float mRightBottomX;
    private float mRightUpX;
    private int mLeftTopY;
    private int mLeftBottomY;
    private int mRightBottomY;
    private int mRightUpY;

    public GarbageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidh = getWidth();
        mHeight = getHeight();
        mPerWidth = mWidh/WITH_COUNT;
        mPerHeight = mHeight/HEIGHT_COUNT;

        mLeftTopX = mWidh/2-mPerWidth*2;//左上角x
        mRightUpX = mWidh/2+mPerWidth*2;//右上角x

        mLeftBottomX = mWidh/2-mPerWidth*1;//左下角x
        mRightBottomX = mWidh/2+mPerWidth*1;//右下角x

        mLeftTopY = mHeight/2- mPerHeight*1;//左上角y
        mRightUpY = mHeight/2 - mPerHeight*1;//右上角y

        mLeftBottomY = mHeight/2 + mPerHeight*1;//左下角y
        mRightBottomY = mHeight/2 + mPerHeight*1;//右下角y
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path lPath = new Path();
        //=================先画垃圾桶捅身
        //1、先移动到左上角
        lPath.moveTo(mLeftTopX,mLeftTopY);
        //2、连接左下角
        lPath.lineTo(mLeftBottomX,mLeftBottomY);
        //3、连接右下角
        lPath.lineTo(mRightBottomX,mRightBottomY);
        //4、连接右上角
        lPath.lineTo(mRightUpX,mRightUpY);
        canvas.drawPath(lPath,mPaint);

        //=================画垃圾桶中级的三条竖线
        //1、先画中间的竖线
        canvas.drawLine(mWidh/2,mHeight/2-mPerHeight/2,mWidh/2,mHeight/2+mPerHeight,mPaint);
        //2、再画左边的竖线
        canvas.drawLine(mWidh/2-mPerWidth,mHeight/2-mPerHeight/2,mWidh/2-mPerWidth,mHeight/2+mPerHeight,mPaint);
        //3、再画右边的竖线
        canvas.drawLine(mWidh/2+mPerWidth,mHeight/2-mPerHeight/2,mWidh/2+mPerWidth,mHeight/2+mPerHeight,mPaint);

        //画盖子
        canvas.drawLine(mLeftTopX-10,mLeftTopY,mRightUpX,mRightUpY,mPaint);
        RectF lRect = new RectF(mWidh/2-mPerWidth,mLeftTopY-mPerHeight/2 ,mWidh/2+mPerWidth,mLeftTopY+mPerHeight/2);
        //mPaint.setColor(Color.BLACK);
        //canvas.drawRect(lRect,mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(lRect, 180f,180f,false,mPaint);
    }
}
