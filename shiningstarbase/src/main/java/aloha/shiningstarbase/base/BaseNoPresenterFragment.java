package aloha.shiningstarbase.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import aloha.shiningstarbase.logger.LogUtil;
import aloha.shiningstarbase.widget.MultiStatusView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Aloha <br>
 * -explain Fragment基类：不使用MVP模式的常规界面
 * @Date 2017/2/7 17:10
 */
public abstract class BaseNoPresenterFragment extends Fragment {

    protected Context mContext;
    protected View mContentView;
    private ProgressDialog baseProgressDialog;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutID(), container, false);
        ButterKnife.bind(this, mContentView);
        LogUtil.biu(getClass().getName());
        unbinder = ButterKnife.bind(mContentView);
        this.init(savedInstanceState);
        return mContentView;
    }

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 17:42
     * @explain 初始化数据等
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 16:48
     * @explain 获取布局文件ID
     */
    protected abstract int getLayoutID();

    public View getContentView() {
        return mContentView;
    }

    public Context getContextView() {
        return this.getActivity();
    }

    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void showContentView() {}

    public void showToastShort(String msg) {
        Toast.makeText(getContextView(),msg,Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String msg) {
        Toast.makeText(getContextView(),msg,Toast.LENGTH_LONG).show();
    }

    public void showDialogView() {
        this.showDialogView(" "," ",true, null);
    }

    public void showDialogView(String title, String msg,boolean isCancle, MultiStatusView.OnDialogButtonClickListener mOnDialogButtonClickListener) {
        MultiStatusView.showDialog(getContextView(),title,msg,isCancle, mOnDialogButtonClickListener);
    }
}
