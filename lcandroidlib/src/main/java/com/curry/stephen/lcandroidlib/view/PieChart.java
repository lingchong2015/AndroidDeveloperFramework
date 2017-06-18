package com.curry.stephen.lcandroidlib.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.*;
import android.widget.Scroller;


import com.curry.stephen.lcandroidlib.R;

import java.lang.Math;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

/**
 * PieChart是一个ViewGroup，包含了多个PieView与一个PointerView。
 * @author 凌冲
 * @date 2017/06/17
 */
public class PieChart extends ViewGroup {

    /**
     * Item数据链表。
     */
    private List<Item> mData = new ArrayList<Item>();

    /**
     * PieView中每个Arc所占比重的总和。
     */
    private float mTotal = 0.0f;

    /**
     * PieChart的边界信息。
     */
    private RectF mPieBounds = new RectF();

    /**
     * 绘制PieView时的基本Paint信息。
     */
    private Paint mPiePaint;

    /**
     * 绘制当前Item名称的Paint。
     */
    private Paint mTextPaint;

    /**
     * 绘制PieChart下方阴影的Paint。
     */
    private Paint mShadowPaint;

    /**
     * 是否显示当前Item名称。
     */
    private boolean mShowText = false;

    /**
     * 当前Item名称x轴坐标。
     */
    private float mTextX = 0.0f;

    /**
     * 当前Item名称y轴坐标。
     */
    private float mTextY = 0.0f;

    /**
     * 当前Item名称的文本宽度，同时用于计算PieChart布局的最小宽度与高度。
     */
    private float mTextWidth = 0.0f;

    /**
     * 当前Item名称的文本高度。
     */
    private float mTextHeight = 0.0f;

    /**
     * 当前Item名称的放置位置（PieChart左边或右边）。
     */
    private int mTextPos = TEXTPOS_LEFT;

    /**
     * PieView中Arc的边界强调色。
     */
    private float mHighlightStrength = 1.15f;

    /**
     * 指针圆圈的半径。
     */
    private float mPointerRadius = 2.0f;

    /**
     * 指针的x轴坐标。
     */
    private float mPointerX;

    /**
     * 指针的y轴坐标。
     */
    private float mPointerY;

    /**
     * PieView当前的旋转角度。
     */
    private int mPieRotation;

    /**
     * 当前Item方法变化时的回调方法。
     */
    private OnCurrentItemChangedListener mCurrentItemChangedListener = null;

    /**
     * 当前Item的文本颜色。
     */
    private int mTextColor;

    /**
     * PieChart中的PieView，实际绘制Arc的视图。
     */
    private PieView mPieView;

    /**
     * 用于计算动画的滑动偏移量数据。
     */
    private Scroller mScroller;

    /**
     * 计算动画数值并将其设置给相应的对象
     */
    private ValueAnimator mScrollAnimator;

    /**
     * 使用MotionEvent对象检测各种手势与事件。
     */
    private GestureDetector mDetector;

    /**
     * PieChart中的指针视图。
     */
    private PointerView mPointerView;

    /**
     * 当前指针视图所指向的角度。
     */
    private int mCurrentItemAngle;

    /**
     * 当前Item索引。
     */
    private int mCurrentItem = 0;

    /**
     * 是否自动对齐当前Item所对应Arc的中心线。
     */
    private boolean mAutoCenterInSlice;

    /**
     * 自动对齐当前Item所对应Arc的属性动画对象。
     */
    private ObjectAnimator mAutoCenterAnimator;

    /**
     * 阴影的边界范围。
     */
    private RectF mShadowBounds = new RectF();

    /**
     * 在PieChart的左边绘制文本。
     */
    public static final int TEXTPOS_LEFT = 0;

    /**
     * 在PieChart的右边绘制文本。
     */
    public static final int TEXTPOS_RIGHT = 1;

    /**
     * 猛推手势的速度除以该数值得到滑动距离。
     */
    public static final int FLING_VELOCITY_DOWNSCALE = 4;

