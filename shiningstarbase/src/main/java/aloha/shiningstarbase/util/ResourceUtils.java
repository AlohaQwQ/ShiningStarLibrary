package aloha.shiningstarbase.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 读取资源文件的工具类
 * Created by chenmingzhen on 16-6-7.
 */
public class ResourceUtils {
    private static final String TAG = ResourceUtils.class.getSimpleName();

    /**
     * 读取Assert目录下的文件
     * @param context
     * @param fileName
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        String Result = "";
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            bufReader = new BufferedReader(inputReader);
            String line;

            while ((line = bufReader.readLine()) != null)
                Result += line;
        } catch (Exception e) {
            Log.e("error!", e.getMessage(), e);
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if(inputReader!=null){
                    inputReader.close();
                }
            } catch (IOException e) {
                Logger.d(TAG,e.getMessage());
            }
        }
        return Result;
    }


    public static Resources getResource() {
          //  return AppConstants.getApplication().getResources();
        return null;
    }

    /**
     * 获取string.xml下的字符串资源
     * @param resId
     * @param formatArgs
     * @return
     */
    public static String getString(int resId, Object... formatArgs) {
        try {
            //return AppConstants.getApplication().getResources().getString(resId, formatArgs);
        } catch (Exception e) {
            Logger.d(TAG,e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取字符串数组
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        try {
           // return AppConstants.getApplication().getResources().getStringArray(resId);
        } catch (Exception ex) {
            Logger.e(TAG, ex.getMessage());
        }

        return null;
    }

    /**
     * 获取颜色
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
      //  return ContextCompat.getColor(AppConstants.getApplication(), resId);
        return 0;
    }


    public static Drawable getDrawable(int resId) {
       // return ContextCompat.getDrawable(AppConstants.getApplication(), resId);
        return null;
    }

}
