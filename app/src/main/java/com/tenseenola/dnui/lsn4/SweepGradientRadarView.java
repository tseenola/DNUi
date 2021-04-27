package com.tenseenola.dnui.lsn4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SweepGradientRadarView extends View {
    private Paint mPain;

    public SweepGradientRadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPain = new Paint();
        float centerX = getWidth() / 2;
        float centerY = getHeight() /2;
        SweepGradient lSweepGradient = new SweepGradient(centerX,centerY, Color.parseColor("#ffffff"),
                Color.parseColor("#000000"));
        mPain.setShader(lSweepGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
