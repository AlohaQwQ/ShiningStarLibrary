package aloha.shiningstarbase.util;

import java.util.Locale;

/**
 * Created by chenmingzhen on 16-7-22.
 */
public class FormatUtils {

    /**
     * 常见的从服务端接收json数据时的格式转化问题
     * @param obj
     * @return
     */
    public static long parseLong(Object obj) {
        if (obj instanceof Double) {
            return (long)((double)obj);
        }

        if (obj instanceof Long) {
            return (long)obj;
        }

        if (obj instanceof Integer) {
            return (long)((int)obj);
        }

        throw new ClassCastException("从网络获取的数据格式有问题");
    }

    /**
     * 格式化内存单位, 保留两位小数
     * @param memorySize
     * @return
     */
    public static String formatMemorySize(long memorySize) {

       if (memorySize / 1024 < 1) {
           return String.format(Locale.CHINA, "%.2f", (float)memorySize) + "B";
       }

        if (memorySize / (1024*1024) < 1) {
            return String.format(Locale.CHINA, "%.2f", memorySize / 1024f) + "KB";
        }

        if (memorySize / (1024 * 1024 * 1024) < 1) {
            return String.format(Locale.CHINA, "%.2f", memorySize / (1024f * 1024f)) + "M";
        }

        return "0B";
    }
}
