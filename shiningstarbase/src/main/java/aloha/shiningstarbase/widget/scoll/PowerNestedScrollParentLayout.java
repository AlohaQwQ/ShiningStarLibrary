package aloha.shiningstarbase.widget.scoll;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.aloha.starworld.logger.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlohaQoQ on 2016/11/7.
 */

public class PowerNestedScrollParentLayout extends LinearLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper mParentHelper;
    private List<Integer> childHeightBox;
    private PowerNestedScrollChildLayout childLayout;
    private OverScroller mScroller;

    public PowerNestedScrollParentLayout(Context context) {
        this(context,null);
    }

    public PowerNestedScrollParentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerNestedScrollParentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        mParentHelper = new NestedScrollingParentHelper(this);
        mScroller = new OverScroller(context);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        LogUtil.biu("onStartNestedScroll--"+"child:"+child+",target:"+target+",nestedScrollAxes:"+nestedScrollAxes);
        return true;
    }


    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        LogUtil.biu("onNestedScrollAccepted"+"child:"+child+",target:"+target+",nestedScrollAxes:"+nestedScrollAxes);
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        LogUtil.biu("onStopNestedScroll--target:"+target);
        mParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        LogUtil.biu("onNestedScroll--"+"target:"+target+",dxConsumed"+dxConsumed+",dyConsumed:"+dyConsumed
                +",dxUnconsumed:"+dxUnconsumed+",dyUnconsumed:"+dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < childHeightBox.get(0);
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);
        if (hiddenTop || showTop) { //如果父亲自己要滑动，则拦截
            scrollBy(0, dy);
            consumed[1] = dy;
            LogUtil.biu("onNestedPreScroll,Parent滑动："+dy);
        }
        LogUtil.biu("onNestedPreScroll--getScrollY():"+getScrollY()+",dx:"+dx+",dy:"+dy+",consumed:"+consumed);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        LogUtil.biu("onNestedFling--target:"+target);
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        LogUtil.biu("onNestedPreFling--target:"+target);
        if (getScrollY() >= childHeightBox.get(0))
            return false;
        fling((int) velocityY);
        return true;
    }

    /*@Override
    public int getNestedScrollAxes() {
        super.getNestedScrollAxes();
        LogUtil.biu("getNestedScrollAxes");
        return 0;
    }*/

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        childHeightBox = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            LogUtil.biu("getChildCount:" + getChildCount());
            final int qq = i;
            getChildAt(i).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    childHeightBox.add(qq, getChildAt(qq).getMeasuredHeight());
                    LogUtil.biu("height:"+getChildAt(qq).getMeasuredHeight());
                }
            });
        }
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, childHeightBox.get(0));
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if(y<0)
            y=0;
        if(y>childHeightBox.get(0))
            y=childHeightBox.get(0);
        if (y != getScrollY())
            super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.biu("dispatchTouchEvent:getRawY:"+event.getRawY());
        return super.dispatchTouchEvent(event);
    }
}
