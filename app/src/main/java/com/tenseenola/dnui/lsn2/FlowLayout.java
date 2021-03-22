package com.tenseenola.dnui.lsn2;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局
 */
public class FlowLayout extends ViewGroup {
    private List<List<View>> mLines = new ArrayList<>();
    private List<View> mLineViews = new ArrayList<>();
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(this.getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取父容器为FlowLayout设置的测量模式和大小
        int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);

        int parentHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        int maxWindowWidth = getScreenSize(this.getContext())[0];
        int measureWidth = 0;//最大宽度
        int measureHeight = 0;//所有子控件最大高度之和

        if (parentWidthMode == MeasureSpec.EXACTLY && parentHeightMode == MeasureSpec.EXACTLY){
            measureWidth = parentWidthSize;
            measureHeight = parentHeightSize;
        }else {
            //测量子View
            int childCount = getChildCount();

            int lineWidth = 0;//每一行view的宽度之和
            int lineHeight = 0;//没一行view中最大高度的高度

            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                measureChild(childView,widthMeasureSpec,heightMeasureSpec);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                int childWidth = layoutParams.leftMargin + childView.getMeasuredWidth() +  layoutParams.rightMargin;
                int childHeight = layoutParams.topMargin +childView.getMeasuredHeight() +  layoutParams.bottomMargin;

                if ((lineWidth + childWidth)>parentWidthSize){
                    /**
                     * 当前View的宽度大于父容器的宽度 需要换行
                     * 换行时 1、高度累加 2、宽度取最宽的 3、将当前一行的view加到 mLines
                     *       4、为linewidth 和 lineheight赋值 5、重置mLineViews并且添加第一个view
                     */
                    measureHeight += lineHeight;//1、高度累加
                    measureWidth = Math.max(measureWidth,lineWidth);//2、宽度取最宽的
                    mLines.add(mLineViews);//3、将当前一行的view加到 mLines


                    lineWidth = childWidth;//4、为linewidth 和 lineheight赋值
                    lineHeight = childHeight;//4、为linewidth 和 lineheight赋值

                    //重置mLineViews
                    mLineViews = new ArrayList<>();//5、重置mLineViews并且添加第一个view
                    mLineViews.add(childView);//5、重置mLineViews并且添加第一个view
                }else {
                    /**
                     * 不需要换行，直接累加在一行
                     * 1、宽度累加 2、高度取最高的 3、添加子view到一行的list中
                     */
                    lineWidth += childWidth;//1、宽度累加
                    lineHeight = Math.max(lineHeight,childHeight);//2、高度取最高的

                    mLineViews.add(childView);//3、添加子view到一行的list中
                }

                //如果是最后一个，需要换行
                if(i == childCount-1){
                    // 1
                    measureWidth = Math.max(measureWidth,lineWidth);
                    measureHeight += lineHeight;
                    mLines.add(mLineViews);
                }
            }
            setMeasuredDimension(measureWidth,measureHeight);
        }
    }

    /**
     * 遍历每一行，再遍历每一行中的每个view，并为每个view设置layout属性
     * @param pChanged
     * @param pLeft
     * @param pTop
     * @param pRight
     * @param pBottom
     */
    protected void onLayout(boolean pChanged, int pLeft, int pTop, int pRight, int pBottom) {
        int childLeft = 0;
        int childTop = 0;
        int childRight = 0;
        int childBottom = 0;

        int totalLeft = 0;
        int totalTop = 0;
        int lines = mLines.size();
        for (int i = 0; i < lines; i++) {
            List<View> lineViews = mLines.get(i);
            int lineViewsSize = lineViews.size();
            int minTop = 0;
            int maxBotton = 0;
            for (int i1 = 0; i1 < lineViewsSize; i1++) {
                View childView = lineViews.get(i1);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                childLeft = totalLeft + layoutParams.leftMargin;//view的左坐标是总的左坐标加上当前view的 leftMargin
                totalLeft = childLeft + childView.getMeasuredWidth()+ layoutParams.rightMargin;
                childTop = totalTop + layoutParams.topMargin;//view的上坐标是总的上坐标加上当前view的topMargin
                childRight = childLeft + childView.getMeasuredWidth() ; //右坐标是view的左坐标加上view的宽度
                childBottom = childTop + childView.getMeasuredHeight();//下坐标是view的上坐标加上view的高度
                if (i1 == 0){
                    minTop = childTop ;
                    maxBotton = childBottom;
                }
                childView.layout(childLeft,childTop,childRight,childBottom);
                //获取一行中top最小的
                minTop = Math.min(minTop,childTop);
                //获取一行中bottom最大的，
                maxBotton =  Math.max(maxBotton,childBottom);
            }
            totalLeft = 0;//一行绘制完成后，总Left重置为0；
            totalTop = totalTop + (maxBotton - minTop)  + 5;//总top要累积起来,  +5为了分开，免得上下合在一起了
        }
        //绘制完成后清空缓存，不然会一直累加内存泄露
        mLineViews.clear();
        mLines.clear();
    }

    public   int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
}
