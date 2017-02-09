package aloha.shiningstarbase.logger;

/**
 * Created by AlohaQoQ on 2016/9/26.
 */

public class LogUtil {
    private static final Boolean DEBUG = true;

    public static void biu(String msg) {
        if (DEBUG) {
            android.util.Log.i("QwQ", msg);
        }
    }
}
