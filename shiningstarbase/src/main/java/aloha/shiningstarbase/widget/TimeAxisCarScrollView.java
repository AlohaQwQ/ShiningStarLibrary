package aloha.shiningstarbase.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by AlohaQoQ on 2016/12/28.
 */

public class TimeAxisCarScrollView extends HorizontalScrollView {

    public TimeAxisCarScrollView(Context context) {
        this(context,null);
    }

    public TimeAxisCarScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeAxisCarScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
