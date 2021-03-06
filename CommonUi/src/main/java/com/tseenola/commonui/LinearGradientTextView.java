package com.tseenola.commonui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class LinearGradientTextView extends TextView {
    public static final String TAG ="LinearGradientTextView";
    private  String mStartDirection;
    private  String mTitleModeStr;
    private  int mgradientEndColo;
    private  int mgradientCenterColor;
    private  int mgradientStartColor;
    private  int mgradientSpeed;
    private  int mgradientSize;
    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private float mTranslate;
    private float DELTAX = 20;
    private float mTextViewWidth;
    private Shader.TileMode mTileMode;
    public LinearGradientTextView(Context context) {
        super(context);
    }

    public LinearGradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearGradientTextView);
        mgradientSize = typedArray.getInteger(R.styleable.LinearGradientTextView_Gradient_Size, 3);
        mgradientSpeed = typedArray.getInteger(R.styleable.LinearGradientTextView_Speed, 150);
        mgradientStartColor = typedArray.getColor(R.styleable.LinearGradientTextView_Start_Color, Color.parseColor("#22ffffff"));
        mgradientCenterColor = typedArray.getColor(R.styleable.LinearGradientTextView_Center_Color, Color.parseColor("#ffffffff"));
        mgradientEndColo = typedArray.getColor(R.styleable.LinearGradientTextView_End_Color, Color.parseColor("#22ffffff"));
        mTitleModeStr = typedArray.getString(R.styleable.LinearGradientTextView_TileModes);
        mStartDirection = typedArray.getString(R.styleable.LinearGradientTextView_StartDirection);

        typedArray.recycle();
        mTileMode = Shader.TileMode.CLAMP;
        if (mTitleModeStr == null) {
            return;
        }
        switch (mTitleModeStr){
            case "CLAMP":
                mTileMode = Shader.TileMode.CLAMP;
                break;
            case "REPEAT":
                mTileMode = Shader.TileMode.REPEAT;
                break;
            case "MIRROR":
                mTileMode = Shader.TileMode.MIRROR;
                break;
            default:
                mTileMode = Shader.TileMode.CLAMP;
                break;
        }
    }

    /**
     * onMeasure ??????????????? onSizeChanged
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //?????????,?????????????????????????????????paint
        mPaint = getPaint();
        //??????3??????????????????
        String textViewStr = getText().toString();
        //?????????????????????
        mTextViewWidth = mPaint.measureText(textViewStr);
        //??????????????????????????? 3???????????????
        float threeTextWidth = mTextViewWidth/textViewStr.length() * mgradientSize;
        /**
         * ?????????GradientSize???????????????????????????GradientSize????????????
         * ??????3????????????????????????????????????-??????????????????-?????????????????????
         * ??????4????????????CLAMP ????????????????????????????????? MIRROR ??????REPEAT ?????????????????????
         */
        mLinearGradient = new LinearGradient(-threeTextWidth, 0,threeTextWidth , 0, new int[]{
                mgradientStartColor,  mgradientCenterColor,mgradientEndColo}, null, mTileMode);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTranslate += DELTAX;
        if(mTranslate > mTextViewWidth + 1 || mTranslate < 1){
            DELTAX = - DELTAX;
        }

        mMatrix = new Matrix();
        mMatrix.setTranslate(mTranslate, 0);
        Log.d(TAG, "onDraw: mTranslate " + mTranslate);
        //??? gradient ??????????????????????????????????????????
        mLinearGradient.setLocalMatrix(mMatrix);
        postInvalidateDelayed(mgradientSpeed);
    }
}