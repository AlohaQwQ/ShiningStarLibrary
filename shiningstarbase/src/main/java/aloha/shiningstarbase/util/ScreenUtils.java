package aloha.shiningstarbase.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 屏幕设置相关类
 * Created by chenmingzhen on 16-6-6.
 */
public class ScreenUtils {

    private ScreenUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public static void setBackgroundAlpha( Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 获取屏幕宽度
     * */
    public static int getScreenWidth(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     * */
    public static int getScreenHeight(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
}
