package aloha.shiningstarbase.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 不可滚动的ListView
 * Created by chenmingzhen on 16-6-12.
 */
public class NoScrollListView extends ListView {
    public NoScrollListView(Context context) {
        super(context);
        setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    public NoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private OnTouchInvalidPositionListener mTouchInvalidPosListener;

    public void setmTouchInvalidPosListener(OnTouchInvalidPositionListener mTouchInvalidPosListener) {
        this.mTouchInvalidPosListener = mTouchInvalidPosListener;
    }

    public interface OnTouchInvalidPositionListener {
        /**
         * motionEvent 可使用 MotionEvent.ACTION_DOWN 或者 MotionEvent.ACTION_UP等来按需要进行判断
         * @return 是否要终止事件的路由
         */
        boolean onTouchInvalidPosition(int motionEvent);
    }

    /**
     * 点击空白区域时的响应和处理接口
     */
    public void setOnTouchInvalidPositionListener(OnTouchInvalidPositionListener listener) {
        mTouchInvalidPosListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(mTouchInvalidPosListener == null) {
            return super.onTouchEvent(event);
        }

        if (!isEnabled()) {
            // A disabled view that is clickable still consumes the touch
            // events, it just doesn't respond to them.
            return isClickable() || isLongClickable();
        }

        final int motionPosition = pointToPosition((int) event.getX(), (int) event.getY());

        if(event.getAction()== MotionEvent.ACTION_DOWN && motionPosition == INVALID_POSITION ) {
            super.onTouchEvent(event);
            return mTouchInvalidPosListener.onTouchInvalidPosition(event.getActionMasked());
        }

        return super.onTouchEvent(event);
    }
}
