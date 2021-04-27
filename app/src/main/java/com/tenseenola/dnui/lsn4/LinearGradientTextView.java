package com.tenseenola.dnui.lsn4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.widget.TextView;

import com.tenseenola.dnui.R;

/**
 * 跑马灯效果的TextView
 */
public class LinearGradientTextView extends TextView {
    public static final String TAG ="LinearGradientTextView";
    private  String mTitleModeStr;
    private  int mgradientEndColo;
    private  int mgradientCenterColor;
    private  int mgradientStartColor;
    private  int mgradientSpeed;
    private  int mgradientSize;
    private  Paint mPaint;
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
     * onMeasure 完成后会走 onSizeChanged
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化,获取给当前字体画文字的paint
        mPaint = getPaint();
        //获得3个文字的宽度
        String textViewStr = getText().toString();
        //测试文字的宽度
        mTextViewWidth = mPaint.measureText(textViewStr);
        //跑马灯的宽度设置为 3个文字宽度
        float threeTextWidth = mTextViewWidth/textViewStr.length() * mgradientSize;
        /**
         * 从左边GradientSize开始，左边距离文字GradientSize开始渐变
         * 参数3：颜色按照从半透明的白色-不透明的白色-半透明白色渐变
         * 参数4：如果是CLAMP 就是跑马灯效果，如果是 MIRROR 或者REPEAT 有点像波浪效果
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
        //将 gradient 所有移动就达到了跑马灯的效果
        mLinearGradient.setLocalMatrix(mMatrix);
        postInvalidateDelayed(mgradientSpeed);
    }
}
