package com.curry.stephen.lcandroidlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/5/19.
 */

public class PieChart extends ViewGroup {

    private float mTextX = 0.0f;

    private float mPointerRadius = 2.0f;
    private float mPointerX;
    private float mPointerY;

    private Paint mTextPaint;

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void setLayerToSW(View view) {
        if (!view.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private void setLayerToHW(View view) {
        if (!view.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }

    private class PieView extends View {

        private float mRotation = 0;
        private Matrix mTransform = new Matrix();
        private PointF mPivot = new PointF();

        /**
         * 构造一个饼块对象。
         *
         * @param context 上下文对象。
         */
        public PieView(Context context) {
            super(context);
        }

        /**
         * 开启硬件加速（内存消耗提升）。
         */
        public void accelerate() {
            setLayerToHW(this);
        }

        /**
         * 开启软件加速，关闭硬件减速（内存消耗下降）。
         */
        public void decelerate() {
            setLayerToSW(this);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (Build.VERSION.SDK_INT < 11) {
                mTransform.set(canvas.getMatrix());
                mTransform.preRotate(mRotation, mPivot.x, mPivot.y);
                canvas.setMatrix(mTransform);
            }
        }
    }

    /**
     * 在饼图的上方绘制一个指针视图。
     */
    private class PointerView extends View {

        /**
         * 构造一个指针视图对象。
         *
         * @param context 上下文对象。
         */
        public PointerView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);// View的onDraw实现方法是空方法。

            canvas.drawLine(mTextX, mPointerY, mPointerX, mPointerY, mTextPaint);
            canvas.drawCircle(mPointerX, mPointerY, mPointerRadius, mTextPaint);
        }
    }
}













































