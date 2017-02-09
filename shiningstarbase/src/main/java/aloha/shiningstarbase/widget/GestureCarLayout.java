package aloha.shiningstarbase.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.aloha.starworld.logger.LogUtil;


/**
 * Created by AlohaQoQ on 2016/12/23.
 */

public class GestureCarLayout extends LinearLayout {

    private ViewDragHelper mDragger;
    /**
     * ViewDragHelper 已持有了该两个类
     */
    //private VelocityTracker velocityTracker;
    //private Scroller mScroller;
    private Point mAutoBackOriginPos = new Point();
    private View mCarView;
    private RecyclerView mRecycleView;

    private int screenWidth;
    private int layoutWidth;

    private float moveXvel;
    private float moveYvel;

    /**
     * 手机按下时的屏幕坐标
     */
    private float touchRawx;
    private float touchRawY;

    private boolean touchEdgeYes = false;
    private boolean touchCarYes = false;
    private boolean touchTimeAxisYes = false;

    public GestureCarLayout(Context context) {
        this(context,null);
    }

    public GestureCarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GestureCarLayout(final Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        /**
         * Created by Aloha <br>
         * -explain
         * @param forParent Parent view to monitor 当前的ViewGroup
         * @param sensitivity Multiplier for how sensitive the helper should be about detecting
         *                    the start of a drag. Larger values are more sensitive. 1.0f is normal.
         *                    sensitivity传入越大，mTouchSlop的值就会越小
         * @param cb Callback to provide information and receive events 用来处理拖动到位置
         * @return a new ViewDragHelper instance
         * @Date 2016/12/23 15:15
         */
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /**
             * Created by Aloha <br>
             * -explain 捕获view, 决定哪个view可以被拖动
             * @Date 2016/12/27 9:37
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mCarView;
            }

            /**
             * Created by Aloha <br>
             * -explain 横向拖动
             * @param child Child view being dragged
             * @param left Attempted motion along the X axis 被横向移动的子控件child的左坐标left
             * @param dx Proposed change in position for left 移动距离dx
             * @return The new clamped position for left
             * @Date 2016/12/23 15:25
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
               /* LogUtil.biu("clampViewPositionHorizontal " + left + "," + dx);
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mCatView.getWidth();
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);*/
                return left;
            }

