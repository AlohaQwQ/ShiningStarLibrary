package aloha.shiningstarbase.util.recycleviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aloha.starworld.logger.Log;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 * Recycleview 万能适配器基类
 * @param <T> Bean 数据源
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<SuperRecycleViewHolder> {

    protected Context mContext;
    protected int mLayoutId; //布局ID
    protected List<T> mDatas; //适配数据，使用泛型代表Bean
    protected LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    //定义点击接口，OnItemClickListener
    public interface OnItemClickListener<T> {
        void onItemClick(ViewGroup parent, View view, T t, int position);
        boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
    }

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    /**
     * 渲染具体的ViewHolder
     * @param viewGroup ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public SuperRecycleViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        SuperRecycleViewHolder viewHolder = SuperRecycleViewHolder.get(mContext, null, viewGroup, mLayoutId, -1);
        setListener(viewGroup, viewHolder, viewType);
        return viewHolder;
    }

    /**
     * 绑定ViewHolder的数据、事件。抽象出去，让用户实现。
     * @param holder
     * @param position 数据源list的下标
     */
    @Override
    public void onBindViewHolder(SuperRecycleViewHolder holder, int position) {
        holder.updatePosition(position);
        Log.i("QoQ","Two"+position);
        convert(holder,position, mDatas.get(position));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    protected void setListener(final ViewGroup parent, final SuperRecycleViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getInflateView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, view, mDatas.get(position), position);
                }
            }
        });

        viewHolder.getInflateView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    return mOnItemClickListener.onItemLongClick(parent, view, mDatas.get(position), position);
                }
                return false;
            }
        });
    }

    /**
     * SectionAdapter header标题是否开启
     * @param viewType
     * @return
     */
    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 对外抽象方法，实现onBindViewHolder 数据、事件绑定。
     * @param holder
     * @param t
     */
    public abstract void convert(SuperRecycleViewHolder holder,int position, T t);


    /*public void addData(int position) {
        mDatas.add(position, new T());
        //Recycleview 添加Item刷新,有添加剔除动画效果
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }*/
}
