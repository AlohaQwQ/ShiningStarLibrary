package aloha.shiningstarbase.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 操作sharepreferences工具类
 *
 * Author name venco
 * Created on 2016/6/6.
 */
public class PreferencesUtils {

    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public PreferencesUtils(Context context, String name) {
        this.context = context;
        this.sp = context.getSharedPreferences(name, 0);
        this.editor = sp.edit();
    }

    public void putValue(String key, String value) {
        editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(String key) {
        return sp.getString(key, null);
    }

    public void clear() {
        editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
