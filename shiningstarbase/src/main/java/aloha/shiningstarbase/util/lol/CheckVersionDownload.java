package aloha.shiningstarbase.util.lol;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.aloha.starworld.base.MyApplication;
import com.aloha.starworld.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author  Aloha
 * @version 2015-11-11 下午5:24:50
 * @explain 按钮检测版本更新 	http://www.xiedajia.com/kaodeguo/android_version_getVersion.action
 */
public class CheckVersionDownload {

	private static Context mContext;

    private Handler handler = null;	/*处理网络请求*/
    private String url = MyApplication.SERVER_URL+"android_version_getVersion.action" ; 	  /*验证版本号地址*/
    private String path = MyApplication.SERVER_URL+"download/kaodeguo.apk"; 	              /*请求新版APK下载地址*/
	private String response = null;	 /*处理网络请求之后返回值    */

    public CheckVersionDownload(Context context) {
		mContext = context;
		checkVersionName();
	}

    /**
     * 检测当前程序的版本号
     */
    private void checkVersionName(){
		handler = new Handler(){
			public void handleMessage(Message msg) {
				response = (String) msg.obj;
				Log.i("QAQ","CheckVersionService...");
				/*解析JSON*/
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        MyApplication.SERVER_VERSION = jsonObject.getInt("version");
                        Log.i("QAQ", "服务器版本号："+MyApplication.SERVER_VERSION);
                        Log.i("QAQ", "本地版本号："+MyApplication.LOCAL_VERSION);
                        if (MyApplication.LOCAL_VERSION < MyApplication.SERVER_VERSION) {
                            onStartDownload();
                        } else {
                            Toast.makeText(mContext, "当前已是最新版本，无需更新。",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        System.out.println("JSONException 导致解析请求失败");
                        Toast.makeText(mContext, "网络请求失败,请重新连接。",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("解析请求失败");
                    Toast.makeText(mContext, "网络请求失败,请重新连接。",Toast.LENGTH_LONG).show();
                }
            };
		};
		NetConnection.sendRequest(url, new NetConnection.OnNetConnectionCallBack() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    /**
     * @explain 提示用户更新版本
     */
    public void onStartDownload() {
		AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(mContext);
		alertDialogBuilder.setTitle("版本更新");
		alertDialogBuilder.setMessage("检测到新版本，是否要更新？");
		alertDialogBuilder.setPositiveButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
            	System.out.println("Dialog消失");
            }
        });
		alertDialogBuilder.setNegativeButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                new DownloadApkTask(mContext).execute(path);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        //ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG); //系统中关机对话框就是这个属性
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.setCanceledOnTouchOutside(false);                                   //默认为true，可以被Back键取消
        alertDialog.show();
    	/*manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
    	new DownloadApkTask().execute(path);*/
    }
}
