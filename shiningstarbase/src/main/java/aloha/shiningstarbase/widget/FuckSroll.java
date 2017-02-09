package aloha.shiningstarbase.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import static com.aloha.starworld.widget.shitSroll.CHILD_NUMBER;

/**
 * Created by AlohaQoQ on 2016/12/26.
 */

public class FuckSroll extends ViewGroup {

    private Paint paint;
    // 滚动器
    protected Scroller mScroller;
    // 速度监控器
    private VelocityTracker mVelocityTracker;

    // 上一次事件的位置
    private float mLastMotionX;
    // 触发滚动的最小滑动距离，手指滑动超过该距离才认为是要拖动，防止手抖
    private int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    // 最小滑动速率，手指滑动超过该速度时才会触发翻页
    private static final int VELOCITY_MIN = 600;


    public FuckSroll(Context context) {
        this(context,null);
    }

    public FuckSroll(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FuckSroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint == null) {
            paint = new Paint();
        }
        //super.onDraw(canvas);
        canvas.drawText("测试", mLastMotionX, 50, paint);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        return super.onInterceptTouchEvent(ev);
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
                if (velocityX > VELOCITY_MIN ) {
                    // 自动向右边继续滑动
                   // moveToIndex(getCurrentIndex() - 1);
                } else if (velocityX < -VELOCITY_MIN ) {
                    // 自动向左边继续滑动
                   // moveToIndex(getCurrentIndex() + 1);
                } else {
                    // 手指速度不够或不允许再滑

                    int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                   // moveToIndex(targetIndex);
                }
                // 回收速度监控器
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.recycle();
                break;
        }

        return true;
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
