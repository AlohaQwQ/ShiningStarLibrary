package aloha.shiningstarbase.widget.scoll;

import android.support.v4.view.NestedScrollingChildHelper;
import android.view.View;

/**
 * Created by AlohaQoQ on 2016/11/7.
 */

public class MyNestedScrollingChildHelper extends NestedScrollingChildHelper {

    /**
     * Construct a new helper for a given view.
     *
     * @param view
     */
    public MyNestedScrollingChildHelper(View view) {
        super(view);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return super.startNestedScroll(axes);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public void stopNestedScroll() {
        super.stopNestedScroll();
    }
}
