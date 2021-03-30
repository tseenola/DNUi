package com.tenseenola.dnui.lsn4;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tenseenola.dnui.R;

/**
 * 画出圆形图片控件
 */
public class CirclePic extends View {

    private final int mBitmapWidth;
    private final int mBitmapHeight;
    private Bitmap mBitmap;
    public CirclePic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Resources resources = context.getResources();
        mBitmap = BitmapFactory.decodeResource(resources,
                R.mipmap.handsome);
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(shader);

        int width = getWidth();
        int height = getHeight();
        int viewMin = Math.min(width,height);

        int bitMin = Math.min(mBitmapHeight,mBitmapWidth);
        int min = Math.min(bitMin,viewMin);
        shapeDrawable.setBounds(0,0,min ,min );
        shapeDrawable.draw(canvas);
    }
}
