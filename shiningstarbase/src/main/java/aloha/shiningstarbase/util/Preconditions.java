package aloha.shiningstarbase.util;

import android.support.annotation.Nullable;

/**
 * 预处理工具类，比如预先判断变量引用非空
 * Created by chenmingzhen on 16-6-6.
 */
public class Preconditions {

    /**
     * 默认的判断引用非空方法
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }



    /**
     * 判断引用非空,带错误信息参数版
     * @param reference
     * @param errorMessage 错误提示信息
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

}
