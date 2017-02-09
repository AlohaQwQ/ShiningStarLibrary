package aloha.shiningstarbase.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * 用于操作文件夹路径的类
 * Created by chenmingzhen on 16-5-31.
 */
public class AppPathUtils {

    private static final String TAG = AppPathUtils.class.getSimpleName();

    //App文件夹的路径,包括该App的文件名
    private static String APP_DIR;

   //private static String APP_FOLDER = File.separator + EshineConfig.APP_FOLDER + File.separator;

    //系统内部缓存路径
    private static String CACHE_DIR = "CACHE";

    //奔溃日志的文件路径
    private static String CRASH_LOG = "CRASH_LOG";

    //普通日志的文件位置
    private static String LOG_DIR = "LOG";

    //缓存图片的位置
    //private static final String IMAGE_CACHE_DIR = EshineConfig.IMAGE_CACHE_PATH;

    //http缓存的位置
    //private static final String HTTP_CACHE_PATH = EshineConfig.HTTP_CACHE_PATH;

    //更新App时，下载增量包或者整包的位置
    private static final String APK_CACHE = "APK_CACHE";

    private volatile static AppPathUtils sInstance = new AppPathUtils();

    public static AppPathUtils getInstance() {
        synchronized (TAG) {
            if (null == sInstance) {
                sInstance = new AppPathUtils();
            }

            if (TextUtils.isEmpty(APP_DIR)) {
                boolean sdCardExist = Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED);
                if (sdCardExist) {
                   // APP_DIR = Environment.getExternalStorageDirectory() + APP_FOLDER;
                } else {
                    String path = getInternalPath();
                    if (!TextUtils.isEmpty(path)) {
                     //   APP_DIR = path + APP_FOLDER;
                    } else {
                        APP_DIR = CACHE_DIR;
                    }
                }
            }
            return sInstance;
        }
    }


    /**
     * 奔溃日志文件夹路径
     */
    public String getCrashLogDir() {
       return FileUtils.getFileMkDirPath(APP_DIR + CRASH_LOG);
    }

    /**
     * 普通日志文件夹路径
     */
    public String getLogDir() {
        return FileUtils.getFileMkDirPath(APP_DIR + LOG_DIR);
    }

    /**
     * APK下载的路径
     */
    public String getAPKDownloadDir() {
        return FileUtils.getFileMkDirPath(APP_DIR + APK_CACHE);
    }

    /**
     * 缓存图片的绝对路径
     * @return
     */
    public String getImageDiskCacheDir() {
      //  return FileUtils.getFileMkDirPath(APP_DIR + IMAGE_CACHE_DIR);
        return "";
    }

    /**
     * http缓存的位置
     */
    public String getHttpCacheDir() {
        //return FileUtils.getFileMkDirPath(CACHE_DIR + HTTP_CACHE_PATH);
        return "";
    }

    /**
     * 在Application中使用该方法，并初始化CACHE_DIR这个值
     * @param context
     */
    public void initAppDirPath(Context context) {
       // CACHE_DIR = context.getCacheDir() + APP_FOLDER;
    }

    /**
     * 获取内部存储的地址, 不同手机会使用不同名称的目录, 此处是先获得挂载点然后进行判断筛选
     * */
    private static String getInternalPath(){
        //所有挂载的地址
        ArrayList<String> mountList = getDevMountList();
        String internalPath = "";
        for(String mountPth : mountList){
            File dictory = new File(mountPth);
            if(dictory.isDirectory()&& dictory.canWrite() && dictory.canRead() && dictory.canExecute()){
                internalPath = dictory.getAbsolutePath();
                break;
            }
        }

        return internalPath;
    }

    /**
     * 遍历/etc/vold.fstab 获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {
        byte[] contentByte = FileUtils.readFile("/etc/vold.fstab");
        String[] toSearch = new String(contentByte).split(" ");
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

}
