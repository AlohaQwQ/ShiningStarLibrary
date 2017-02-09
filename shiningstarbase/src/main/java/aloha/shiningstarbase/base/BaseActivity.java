package aloha.shiningstarbase.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
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
public abstract class BaseActivity<V extends IBaseView,P extends BasePresenter<V>> extends AppCompatActivity implements IBaseView{

    protected P mPresenter;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutID());
        unbinder = ButterKnife.bind(this);
        MyApplication.getApplicationInstance().pushOneActivity(this);
        LogUtil.biu(getLocalClassName());
        mPresenter = CreatePresenter();
        if (mPresenter != null)
            //Presenter 绑定 View
            mPresenter.lockContentView(this);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new TestFragment("param")).commit();
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        this.init();
    }

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 17:42
     * @explain 初始化数据等
     */
    protected abstract void init();

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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
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
        return this;
    }


    /**
     * 默认的页面切换效果
     * @param intent
     * @param options
     *//*
    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
        onSwitchActivityAnimation();
    }


    private void startActivityNoAnim(Intent intent, Bundle options) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            super.startActivity(intent, options);
        } else {
            super.startActivity(intent);
        }
    }

    *//**
     * 自定义切换特效的Activity
     * @param intent
     *//*
    public void startActivity(Intent intent, int enterAnim, int outAnim) {
        startActivityNoAnim(intent, null);
        overridePendingTransition(enterAnim, outAnim);
    }

    *//**
     * 默认的页面切换效果
     *//*
    //// TODO: 16-8-1, 写定集中切换方式
    protected void onSwitchActivityAnimation() {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    protected void onFinishAnimation() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onFinishAnimation();
    }
*/
}