package com.tenseenola.dnui.lsn4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画最上面一条线
        drawTopLine(canvas);
        //画中间一条线
        drawCenterLine(canvas);
        //画下面一条线
        drawBottom(canvas);
    }


    /**
     * @param canvas
     */
    private void drawTopLine(Canvas canvas) {
        canvas.drawLine(mPerWith ,mPerHeight,mPerWith*2,mPerHeight,mPaint);
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
        canvas.drawLine(mPerWith,mPerHeight*3,mPerWith*2,mPerHeight*3,mPaint);
    }
}
