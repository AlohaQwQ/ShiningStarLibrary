package aloha.shiningstarbase.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.aloha.starworld.R;

/**
 * Created by Aloha <br>
 * -explain 多种状态视图加载工具类
 * @Date 2016/9/27 10:02
 */

public class MultiStatusView {

    /**
     * 定义MultiStatusView 单事件监听回调
     */
    public interface OnMultiStatusViewSingleCallBack {
        /**
         * 事件 监听回调
         */
        void onSingleCallBack();
    }

    /**
     * 定义MultiStatusView Dialog按钮监听回调
     */
    public interface OnDialogButtonClickListener {
        /**
         * 取消按钮 监听回调
         */
        void onPositiveButtonClick();
        /**
         * 确定按钮 监听回调
         */
        void onNegativeButtonClick();
    }

    /**
     * 定义MultiStatusView 事件回调
     */
    public interface OnMultiStatusViewCallBack {
        /**
         * Success回调
         */
        void onSuccessCallBack();
        /**
         * Error回调
         */
        void onErrorCallBack();
    }

    private int status;
    public static final int STATUS_NORMAL = 0;          // 正常状态
    public static final int STATUS_LOADING = 1;         // 加载状态
    public static final int STATUS_PROGRESS = 2;        // 加载进度状态
    public static final int STATUS_ERROR = 3;           // 错误状态

    private static FrameLayout mDecorView;
    private static FrameLayout mBackView;
    private View mLoadingView;
    private static ProgressBar mProgressBar;
    private View mErrorView;
    private static WindowManager mWindowManager = null;

    private static Context mContext;
    private static Window mWindow;


    /**
     * Created by Aloha <br>
     * @Date 2016/9/27 10:11
     * @explain show Dialog
     */
    public static void showDialog(Context context, String title, String message,boolean isCancle, final MultiStatusView.OnDialogButtonClickListener mOnDialogButtonClickListener){
        AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(isCancle);//默认为true，可以被Back键取消
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                if (mOnDialogButtonClickListener !=null)
                    mOnDialogButtonClickListener.onPositiveButtonClick();
            }
        });
        alertDialogBuilder.setNegativeButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                if (mOnDialogButtonClickListener !=null)
                    mOnDialogButtonClickListener.onNegativeButtonClick();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        //ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG); //系统中关机对话框就是这个属性
        //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.setCanceledOnTouchOutside(true);//设置是否可以点击外部消失
        alertDialog.show();
    }

    /**
     * Created by Aloha <br>
     * @Date 2016/9/28 10:57
     * @explain showLoadingView
     * @param context
     */
    public static void showLoadingView(Context context){
        /*mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        //mParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

        // 设置Window flag
        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 焦点
        //mParams.alpha = 0.1f;//窗口的透明度
        //mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        //        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        *//*
         * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
         * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
         * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
         *//*

        // 设置悬浮窗的长得宽
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.gravity = Gravity.CENTER;
        // 设置window type
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                *//*
         * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE; 那么优先级会降低一些,
         * 即拉下通知栏不可见
         *//*

*/
        mProgressBar = new ProgressBar(context);
        // 设置mProgressDialog风格
        mProgressBar.setProgress(ProgressDialog.STYLE_SPINNER);//圆形
        mProgressBar.setProgress(ProgressDialog.STYLE_HORIZONTAL);//水平*/
        mProgressBar.setBackgroundResource(R.drawable.progressbar_circle_1);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;

        Activity activity = (Activity) context;
        mDecorView =  (FrameLayout) activity.getWindow().getDecorView();
        mBackView = new FrameLayout(context);
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp1.gravity = Gravity.CENTER;
        mBackView.setBackgroundColor(Color.parseColor("#e0000000"));
        mBackView.getBackground().setAlpha(70);
        /*WindowManager.LayoutParams lp2= activity.getWindow().getAttributes();
        lp2.alpha=0.4f;
        activity.getWindow().setAttributes(lp2);
        // mWindowManager.addView(mProgressBar, mParams);*/

        mBackView.addView(mProgressBar, lp);
        mDecorView.addView(mBackView, lp1);
    }

    /**
     * Created by Aloha <br>
     * @Date 2016/9/28 10:57
     * @explain hideLoadingView
     */
    public static void hideLoadingView(){
        mDecorView.removeView(mBackView);
    }

    public void showErrorView() {}

    public void showProgressView() {}

    public void showProgressView(String msg) {}

    public void hideErrorView() {}

    public void hideProgressView() {}

}
