package aloha.shiningstarbase.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Stack;

import aloha.shiningstarbase.database.MyDatabaseHelper;
import aloha.shiningstarbase.logger.LogUtil;

/**
 * @author  Aloha
 * @version 2015-11-10 下午6:44:57
 * @explain	自定义Application  必须定义在项目包下
 *		  	版本更新——
 *			获取manifest配置文件中的versionCode和versionName，我们一般用versionCode来实现版本更新。
 *			实现原理很简单：服务器端有个serverVersion，我们本地有个localVersion.服务器端serverVersion>localVersion,这个时候我们就需要进行升级版本
 */
public class MyApplication extends Application {

    /*单例Application*/
    private volatile static MyApplication myApplication;
    //public static final String SERVER_URL = "http://aloha-qoq.imwork.net:8088"; //通信接口地址
    //public static final String SERVER_URL = "http://192.168.43.31:8088/TestServer/api.jsp";	//安卓模拟器下访问本机服务器,需使用IP访问。老子的IP每次都不一样啊艹............
    public static String SERVER_URL = "http://www.xiedajia.com/kaodeguo/";	//http://www.xiedajia.com/kaodeguo/	http://192.168.1.137:8080/kaodeguo/
    public static String USER_ID;
    public static String EMAIL;			/*"/data"+ Environment.getDataDirectory().getAbsolutePath() + "/" + "com.mingzhi.testsystemapp" +"/user_download/"*/
    public final static String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() +"/kaodeguo/" ;  //项目文件夹

    public static String SHARED_MY = "SHARED_MY"; //SharedPreferences的文件名称
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static int LOCAL_VERSION; 	// 本地安装版本
    public static int SERVER_VERSION; // 服务器版本

    /*获取屏幕大小*/
    public static int WINDOW_WIDTH ;
    public static int WINDOW_HEIGHT;
    /*用户手机IP*/
    public static String PHONE_IP;
    /*本地缓存数据库*/
    private SQLiteDatabase db;
    private MyDatabaseHelper databaseHelper;
    /*Activity 管理栈*/
    private Stack<Activity> activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("QAQ", "MyApplication启动。");
        /*获取屏幕大小*/
        WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        WINDOW_WIDTH = wm.getDefaultDisplay().getWidth();
        WINDOW_HEIGHT = wm.getDefaultDisplay().getHeight();
        initMyConfig();
		/*创建项目文件夹*/
        File dirFile = new File(DOWNLOAD_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        /*创建本地缓存数据库*/
        databaseHelper = new MyDatabaseHelper(getApplicationContext(), "my.db", null, 1);
        databaseHelper.getWritableDatabase();
    }

    /**
     * Created by Aloha <br>
     * -explain 单例Application
     * @Date 2016/10/10 9:56
     */
    public static MyApplication getApplicationInstance(){
        if (myApplication == null) {
             synchronized (MyApplication.class) {
                 if (myApplication == null)
                     myApplication = new MyApplication();
             }
        }
        return myApplication;
    }


    /**
     * 配置初始化
     */
    private void initMyConfig(){
        /**
         * 获取当前程序的版本号
         */
        /*获取packagemanager的实例 */
        PackageManager packageManager = getApplicationContext().getPackageManager();
        PackageInfo packInfo;
        try {
			/*getPackageName()是你当前类的包名，0代表是获取版本信息    */
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            LOCAL_VERSION = packInfo.versionCode;
            Log.i("QAQ", "本地版本号："+LOCAL_VERSION);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        createSharedPreferences();
        getLocalIpAddress();
    }

    /**
     * 生成用户配置SharedPreferences文件
     */
    private void createSharedPreferences(){
        sharedPreferences = getSharedPreferences(SHARED_MY, MODE_PRIVATE);
    	/*用于保存用户调整字体大小，用户第一次进来时加载该配置文件。*/
        editor = sharedPreferences.edit();
        editor.putString("test", "0");
        editor.commit();
    }

    /**
     * @explain 保存用户配置SharedPreferences文件
     * @param value
     */
    public void setSharedPreferencesTextView(String value){
        editor.putString("test", value);
        editor.commit();
        Log.i("QAQ", "Shared--TextSize-"+value);
    }

    /**
     * 取出用户配置SharedPreferences文件数据
     */
    public String getSharedPreferencesTextView(){
        return sharedPreferences.getString("test", "0");
    }

    /**
     * @explain 获取手机GPRS IP
     */
    public void getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        PHONE_IP =  inetAddress.getHostAddress().toString();
                        Log.i("QAQ", "PHONE_IP---"+PHONE_IP);
                    }
                }
            }
            getWifiIpAddress();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @explain 获取手机WIFI IP
     */
    public void getWifiIpAddress() {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = intToIp(ipAddress);
            PHONE_IP = ip;
            Log.i("QAQ", "----PHONE_WIFI_IP---"+PHONE_IP);
        }
    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    /**
     * Created by Aloha <br>
     * @Date 2016/9/30 14:10
     * @explain Activity push to Stack
     **/
    public void pushOneActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.push(actvity);
        LogUtil.biu("MyActivityManager size = " + activityStack.size());
    }

    /**
     * Created by Aloha <br>
     * -explain 获取栈顶的activity，先进后出原则
     * @Date 2016/10/10 9:41
     */
    public Activity getLastActivity() {
        return activityStack.lastElement();
    }

   /**
    * Created by Aloha <br>
    * -explain 移除一个activity
    * @Date 2016/10/10 9:41
    */
    public void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }

    /**
     * Created by Aloha <br>
     * -explain 退出所有activity
     * @Date 2016/10/10 9:41
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null)
                    break;
                popOneActivity(activity);
            }
        }
    }
}