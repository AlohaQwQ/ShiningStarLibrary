package aloha.shiningstarbase.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.aloha.starworld.R;


/**
 * Created by AlohaQoQ on 2016/12/28.
 */

public class TimeAxisCarBehavior extends CoordinatorLayout.Behavior{

    private int targetId;
    int offsetTotal = 0;
    boolean scrolling = false;

    public TimeAxisCarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(1000);
        //child.offsetTopAndBottom(600);
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.img_time_gesture_car;
    }
    /**
     * Created by Aloha <br>
     * -explain 这里返回true，才会接受到后续滑动事件。
     * @Date 2016/12/28 16:27
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    /**
     * Created by Aloha <br>
     * -explain 进行滑动事件处理
     * @Date 2016/12/28 16:27
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout,child,target,dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed);
        //offset(child, dyConsumed);
    }

    /**
     * Created by Aloha <br>
     * -explain 当进行快速滑动
     * @Date 2016/12/28 16:27
     */
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
