package aloha.shiningstarbase.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aloha.starworld.util.recycleviewadapter.MultiItemCommonAdapter;


/**
 * Created by Aloha <br>
 * -explain 我要还款-卡券pop
 * @Date 2016/10/24 14:54
 */

public class CardPopView extends PopupWindow {

    private View mCardInterestView;
    private Button btnInterestCardDetail;
    private TextView tvInterestCardDetail;

    public CardPopView(Context context, MultiItemCommonAdapter adapter, View.OnClickListener disOnClickListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       /* mCardInterestView = inflater.inflate(R.layout.view_pop_card_interest, null);
        btnInterestCardDetail = (Button) mCardInterestView.findViewById(R.id.btn_interest_card_detail);
        //recyclerInterestCardDetail = (LeRecyclerView) mCardInterestView.findViewById(R.id.recycler_interest_card_detail);
        tvInterestCardDetail = (TextView) mCardInterestView.findViewById(R.id.tv_interest_card_detail);
        if(disOnClickListener!=null)
            btnInterestCardDetail.setOnClickListener(disOnClickListener);
        //recyclerInterestCardDetail.setAdapter(adapter);
        *//*RecyclerView调用notifyItemChanged闪烁问题 *//*
        ((SimpleItemAnimator)recyclerInterestCardDetail.getItemAnimator()).setSupportsChangeAnimations(false);*/
        /*recyclerInterestCardDetail.addOnScrollListener(new RecyclerView.OnScrollListener(){
            private int totalDy = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int count = recyclerView.getChildCount();
                int nowItem = 3;
                int nextItem = 4;
                if(count>3){
                   *//* LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
                    CardView nowVisibleItem = (CardView)recyclerView.getChildAt(nowItem);
                    recyclerView.getNextFocusDownId();
                    CardView firstVisibleItem = (CardView)recyclerView.getChildAt(nextItem);
                    int itemWidth = firstVisibleItem.getWidth();
                    int firstItemRight = layoutManager.getDecoratedRight(firstVisibleItem);
                    totalDy =  (recyclerView.getAdapter().getItemCount() * itemWidth
                            - (firstItemPosition + 2) * itemWidth + firstItemRight);*//*
                }
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/
        //设置SelectPicPopupWindow的View
        this.setContentView(mCardInterestView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimCardInsterest);
        //实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0x30000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(null);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mCardInterestView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
            /*int height = mCardInterestView.findViewById(R.id.pop_layout).getTop();*/
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    /*if (y < height) {
                        dismiss();
                    }*/
                }
                return true;
            }
        });
    }

    public void setPopTitle(int chooseCardInterest){
        switch (chooseCardInterest) {
            case 1:
                tvInterestCardDetail.setText("选择卡券");
                break;
            case 2:
                tvInterestCardDetail.setText("优惠详情");
                break;
        }
    }
}