            /**
             * Created by Aloha <br>
             * -explain 纵向拖动
             * @Date 2016/12/23 15:30
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                /*final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - mDragView.getHeight();
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;*/
                //return 0;
                return top;
            }

            /**
             * Created by Aloha <br>
             * -explain 控制横向移动的边界范围，单位是像素。
             * @Date 2016/12/24 13:42
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
                //return getMeasuredWidth()-child.getMeasuredWidth();
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                //return getMeasuredHeight()-child.getMeasuredHeight();
                return super.getViewVerticalDragRange(child);
            }

            /**
             * @explain 手指释放之后的动作
             * @param releasedChild The captured child view now being released
             *      - 被捕获到的要释放的子view
             * @param xvel X velocity of the pointer as it left the screen in pixels per second.
             *      - pointer离开屏幕X轴方向每秒运动的速率,单位是px.
             * @param yvel Y velocity of the pointer as it left the screen in pixels per second.
             *      - pointer离开屏幕Y轴方向每秒运动的速率,单位是px.
             * @Date 2016/12/27 9:40
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
              /*  if (releasedChild == mCarView) {
                    if(releasedChild.getLeft()>layoutWidth-releasedChild.getWidth()){
                        mDragger.settleCapturedViewAt(layoutWidth-releasedChild.getWidth(), 0);
                        onInterceptTouchEvent(event);
                        invalidate();
                    } else {
                        mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                        invalidate();
                    }
                }*/
                moveXvel = xvel;
                moveYvel = yvel;
                LogUtil.biu("xvel:"+xvel+"-yvel:"+yvel);
                if(mAutoBackOriginPos.y+15<releasedChild.getTop() || releasedChild.getTop()<mAutoBackOriginPos.y-15){
                    /**
                     * Settle the captured view at the given (left, top) position.
                     * The appropriate velocity from prior motion will be taken into account.
                     * If this method returns true, the caller should invoke {@link #continueSettling(boolean)}
                     * on each subsequent frame to continue the motion until it returns false. If this method
                     * returns false there is no further work to do to complete the movement.
                     *
                     * @explain 实现手势惯性的效果(只要手指轻轻滑动一段距离，判断用户确实想把子View滑动到目标坐标，
                     *          即使用户中途松手或者未能滑动到目标坐标，也会自动把子View滑动到目标位置)
                     * @param finalLeft Settled left edge position for the captured view
                     * @param finalTop Settled top edge position for the captured view
                     * @return true if animation should continue through {@link #continueSettling(boolean)} calls
                     */
                    //回到初始位置
                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                } else {
                    /**
                     * Settle the captured view based on standard free-moving fling behavior.
                     * The caller should invoke {@link #continueSettling(boolean)} on each subsequent frame
                     * to continue the motion until it returns false.
                     * @explain 滑动时松手后以一定速率继续自动滑动下去并逐渐停止，(类似于扔东西)或者松手后自动滑动到指定位置
                     * @param minLeft Minimum X position for the view's left edge
                     * @param minTop Minimum Y position for the view's top edge
                     * @param maxLeft Maximum X position for the view's left edge
                     * @param maxTop Maximum Y position for the view's top edge
                     */
                    mDragger.flingCapturedView(-100 ,mCarView.getTop(), screenWidth-100,mCarView.getTop());
                    invalidate();
                    touchCarYes = true;
                }
            }

            @Override
            public void onViewDragStateChanged(int state) {
                switch (state) {
                    case ViewDragHelper.STATE_IDLE:  // view没有被拖拽或者闲置
                        if(touchEdgeYes){
                            //边缘拖动结束
                            touchEdgeYes = false;
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDragger.smoothSlideViewTo(mCarView,mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                                    postInvalidate();
                                }
                            },500);
                        }
                        if(touchCarYes){
                            LogUtil.biu("mCarView.getLeft():"+mCarView.getLeft()+"-mCarView.getRight():"+mCarView.getRight());
                            if(mCarView.getLeft()<0 || mCarView.getRight()>screenWidth-100){
                                touchCarYes = false;
                                if(moveXvel>0) {
                                    if(moveXvel>3000){
                                        moveXvel -=1000;
                                    } else if(moveYvel>2000){
                                        moveXvel -=500;
                                    } else if(moveYvel>1000){
                                        moveXvel -=200;
                                    } else {
                                        moveXvel -=100;
                                    }
                                } else if(moveXvel<0){
                                    if(moveXvel<-3000){
                                        moveXvel +=1000;
                                    } else if(moveYvel<-2000){
                                        moveXvel +=500;
                                    } else if(moveYvel<-1000){
                                        moveXvel +=200;
                                    } else {
                                        moveXvel +=100;
                                    }
                                }
                                mRecycleView.fling((int)moveXvel,(int)moveYvel);
                            }
                        }
                        break;
                    case ViewDragHelper.STATE_DRAGGING:  // 正在被拖动
                        break;
                    case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置
                        break;
                }
                super.onViewDragStateChanged(state);
            }

            /**
             * Created by Aloha <br>
             * -explain 当changedView的位置发生变化时调用，我们可以在这里面控制View的显示位置和移动
             * @Date 2016/12/27 9:41
             */
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                /*if (changedView == mCarView) {
                    distanceLeft = left;
                } else {
                    distanceLeft = distanceLeft + left;
                }
                if (distanceLeft < 0) {
                    distanceLeft = 0;
                } else if (distanceLeft > horizontalRange) {
                    distanceLeft = horizontalRange;
                }
               // layoutMenu.layout(0, 0, viewWidth, viewHeight);
               // layoutContent.layout(distanceLeft, 0, distanceLeft + viewWidth,viewHeight);
               // dispatchDragEvent(distanceLeft);*/
            }

            /**
             * Created by Aloha <br>
             * -explain 边缘滑动的时候根据滑动距离移动一个子view
             * @Date 2016/12/23 15:56
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
               // mDragger.captureChildView(mCarView, pointerId);
               // Toast.makeText(getContext(), "onEdgeDragStarted:"+edgeFlags+"-"+pointerId, Toast.LENGTH_SHORT).show();
                super.onEdgeDragStarted(edgeFlags,pointerId);
            }

            //在边界拖动时回调
            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
                if(touchRawx>0 && touchRawY>0){
                    touchEdgeYes = true;
                    mDragger.smoothSlideViewTo(mCarView,(int)touchRawx, (int)touchRawY);
                    invalidate();
                }

               // mDragger.captureChildView(mCarView, pointerId);
                /*if (mDragger.smoothSlideViewTo(mCarView, mCarView.getLeft(), 300)) {
                    ViewCompat.postInvalidateOnAnimation(mCarView);
                }*/
            }
        });
        //处理到右边缘的滑动 onEdgeTouched()
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        //mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
    }

    /**
     * Created by Aloha <br>
     * -explain 将触摸事件传递给ViewDragHelper
     * @Date 2016/12/23 15:20
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        touchRawx = event.getX();
        touchRawY = event.getY();
        /**
         * 检查是否可以拦截touch事件
         * 如果onInterceptTouchEvent可以return true 则这里return true
         */
        return mDragger.shouldInterceptTouchEvent(event);
    }

    /**
     * Created by Aloha <br>
     * -explain 将触摸事件传递给ViewDragHelper
     * @Date 2016/12/23 15:20
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // 在processTouchEvent中对ACTION_DOWN、ACTION_MOVE和ACTION_UP事件进行了处理：
        // 1.在ACTION_DOWN中调用回调接口中的tryCaptureView方法，看当前touch的view是否允许拖动
        // 在此项目中的是直接return true，两个view都是允许拖动的
        // 2.在ACTION_MOVE中，view的坐标发生改变，调用回调接口中的onViewPositionChanged方法，
        // 根据坐标信息对view进行layout，通过ViewHelper这个类中的setScaleX、setScaleY方法，实现在
        // 拖动的过程中view在XY坐标上进行相应比例的缩放；
        // 3.在ACTION_UP后调用回调接口中的onViewReleased方法，此方法中一个重要的任务是在ACTION_UP事件
        mDragger.processTouchEvent(e);
        /*LogUtil.biu("dx:"+dx+"-dy:"+dy);
        mDragger.smoothSlideViewTo(mCarView,dx,dy);
        mRecycleView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                mDragger.forceSettleCapturedViewAt();
                return false;
            }
        });*/
        return true;
    }

    @Override
    public void computeScroll() {
        if(mDragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
            //invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r, b);
        //记录起始位置
        mAutoBackOriginPos.x = mCarView.getLeft();
        mAutoBackOriginPos.y = mCarView.getTop();
        layoutWidth = this.getWidth();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCarView = getChildAt(0);
        mRecycleView = (RecyclerView) getChildAt(1);
    }

    public void smoothCarView(boolean scollDirection){
        if(scollDirection){
            mDragger.smoothSlideViewTo(mCarView,screenWidth-100,  mAutoBackOriginPos.y);
           //LogUtil.biu("Right():"+mCarView.getRight()+"smoothX:"+smoothX+"sum:"+(mCarView.getRight()+smoothX));
        } else {
            mDragger.smoothSlideViewTo(mCarView,-100,  mAutoBackOriginPos.y);
           // LogUtil.biu("Left():"+mCarView.getLeft()+"smoothX:"+smoothX+"sum:"+(mCarView.getLeft()-Math.abs(smoothX)));
        }
        invalidate();
    }

    public void smoothInitialCarView(){
        mDragger.smoothSlideViewTo(mCarView,mAutoBackOriginPos.x, mAutoBackOriginPos.y);
        invalidate();
    }
}

