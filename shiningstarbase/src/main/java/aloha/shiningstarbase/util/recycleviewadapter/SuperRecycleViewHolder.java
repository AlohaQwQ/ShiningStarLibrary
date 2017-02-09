package aloha.shiningstarbase.util.recycleviewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Aloha on 16/6/3.
 * Recycleview 万能适配器ViewHolder
 */
public class SuperRecycleViewHolder extends RecyclerView.ViewHolder {
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    private static final String TAG = "SuperRecycleViewHolder";
    private Context mContext;
    private SparseArray<View> mViews; //控件view 集合
    private View mInflateView; //装载布局view
    private int mPosition;
    private int mLayoutId;

    public SuperRecycleViewHolder(Context context, View inflateView, ViewGroup viewGroup, int position) {
        super(inflateView);
        mContext = context;
        mInflateView = inflateView;
        mPosition = position;
        mViews = new SparseArray<View>();
        mInflateView.setTag(TAG);
        // Define click listener for the ViewHolder's View.
    }

    /**
     * ViewHolder get方法，每次需要创建ViewHolder只需要传入我们的layoutId即可。
     * @param context
     * @param convertView
     * @param viewGroup
     * @param layoutId
     * @return
     */
    public static SuperRecycleViewHolder get(Context context, View convertView, ViewGroup viewGroup, int layoutId, int position) {
        if (convertView == null) {
            View inflateView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
            SuperRecycleViewHolder holder = new SuperRecycleViewHolder(context, inflateView, viewGroup, position);
            holder.mLayoutId = layoutId;
            return holder;
        } else {
            SuperRecycleViewHolder holder = (SuperRecycleViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 泛型定义，通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        //从views 集合中获取
        View view = mViews.get(viewId);
        if (view == null) {
            view = mInflateView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * @param position
     */
    public void updatePosition(int position) {
        mPosition = position;
    }

    /**
     * @return
     */
    public View getInflateView() {
        return mInflateView;
    }

    /**
     * @return
     */
    public int getLayoutId() {
        return mLayoutId;
    }

    /**
     * 设置View的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
    }

    public void setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
    }

    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
    }

    public void setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
    }

    public void setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
    }

    public void setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
    }

    public void setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
    }

    public void setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
    }

    @SuppressLint("NewApi")
    public void setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
    }

    public void setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setVisibility(int viewId, int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
    }

    public void linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
    }

    public void setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }

    public void setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
    }

    public void setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
    }

    public void setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
    }

    public void setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
    }

    public void setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
    }

    public void setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
    }

    public void setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
    }

    public void setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
    }


    /**
     * 点击事件绑定
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
    }

    public void setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
    }

    public void setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
    }
}
