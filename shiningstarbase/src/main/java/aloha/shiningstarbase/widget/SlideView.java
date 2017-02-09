package aloha.shiningstarbase.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ImageView;
import android.widget.Scroller;

import com.aloha.starworld.logger.LogUtil;

/**
 * Created by AlohaQoQ on 2016/12/26.
 */

public class SlideView extends ImageView {

    private Paint paint;
    private VelocityTracker velocityTracker;
    private Scroller scroller;
    private float lastX;
    private float currentX = 200;

    public SlideView(Context context) {
        super(context);
        scroller = new Scroller(context);
        // TODO Auto-generated constructor stub
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        scroller = new Scroller(context);
    }

    public SlideView(Context context, AttributeSet attrs,
                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        // TODO Auto-generated constructor stub
    }

    // public HelloScrollerView(Context context, AttributeSet attrs,
    //         int defStyleAttr, int defStyleRes) {
    //     super(context, attrs, defStyleAttr, defStyleRes);
    //     // TODO Auto-generated constructor stub
    // }

    @Override
    public void onDraw(Canvas canvas) {
        if (paint == null) {
            paint = new Paint();
        }
        //super.onDraw(canvas);
        canvas.drawText("测试", currentX, 50, paint);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("KeyEventView", " " + keyCode);
        //     textView.setText(KeyEvent.keyCodeToString(keyCode) + "");
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.e("KeyEventView", " " + keyCode);
        //     textView.setText(KeyEvent.keyCodeToString(keyCode) + "");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        LogUtil.biu("onTouchEvent");

        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        //将移动信息传递给VelocityTracker
        velocityTracker.addMovement(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                lastX = ev.getX();
                LogUtil.biu("ACTION_DOWN:"+lastX);
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = (currentX + (ev.getX() - lastX));
                lastX = ev.getX();
                LogUtil.biu("ACTION_MOVE:"+lastX);
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.biu("ACTION_UP");
                //当手指抬起时，使VelocityTracker计算手指移动的速度
                velocityTracker.computeCurrentVelocity(1000);
                //然后将手指在水平方向移动的速度做为参数传递给Scroller.fling. fling函数用来开始一个滑动减速计算
                scroller.fling((int) currentX, 0, (int) velocityTracker.getXVelocity(), 0, 0, 99999999, 0, 0);
                velocityTracker.recycle();
                velocityTracker = null;
                if (!scroller.computeScrollOffset()) {
                }
                break;
        }
        //在每一次手指移动时，都要调用View的invalidate()函数. 系统之后会调用View的onDraw函数来对界面进行重绘。
        invalidate();
        LogUtil.biu("invalidate");

        return true;
    }

    //在重绘的时候，View会调用自己的computeScroll()函数，
    // 在这个函数中，我们向Scroller询问滑动是否还在进行（109行），如果正在滑动，则获取当前的X值，并调用invalidate()进行再次重绘。
    @Override
    public void computeScroll() {
        //scrollBy(-1,0);
        LogUtil.biu("computeScroll");
        if (scroller.computeScrollOffset()) {
            currentX = scroller.getCurrX();
            LogUtil.biu("computeScroll-currentX:"+currentX);

            invalidate();
        } else {
        }
    }


}