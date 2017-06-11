package com.curry.stephen.lcandroidlib.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class PieChart extends ViewGroup {

    /**
     * 扇形对象的列表。
     */
    private List<Item> mData = new ArrayList<>();

    /**
     * 扇形对象列表中各对象所有数值的总和，用于计算每个扇形对象在整个饼图中所占的比例。
     */
    private float mTotal = 0.0f;

    /**
     * 确定饼图大小的矩形。
     */
    private RectF mPieBounds = new RectF();

    /**
     * 绘制扇形的Paint。
     */
    private Paint mPiePaint;

    /**
     * 绘制文字的Paint。
     */
    private Paint mTextPaint;

    /**
     * 绘制阴影的Paint。
     */
    private Paint mShadowPaint;

    /**
     * 是否显示文本。
     */
    private boolean mShowText = false;

    /**
     * 显示文字的x轴坐标。
     */
    private float mTextX = 0.0f;

    /**
     * 显示文字的y轴坐标。
     */
    private float mTextY = 0.0f;

    /**
     * 文字宽度。
     */
    private float mTextWidth = 0.0f;

    /**
     * 文字高度。
     */
    private float mTextHeight = 0.0f;

    /**
     * 文字位置（左或右）。
     */
    private int mTextPos = TEXTPOS_LEFT;

    /**
     * 扇形边界强调色。
     */
    private float mHighlightStrength = 1.15f;

    /**
     * 指针圆圈的半径。
     */
    private float mPointerRadius = 2.0f;

    /**
     * 指针起点x轴坐标。
     */
    private float mPointerX;

    /**
     * 指针起点y轴坐标。
     */
    private float mPointerY;


    /**
     * 饼图旋转数值。
     */
    private int mPieRotation;

    /**
     * OnCurrentItemChangedListener监听器。
     */
    private OnCurrentItemChangedListener mOnCurrentItemChangedListener = null;

    /**
     * 指针指向的当前扇形对象的角度。
     */
    private int mCurrentItemAngle;

    /**
     * 当前扇形对象的索引。
     */
    private int mCurrentItem = 0;

    /**
     * 在转动饼图动画结束时，是否需要继续移动指针，使其指向当前扇形的中心方向。
     */
    private boolean mAutoCenterInSlice;

    /**
     * 旋转饼图的属性动画对象。
     */
    private ObjectAnimator mAutoCenterAnimator;

    /**
     * 饼图阴影边界。
     */
    private RectF mShadowBounds = new RectF();

    /**
     * 文字位置左置。
     */
    public static final int TEXTPOS_LEFT = 0;

    /**
     * 文字位置右置。
     */
    public static final int TEXTPOS_RIGHT = 0;

    /**
     * Fling手势速率缩减倍数。
     */
    public static final int FLING_VELOCITY_DOWNSCALE = 4;

    /**
     * ObjectAnimator对象动画时长。
     */
    public static final int AUTOCENTER_ANIM_DURATION = 250;

    /**
     * 回调方法，当饼图当前指向扇形发生变化时被调用，提醒指针指向的扇形对象发生改变。
     */
    public interface OnCurrentItemChangedListener {
        void OnCurrentItemCHanged(PieChart source, int currentItem);
    }

    /**
     * 只包含上下文对象的PieChart类构造器，该构造器用于从Java代码级别创建PieChart对象。
     * @param context 上下文对象。
     */
    public PieChart(Context context) {
        super(context);
        init();
    }


    /**
     * 包含了一个上下文对象与一个属性集合的PieChart类构造器，布局引擎使用该构造器从xml文
     * 件中构建一个PieChart对象。
     * @param context 上下文对象。
     * @param attrs 包含了{@link com.curry.stephen.lcandroidlib.R.styleable.PieCh}
     */
    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    private void init() {

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

    private class Item {

        public String mLabel;

        public float mValue;

        public int mColor;

        public int mHighlight;

        /**
         * Shader用于在绘图时定义颜色渐变横幅的对象的基类。使用paint.setShader(shader)方法可以为一个Paint实例
         * （在此为paint）安装一个Shader实例。在为Paint实例安装Shader实例之后，使用此Paint实例绘制图形时，除了
         * 绘制位图（Bitmap实例）外，Paint实例将使用Shader实例为图形添加相应的颜色。
         */
        public Shader mShader;

        public int mStartAngle;

        public int mEndAngle;
    }
}













