    /**
     * 自动对齐动画的持续时间。
     */
    public static final int AUTOCENTER_ANIM_DURATION = 250;

    /**
     * 定义当前Item发生变化时的监听器接口。
     */
    public interface OnCurrentItemChangedListener {
        void OnCurrentItemChanged(PieChart source, int currentItem);
    }

    /**
     * 从Java代码中创建{@link PieChart}。
     *
     * @param context 创建{@link PieChart}的上下文。
     */
    public PieChart(Context context) {
        super(context);
        init();
    }

    /**
     * 使用布局引擎构建{@link PieChart}，我的理解是布局引擎可以使用这个构造方法构建{@link PieChart}，也就是Preview
     * 中看到的PieChart类形成的视图，而程序本身在Android设备运行时，Activity中的setContentView方法如果设置了包含该
     * 类的布局文件，那么这个构造方法也会在运行时被调用。<br/>
     * attrs参数是包含了布局文件中{@link PieChart}定义的属性的集合，比如在布局文件中{@link PieChart}定义了layout_height
     * 与layout_width两个属性，那么attrs中只会包含只会包含这两个属性（以及属性值），不包含其它属性。
     *
     * @param context 上下文。
     * @param attrs 包含了在布局文件中指定的{@link View}中的属性或{@link R.styleable#PieChart}中的属性。
     */
    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        // attrs包含了定义在PieChart布局文件中的xml属性，不包含定义在定义在style或theme文件中的属性，这可能会产生解析引用，通过调
        // 用obtainStyledAttributes方法可以得到最终的属性值（如果某个属性值是一个style或theme文件中某个属性的引用值，则会自动进行
        // 解析），这里的问题是attrs是不是只包含在PieChart布局文件中显示声明的属性？答案为：是的。
        //
        // 这里的解析顺序是否是先使用R.styleable.PieChart文件中声明的属性解析，然后使用View中存在的属性解析？解析的结果是包含在PieChart
        // 布局文件中声明的所有属性，还是在PieChart布局文件中声明的且存在于R.styleable.PieChart文件的属性？
        // 答案：obtainStyledAttributes方法的第二参数是一个数组，里面存储了需要解析的资源的id，这些id定义在R.java里面。第一个、第二个与
        // 第三个参数依次作为资源的解析源来解析第二个参数指定的数组中的资源，如果找到了资源的数值，那么将停止查找，例如：在第一个参数中已经找
        // 到资源的数值，那么将停止继续从第二个与第三个参数中继续查找。如果全都没有找到，那么将从程序默认的主题中查找。
        //
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PieChart,
                0, 0
        );

        try {
            mShowText = a.getBoolean(R.styleable.PieChart_showText, false);
            mTextY = a.getDimension(R.styleable.PieChart_labelY, 0.0f);
            mTextWidth = a.getDimension(R.styleable.PieChart_labelWidth, 0.0f);
            mTextHeight = a.getDimension(R.styleable.PieChart_labelHeight, 0.0f);
            mTextPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
            mTextColor = a.getColor(R.styleable.PieChart_labelColor, 0xff000000);
            mHighlightStrength = a.getFloat(R.styleable.PieChart_highlightStrength, 1.0f);
            mPieRotation = a.getInt(R.styleable.PieChart_pieRotation, 0);
            mPointerRadius = a.getDimension(R.styleable.PieChart_pointerRadius, 2.0f);
            mAutoCenterInSlice = a.getBoolean(R.styleable.PieChart_autoCenterPointerInSlice, false);
        } finally {
            // 释放TypedArray以便该类继续被其它地方使用。
            a.recycle();
        }

        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    /**
     * Returns true if the text label should be visible.
     *
     * @return True if the text label should be visible, false otherwise.
     */
    public boolean getShowText() {
        return mShowText;
    }

    /**
     * Controls whether the text label is visible or not. Setting this property to
     * false allows the pie chart graphic to take up the entire visible area of
     * the control.
     * <p>
     * 该方法会导致PieChart视图重绘。
     *
     * @param showText true if the text label should be visible, false otherwise
     */
    public void setShowText(boolean showText) {
        mShowText = showText;
        invalidate();
    }

    /**
     * Returns the Y position of the label text, in pixels.
     *
     * @return The Y position of the label text, in pixels.
     */
    public float getTextY() {
        return mTextY;
    }

    /**
     * Set the Y position of the label text, in pixels.
     * <p>
     * 该方法会导致PieChart视图重绘。
     *
     * @param textY the Y position of the label text, in pixels.
     */
    public void setTextY(float textY) {
        mTextY = textY;
        invalidate();
    }

    /**
     * Returns the width reserved for label text, in pixels.
     *
     * @return The width reserved for label text, in pixels.
     */
    public float getTextWidth() {
        return mTextWidth;
    }

    /**
     * Set the width of the area reserved for label text. This width is constant; it does not
     * change based on the actual width of the label as the label text changes.
     * <p>
     * 该方法会导致PieChart视图重绘。
     *
     * @param textWidth The width reserved for label text, in pixels.
     */
    public void setTextWidth(float textWidth) {
        mTextWidth = textWidth;
        invalidate();
    }

    /**
     * Returns the height of the label font, in pixels.
     *
     * @return The height of the label font, in pixels.
     */
    public float getTextHeight() {
        return mTextHeight;
    }

    /**
     * Set the height of the label font, in pixels.
     * <p>
     * 该方法会导致PieChart视图重绘。
     *
     * @param textHeight The height of the label font, in pixels.
     */
    public void setTextHeight(float textHeight) {
        mTextHeight = textHeight;
        invalidate();
    }

    /**
     * Returns a value that specifies whether the label text is to the right
     * or the left of the pie chart graphic.
     *
     * @return One of TEXTPOS_LEFT or TEXTPOS_RIGHT.
     */
    public int getTextPos() {
        return mTextPos;
    }

    /**
     * Set a value that specifies whether the label text is to the right
     * or the left of the pie chart graphic.
     * <p>
     * 该方法会导致PieChart视图重绘。
     *
     * @param textPos TEXTPOS_LEFT to draw the text to the left of the graphic,
     *                or TEXTPOS_RIGHT to draw the text to the right of the graphic.
     */
    public void setTextPos(int textPos) {
        if (textPos != TEXTPOS_LEFT && textPos != TEXTPOS_RIGHT) {
            throw new IllegalArgumentException(
                    "TextPos must be one of TEXTPOS_LEFT or TEXTPOS_RIGHT");
        }
        mTextPos = textPos;
        invalidate();
    }

    /**
     * Returns the strength of the highlighting applied to each pie segment.
     *
     * @return The highlight strength.
     */
    public float getHighlightStrength() {
        return mHighlightStrength;
    }

    /**
     * Set the strength of the highlighting that is applied to each pie segment.
     * This number is a floating point number that is multiplied by the base color of
     * each segment to get the highlight color. A value of exactly one produces no
     * highlight at all. Values greater than one produce highlights that are lighter
     * than the base color, while values less than one produce highlights that are darker
     * than the base color.
     * <p>
     * 该方法会导致PieChart视图重绘。
     *
     * @param highlightStrength The highlight strength.
     */
    public void setHighlightStrength(float highlightStrength) {
        if (highlightStrength < 0.0f) {
            throw new IllegalArgumentException(
                    "highlight strength cannot be negative");
        }
        mHighlightStrength = highlightStrength;
        invalidate();
    }

    /**
     * Returns the radius of the filled circle that is drawn at the tip of the current-item
     * pointer.
     *
     * @return The radius of the pointer tip, in pixels.
     */
    public float getPointerRadius() {
        return mPointerRadius;
    }

    /**
     * Set the radius of the filled circle that is drawn at the tip of the current-item
     * pointer.
     * <p>
     * 该方法会导致PieChart视图重绘。
     *
     * @param pointerRadius The radius of the pointer tip, in pixels.
     */
    public void setPointerRadius(float pointerRadius) {
        mPointerRadius = pointerRadius;
        invalidate();
    }

    /**
     * Returns the current rotation of the pie graphic.
     *
     * @return The current pie rotation, in degrees.
     */
    public int getPieRotation() {
        return mPieRotation;
    }

    private void init() {

    }

    private void setLayerToSW(View v) {
        if (!v.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private void setLayerToHW(View v) {
        if (!v.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }



    /**
     * Internal child class that draws the pie chart onto a separate hardware layer
     * when necessary.
     */
    private class PieView extends View {
        // Used for SDK < 11
        private float mRotation = 0;
        private Matrix mTransform = new Matrix();
        private PointF mPivot = new PointF();

        /**
         * Construct a PieView
         *
         * @param context
         */
        public PieView(Context context) {
            super(context);
        }

        /**
         * Enable hardware acceleration (consumes memory)
         */
        public void accelerate() {
            setLayerToHW(this);
        }

        /**
         * Disable hardware acceleration (releases memory)
         */
        public void decelerate() {
            setLayerToSW(this);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);


        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        }

        RectF mBounds;

        /*
        * 如果当前设备版本号大于11，则使用属性动画设置视图旋转角度，否则直接刷新（之后再onDraw方法中用矩阵设置视图旋转角度）。
        * setRotation方法会旋转视图本身+视图内容。
        */
        public void rotateTo(float pieRotation) {

        }

        /*
        * 如果当前设备版本号大于11，则使用属性动画设置视图中心点，否则直接刷新。
        * setPivot方法会设置（视图本身+视图内容）的中心点。
        */
        public void setPivot(float x, float y) {

        }
    }

    /**
     * View that draws the pointer on top of the pie chart
     */
    private class PointerView extends View {

        /**
         * Construct a PointerView object
         *
         * @param context
         */
        public PointerView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawLine(mTextX, mPointerY, mPointerX, mPointerY, mTextPaint);
            canvas.drawCircle(mPointerX, mPointerY, mPointerRadius, mTextPaint);
        }
    }

    /**
     * Maintains the state for a data item.
     */
    private class Item {
        public String mLabel;
        public float mValue;
        public int mColor;

        // computed values
        public int mStartAngle;
        public int mEndAngle;

        public int mHighlight;
        public Shader mShader;
    }

    /**
     * Extends {@link GestureDetector.SimpleOnGestureListener} to provide custom gesture
     * processing.
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    private boolean isAnimationRunning() {
        return !mScroller.isFinished() || (Build.VERSION.SDK_INT >= 11 && mAutoCenterAnimator.isRunning());
    }

    /**
     * 此方法用于计算相对于手指滑动前按下的位置(x, y)，手指滑动产生的滑动向量，需要注意的是，原点的坐标轴依然是饼图中心相互垂直的两条射线，为了
     * 更好的判断滑动方向，需要创建一个与向量(x, y)（注意不是点(x, y)）垂直的向量(-y, x)。通过计算向量(dx, dy)与(-y, x)的点积：
     * dot > 0  =>  两个向量的夹角在0~90度之间
     * dot == 0 =>  两个向量的夹角是90度。
     * dot < 0  =>  两个向量的夹角在90~180度之间。
     * 最终，计算出滑动向量的方向，根据此方法可以得到以下结果：
     * 当手指逆时针滑动时，滑动向量为正，顺时针为负。
     *
     * @param dx 当前滑动向量的x轴坐标。
     * @param dy 当前滑动向量的y轴坐标。
     * @param x  手指最后一次产生onScroll或onFling事件的x轴坐标。
     * @param y  手指最后一次产生onScroll或onFling事件的y轴坐标
     * @return 具有滑动方向（正值为逆时针，负值为顺时针）的距离标量。
     */
    private static float vectorToScalarScroll(float dx, float dy, float x, float y) {
        float l = (float) Math.sqrt(dx * dx + dy * dy);

        float dot = (-y * dx + x * dy);
        float sign = Math.signum(dot);

        return l * sign;
    }


}
