package com.tenseenola.dnui.lsn4;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tenseenola.dnui.R;

public class CirclePic2 extends View {

    private final int mBitmapWidth;
    private final int mBitmapHeight;
    private Bitmap mBitmap;
    public CirclePic2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Resources resources = context.getResources();
        mBitmap = BitmapFactory.decodeResource(resources,
                R.mipmap.handsome);
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
    }

    /*@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //图片的宽和高不同，将长边缩短，或者将短边缩长.本套代码选择将短边缩长
        Matrix matrix = new Matrix();
        bitmapShader.setLocalMatrix(matrix);
        int min = Math.min(mBitmapWidth,mBitmapHeight);
        float scale = 0.0f;
        if (min == mBitmapWidth){
            scale = (float) mBitmapHeight/min;
            matrix.setScale(scale,1.0f);
        }else {
            scale = (float) mBitmapWidth/min;
            matrix.setScale(1.0f,scale);
        }

        int width = getWidth();
        int height = getHeight();
        int min1 = Math.min(width,height);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(bitmapShader);
        shapeDrawable.setBounds(0,0, min1 ,min1);
        shapeDrawable.draw(canvas);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //图片的宽和高不同，将长边缩短，或者将短边缩长.本套代码选择将长边缩短
        Matrix matrix = new Matrix();

        int bitmapMax = Math.max(mBitmapWidth,mBitmapHeight);
        int bitmapMin = Math.min(mBitmapWidth,mBitmapHeight);
        float bitmapScaleX = 1.0f;
        float bitmapScaleY = 1.0f;
        if (bitmapMax == mBitmapWidth){
            bitmapScaleX = (float) mBitmapHeight/bitmapMax;
        }else {
            bitmapScaleY = (float) mBitmapWidth/bitmapMax;
        }
        //用户xml布局中的长度和宽度也会不同，如果xml最短边和bitmap最短边还需要做一个缩放
        int xmlWidth = getWidth();
        int xmlHeight = getHeight();
        int xmlMin = Math.min(xmlWidth,xmlHeight);

        float xmlScale =(float) xmlMin / bitmapMin;
        matrix.setScale(xmlScale*bitmapScaleX,xmlScale*bitmapScaleY);
        bitmapShader.setLocalMatrix(matrix);

        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(bitmapShader);
        shapeDrawable.setBounds(0,0, xmlMin ,xmlMin);
        shapeDrawable.draw(canvas);
    }

}