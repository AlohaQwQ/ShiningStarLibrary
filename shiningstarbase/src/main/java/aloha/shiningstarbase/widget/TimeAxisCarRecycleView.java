package aloha.shiningstarbase.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by AlohaQoQ on 2016/12/28.
 */

public class TimeAxisCarRecycleView extends RecyclerView {

    private View anchorView;
    private int xCoordinate;
    private int yCoordinate;
    private int initialPosition = -1;
    private boolean scollDirection;

    public TimeAxisCarRecycleView(Context context) {
        this(context, null);
    }

    public TimeAxisCarRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeAxisCarRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(linearLayoutManager);

    }

    public void setAnchorView(View view,int xCoordinate,int yCoordinate){

    }

    /**
     * Created by Aloha <br>
     * -explain 还能向下滑动多少距离
     * @Date 2016/12/29 10:16
     */
    public int getDistance(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        View firstVisibleItem = this.getChildAt(0);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        int recycleViewHeight = this.getHeight();
        int itemHeight = firstVisibleItem.getHeight();
        int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibleItem);
        return (itemCount - firstItemPosition - 1) * itemHeight - recycleViewHeight + firstItemBottom;
    }

    /**
     * Created by Aloha <br>
     * -explain 已滑动的距离
     * @Date 2016/12/29 10:17
     */
    public int getScrolledDistance(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        View firstVisibleItem = this.getChildAt(0);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        int itemHeight = firstVisibleItem.getHeight();
        int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibleItem);
        return ( firstItemPosition + 1) * itemHeight - firstItemBottom;
    }

    /**
     * Created by Aloha <br>
     * -explain 总的距离
     * @Date 2016/12/29 10:17
     */
    public int getTotalDistance(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        View firstVisibleItem = this.getChildAt(0);
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        int itemHeight = firstVisibleItem.getHeight();
        //int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibleItem);
        return itemCount * itemHeight ;
    }

    /**
     * Created by Aloha <br>
     * -explain 获取当前显示的第一个item position
     * @Date 2016/12/29 10:17
     */
    public int getfirstVisibleItemPosition(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        return firstVisibleItemPosition;
    }

    /**
     * Created by Aloha <br>
     * -explain 获取时间轴左边位置
     * @Date 2016/12/29 10:17
     */
    public int getTimeAxisLeft(){
        View firstVisibleItem = this.getChildAt(0);
        int itemHeight = firstVisibleItem.getHeight();
        if(initialPosition>0){
            return (initialPosition-1) * itemHeight ;
        } else {
            return 0;
        }
    }

    /**
     * Created by Aloha <br>
     * -explain 获取时间轴右边位置
     * @Date 2016/12/29 10:17
     */
    public int getTimeAxisRight(){
        View firstVisibleItem = this.getChildAt(0);
        int itemHeight = firstVisibleItem.getHeight();
        if(initialPosition>0){
            return (initialPosition+1) * itemHeight ;
        } else {
            return 0;
        }
    }

    public void setInitialPosition(int position){
        initialPosition = position;
    }

}
