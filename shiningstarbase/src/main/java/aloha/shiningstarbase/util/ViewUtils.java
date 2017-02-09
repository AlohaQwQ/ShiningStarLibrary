package aloha.shiningstarbase.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * 组件辅助工具类
 * <p/>
 * 去除的方法
 * $ getXmlLinearLayout(Context context,int layoutId)
 * $ getXmlView(Context context,int layoutId)
 * $ initTextView(int id,Activity activity)
 * $ initButton(int id,Activity activity)
 * $ initEditView(int id,Activity activity)
 * $ initListView(int id,Activity activity)
 * $ setFullBar(Activity activity)
 * $ setTranslucentStatus(Activity activity,boolean on)
 * $ getRootView(Activity context)
 * $ setViewClipToPadding(Activity activity)
 * $ changeViewsByString(Context context,List<String> viewpagerViewList,int layoutId)
 *
 * Author name venco
 * Created on 2016/6/6.
 */
public class ViewUtils {

    private static final String TAG=ViewUtils.class.getSimpleName();

    private ViewUtils() {
        /**cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 检查文本框内容是否为空
     */
    public static boolean checkIsEmpty(EditText edit) {
        if (edit == null) {
            return true;
        } else if (edit.getText().toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 获取文本框中的值
     */
    public static String getEditString(EditText edit) {
        if (edit == null) {
            return null;
        } else {
            return edit.getText().toString().trim();
        }
    }

    /**
     * 判断文本框中的值是否相同
     */
    public static boolean IsSameStr(EditText edit1,EditText edit2) {
        if(ViewUtils.getEditString(edit1).equals(ViewUtils.getEditString(edit2)))
        {
            return true;
        }
        return false;
    }

    /**
     * 设置组件左边drawable
     */
    public static void setDrawableLeft(View view, int drawableId) {
        try {

            Drawable drawableImg;
            Resources res = view.getContext().getResources();
            drawableImg = res.getDrawable(drawableId);
            //调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            drawableImg.setBounds(0, 0, drawableImg.getMinimumWidth(), drawableImg.getMinimumHeight());

            if (view instanceof Button) {
                ((Button) view).setCompoundDrawables(drawableImg, null, null, null); //设置左图标
            }
            if (view instanceof TextView) {
                ((TextView) view).setCompoundDrawables(drawableImg, null, null, null); //设置左图标
            }
            if (view instanceof EditText) {
                ((EditText) view).setCompoundDrawables(drawableImg, null, null, null); //设置左图标
            }

        } catch (Exception e) {
            Logger.d(TAG,e.getMessage());
        }
    }

    /**
     * 隐藏输入法键盘
     */
    public static void hideInput(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<Activity>(activity);
        hideInput(weakReference);
    }

    /**
     * 隐藏键盘
     */
    public static void hideInput(WeakReference<Activity> activity) {
        InputMethodManager imm = (InputMethodManager) activity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.get().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.get().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 显示输入法键盘
     */
    public static void showInput(WeakReference<Activity> activity, EditText editText) {
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }


}
