package aloha.shiningstarbase.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;
import aloha.shiningstarbase.logger.LogUtil;

/**
 * Created by Aloha <br>
 * -explain Activity基类：不使用MVP模式的常规界面
 * @Date 2017/2/7 17:10
 */
public class BaseNoPresenterActivity extends AppCompatActivity {

    private ProgressDialog baseProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.biu(getLocalClassName());
    }

    protected void showProgressDialog(String msg) {
        if (null == baseProgressDialog) {
            baseProgressDialog = new ProgressDialog(this);
        }

        if (null != baseProgressDialog && !baseProgressDialog.isShowing()) {
            if (!TextUtils.isEmpty(msg)) {
                baseProgressDialog.setMessage(msg);
            }
            baseProgressDialog.show();
        }
    }

    protected void dimissProgressDialog() {
        if (null != baseProgressDialog && baseProgressDialog.isShowing()) {
            baseProgressDialog.dismiss();
        }
    }

    /*****  showToast  *****/

    protected void showToast(int resId) {
        showToast(getString(resId));
    }

    protected void showToast(CharSequence msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    protected void showLongToast(int resId) {
        showLongToast(getString(resId));
    }

    protected void showLongToast(CharSequence msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    private void showToast(CharSequence msg, int duration) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, duration).show();
        }
    }
}
