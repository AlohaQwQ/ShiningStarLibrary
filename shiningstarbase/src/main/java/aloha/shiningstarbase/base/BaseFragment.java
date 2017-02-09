package aloha.shiningstarbase.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import aloha.shiningstarbase.logger.LogUtil;
import aloha.shiningstarbase.widget.MultiStatusView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *  Created by Aloha <br>
 *  -explain 父类Activity
 *  @Date 2016/9/26 16:47
 *  @version 1.0.0
 **/

public abstract class BaseFragment<V extends IBaseView,P extends BasePresenter<V>> extends Fragment implements IBaseView{

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    /**Bundle 缓存标识 key,用于保存 Activity 传递参数并保存。当fragment 强制初始化(如横竖屏切换)时,
     getArguments() 会返回之前setArguments() 保存的 bundle参数,并作用于fragment 初始化 onCreate().
     */
    private static final String ARG_SECTION_PAGE = "section_page";
    /**Bundle 缓存value
     * */
    private int mPage = 0;

    protected P mPresenter;
    protected Context mContext;
    protected View mContentView;
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
        mPresenter = CreatePresenter();
        if (mPresenter != null)
            mPresenter.lockContentView(this);
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
     * @Date 2016/9/26 17:42
     * @explain 创建Presenter
     */
    protected abstract P CreatePresenter();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 16:48
     * @explain 获取布局文件ID
     */
    protected abstract int getLayoutID();

    public View getContentView() {
        return mContentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unlockContentView();
        }
        unbinder.unbind();
    }

    @Override
    public void showContentView() {}

    @Override
    public void showToastShort(String msg) {
        Toast.makeText(getContextView(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastLong(String msg) {
        Toast.makeText(getContextView(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialogView() {
        this.showDialogView(" "," ",true, null);
    }

    public void showDialogView(String title, String msg,boolean isCancle, MultiStatusView.OnDialogButtonClickListener mOnDialogButtonClickListener) {
        MultiStatusView.showDialog(getContextView(),title,msg,isCancle, mOnDialogButtonClickListener);
    }

    @Override
    public void showLoadingView() {
        MultiStatusView.showLoadingView(getContextView());
    }

    @Override
    public void hideLoadingView() {
        MultiStatusView.hideLoadingView();
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showProgressView() {

    }

    @Override
    public void showProgressView(String msg) {

    }

    @Override
    public void hideDialogView() {

    }

    @Override
    public void hideErrorView() {

    }

    @Override
    public void hideProgressView() {

    }

    @Override
    public Context getContextView() {
        return this.getActivity();
    }

}
