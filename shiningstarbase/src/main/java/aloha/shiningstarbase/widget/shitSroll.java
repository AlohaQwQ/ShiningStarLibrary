package aloha.shiningstarbase.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by AlohaQoQ on 2016/12/26.
 */

public class shitSroll extends ViewGroup {

    public static final int CHILD_NUMBER = 6;
    private int mCurrentIndex = 0;
    // 滚动器
    protected Scroller mScroller;
    // 速度监控器
    private VelocityTracker mVelocityTracker;

    // 非滑动状态
    private static final int TOUCH_STATE_REST = 0;
    // 滑动状态
    private static final int TOUCH_STATE_SCROLLING = 1;
    // 表示当前状态
    private int mTouchState = TOUCH_STATE_REST;

    // 上一次事件的位置
    private float mLastMotionX;
    // 触发滚动的最小滑动距离，手指滑动超过该距离才认为是要拖动，防止手抖
    private int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    // 最小滑动速率，手指滑动超过该速度时才会触发翻页
    private static final int VELOCITY_MIN = 600;

    public shitSroll(Context context) {
        super(context);
        init();
        initScroller();
    }

    public shitSroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initScroller();
    }

    public shitSroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initScroller();
    }

    private void initScroller() {
        mScroller = new Scroller(getContext());
    }

    private void init() {
        // 添加几个子 View
        for (int i = 0; i < CHILD_NUMBER; i++) {
            TextView child = new TextView(getContext());
            int color;
            switch (i % 3) {
                case 0:
                    color = 0xffcc6666;
                    break;
                case 1:
                    color = 0xffcccc66;
                    break;
                case 2:
                default:
                    color = 0xff6666cc;
                    break;
            }
            child.setBackgroundColor(color);
            child.setGravity(Gravity.CENTER);
            child.setTextSize(TypedValue.COMPLEX_UNIT_SP, 46);
            child.setTextColor(0x80ffffff);
            child.setText(String.valueOf(i));
            addView(child);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        // 每个子 View 都与自己一样大
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            childView.measure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 子 View 一字排开
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(getWidth() * i, 0, getWidth() * (i + 1), b - t);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        //表示已经开始滑动了，不需要走该 ACTION_MOVE 方法了。
        if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
            return true;
        }

        final float x = ev.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
                break;

            case MotionEvent.ACTION_MOVE:
                final int xDiff = (int) Math.abs(mLastMotionX - x);
                //超过了最小滑动距离，就可以认为开始滑动了
                if (xDiff > mTouchSlop) {
                    mTouchState = TOUCH_STATE_SCROLLING;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return mTouchState != TOUCH_STATE_REST;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        // 速度监控器，监控每一个 event
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        // 触摸点
        final float eventX = event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                // 如果滚动未结束时按下，则停止滚动
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                // 记录按下位置
                mLastMotionX = eventX;
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动的位移
                int deltaX = (int)(eventX - mLastMotionX);
                // 滚动内容，前提是不超出边界
                int targetScrollX = getScrollX() - deltaX;
                if (targetScrollX >= 0 &&
                        targetScrollX <= getWidth() * (CHILD_NUMBER - 1)) {
                    scrollTo(targetScrollX, 0);
                }
                // 记下手指的新位置
                mLastMotionX = eventX;
                break;
            case MotionEvent.ACTION_UP:
                // 计算速度
                mVelocityTracker.computeCurrentVelocity(1000);
                float velocityX = mVelocityTracker.getXVelocity();
                if (velocityX > VELOCITY_MIN && canMoveToIndex(getCurrentIndex() - 1)) {
                    // 自动向右边继续滑动
                    moveToIndex(getCurrentIndex() - 1);
                } else if (velocityX < -VELOCITY_MIN && canMoveToIndex(getCurrentIndex() + 1)) {
                    // 自动向左边继续滑动
                    moveToIndex(getCurrentIndex() + 1);
                } else {
                    // 手指速度不够或不允许再滑

                    int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                    moveToIndex(targetIndex);
                }
                // 回收速度监控器
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                //修正 mTouchState 值
                mTouchState = TOUCH_STATE_REST;
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_REST;
                break;
        }

        return true;
    }

/*    *//**
     * 瞬时滚动到第几个子 View
     * @param targetIndex 要移动到第几个子 View
     *//*
    public void moveToIndex(int targetIndex) {
        if (!canMoveToIndex(targetIndex)) {
            return;
        }
        scrollTo(targetIndex * getWidth(), getScrollY());
        mCurrentIndex = targetIndex;
        invalidate();
    }*/


    /**
     * 通过动画滚动到第几个子 View
     * @param targetIndex 要移动到第几个子 View
     */
    public void moveToIndex(int targetIndex) {
        if (!canMoveToIndex(targetIndex)) {
            return;
        }
        mScroller.startScroll(
                getScrollX(), getScrollY(),
                targetIndex * getWidth() - getScrollX(), getScrollY());
        mCurrentIndex = targetIndex;
        invalidate();
    }

    /**
     * 判断移动的子 View 下标是否合法
     * @param index 要移动到第几个子 View
     * @return index 是否合法
     */
    public boolean canMoveToIndex(int index) {
        return index < CHILD_NUMBER && index >= 0;
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }


    public void stopMove() {
        if (!mScroller.isFinished()) {
            int currentX = mScroller.getCurrX();
            int targetIndex = (currentX + getWidth() / 2) / getWidth();
            mScroller.abortAnimation();
            this.scrollTo(targetIndex * getWidth(), 0);
            mCurrentIndex = targetIndex;
        }
    }

    /**
     * 在 ViewGroup.dispatchDraw() -> ViewGroup.drawChild() -> View.draw(Canvas,ViewGroup,long) 时被调用
     * 任务：计算 mScrollX & mScrollY 应有的值，然后调用scrollTo/scrollBy
     */
    @Override
    public void computeScroll() {
        boolean isNotFinished = mScroller.computeScrollOffset();
        if (isNotFinished) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
