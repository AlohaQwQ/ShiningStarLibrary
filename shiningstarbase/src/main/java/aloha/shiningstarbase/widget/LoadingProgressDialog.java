package aloha.shiningstarbase.widget;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Aloha
 * @Date 2016/9/27 16:06
 * @explain
 */

public class LoadingProgressDialog extends AlertDialog{


    protected LoadingProgressDialog(Context context) {
        super(context);
    }

    protected LoadingProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected LoadingProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
