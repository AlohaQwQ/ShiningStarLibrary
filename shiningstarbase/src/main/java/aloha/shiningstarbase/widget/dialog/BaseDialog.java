package aloha.shiningstarbase.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by roly on 16/6/5.
 */
public class BaseDialog {

    /**
     * 定义DialogButton 监听回调
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

    //show Dialog
    public static void showDialog(Context context,String title,String message,final OnDialogButtonClickListener mOnDialogButtonClickListener){
        AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);//默认为true，可以被Back键取消
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                mOnDialogButtonClickListener.onPositiveButtonClick();
            }
        });
        alertDialogBuilder.setNegativeButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                mOnDialogButtonClickListener.onNegativeButtonClick();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        //ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG); //系统中关机对话框就是这个属性
        //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.setCanceledOnTouchOutside(true);//设置是否可以点击外部消失
        alertDialog.show();
    }
}
