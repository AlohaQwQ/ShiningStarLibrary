package aloha.shiningstarbase.util.recycleviewadapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * 多种ItemViewType 适配器
 * @param <T> Bean 数据源
 */
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    //实现MultiItemTypeSupport 接口,多种ItemViewType 适配
    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas,
                                  MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }

    /**
     * 渲染具体的ViewHolder
     * @param viewGroup ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public SuperRecycleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (mMultiItemTypeSupport == null) {
            return super.onCreateViewHolder(viewGroup, viewType);
        }
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        SuperRecycleViewHolder holder = SuperRecycleViewHolder.get(mContext, null, viewGroup, layoutId, -1);
        setListener(viewGroup, holder, viewType);
        return holder;
    }


    /**
     * 决定元素的布局使用哪种类型
     * @param position 数据源List的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport == null) {
            return super.getItemViewType(position);
        }
        return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
    }
}
