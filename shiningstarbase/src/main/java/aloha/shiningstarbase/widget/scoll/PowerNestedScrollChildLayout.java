package aloha.shiningstarbase.widget.scoll;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.aloha.starworld.logger.LogUtil;

/**
 * Created by AlohaQoQ on 2016/11/7.
 */

public class PowerNestedScrollChildLayout extends LinearLayout implements NestedScrollingChild {

    private NestedScrollingChildHelper mChildHelper;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private final int[] mNestedOffsets = new int[2];
    private int mLastTouchX;
    private int mScrollPointerId;
    private int mLastTouchY;
    private int mTouchSlop;
    private boolean mIsBeingDragged;
    private int showHeight;

    public PowerNestedScrollChildLayout(Context context) {
        this(context,null);
    }

    public PowerNestedScrollChildLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PowerNestedScrollChildLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mChildHelper = new NestedScrollingChildHelper(this);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        LogUtil.biu("dispatchNestedScroll:dxConsumed:" + dxConsumed + "," +
                "dyConsumed:" + dyConsumed + ",dxUnconsumed:" + dxUnconsumed + ",dyUnconsumed:" +
                dyUnconsumed + ",offsetInWindow:" + offsetInWindow);
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        LogUtil.biu("dispatchNestedPreScroll:dx" + dx + ",dy:" + dy + ",consumed:"
                + consumed + ",offsetInWindow:" + offsetInWindow);
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        LogUtil.biu( "dispatchNestedFling:velocityX:" + velocityX + ",velocityY:" + velocityY + ",consumed:" + consumed);
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        LogUtil.biu( "dispatchNestedPreFling:velocityX:" + velocityX + ",velocityY:" + velocityY);
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        LogUtil.biu( "setNestedScrollingEnabled" + enabled);
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        LogUtil.biu( "isNestedScrollingEnabled" );
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchY = (int) (e.getRawY() + 0.5f);
                int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;
                nestedScrollAxis |= ViewCompat.SCROLL_AXIS_HORIZONTAL;
                startNestedScroll(nestedScrollAxis);
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.biu( "Child--getRawY:" + e.getRawY());
                int x = (int) (e.getX() + 0.5f);
                int y = (int) (e.getRawY() + 0.5f);
                int dx = mLastTouchX - x;
                int dy = mLastTouchY - y;
                LogUtil.biu( "child:dy:" + dy + ",mLastTouchY:" + mLastTouchY + ",y;" + y);
                mLastTouchY = y;
                mLastTouchX = x;
                if (dispatchNestedPreScroll(dx, dy, mScrollConsumed, mScrollOffset)) {
                    dy -= mScrollConsumed[1];
                    if (dy == 0) {
                        return true;
                    }
                } else {
                    scrollBy(0, dy);
                }
                break;
        }
        return true;
    }

    @Override
    public void scrollTo(int x, int y) {
        int sy = getScrollY();
        int mh = getMeasuredHeight();
        int MaxY = getMeasuredHeight() - showHeight;
        if (y > MaxY)
            y = MaxY;
        if (y < 0)
            y = 0;
        super.scrollTo(x, y);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (showHeight <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            showHeight = getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000000, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
