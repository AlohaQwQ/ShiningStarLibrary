package aloha.shiningstarbase.util.lol;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.aloha.starworld.R;
import com.aloha.starworld.base.MyApplication;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by roly on 16/6/27.
 * 定义一个异步任务用于下载新版APK
 */
public class DownloadApkTask extends AsyncTask<String, Integer, File> {
    /*文件存放位置  */
    private String filePath = MyApplication.DOWNLOAD_PATH;

    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    public static Context mContext;

    public DownloadApkTask(Context mContext) {
        this.mContext = mContext;
    }

    /*下载前  */
    @Override
    protected void onPreExecute() {
        Log.i("QAQ", "onPreExecute");
        /*创建一个前台通知栏*/
        manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher));
        builder.setContentTitle("考得过更新");

        //Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        Intent notificationIntent = new Intent();
        notificationIntent.setDataAndType( Uri.fromFile(new File(filePath)),  "application/vnd.android.package-archive");
        PendingIntent pi = PendingIntent.getActivity(mContext,
                0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pi);
        builder.setProgress(100, 0, false);
        builder.setTicker("文件开始下载...");
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(1, notification);
    }

    @Override
    protected File doInBackground(String... params) {
        String path = params[0];
		/*创建项目文件夹*/
        File dirFile = new File(MyApplication.DOWNLOAD_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        try {
            Log.i("QAQ", "doInBackground");
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

                /*获取下载文件的size   */
            long totalSize = conn.getContentLength();
            Log.i("QAQ", "totalSize.."+totalSize);
            String file = filePath+"kaodeguo.apk";
            Log.i("QAQ", "filePath..."+filePath);
            InputStream is = conn.getInputStream();
            BufferedInputStream inputStream  = new BufferedInputStream(is);
            //OutputStream outputStream  = new FileOutputStream(file,false); // 文件存在则覆盖掉
            FileOutputStream outputStream = mContext.openFileOutput("kaodeguo.apk", Context.MODE_WORLD_READABLE);

               /* byte[] buf = new byte[1024];
                int len = 0;
                int current_len = 0;
                int progress = 0;当前下载进度
                int temp = 0;

                while ((len = bis.read(buf)) != -1) {
                    current_len += len;
                    fos.write(buf, 0, len);
                    if ((current_len - temp) > 204800) { //间断性的更新进度条，否则会因更新过于频繁而导致进度条不刷新
                        temp = current_len;
                        progress = (int) ((current_len / (float) total_length) * 100);
                        this.publishProgress(progress);  //调用publishProgress()方法跳转到onProgressUpdate()实时更新UI
                    }
                }*/
            byte buffer[] = new byte[1024];
            int downloadCount = 0;// 已经下载好的大小
            int updateCount = 0; // 已经上传的文件大小。当前下载进度
            int down_step = 5;// 提示step
            int readsize = 0;//下载文件字节大小
            while ((readsize = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readsize);
                downloadCount += readsize;// 时时获取下载到的大小

                    /*每次增张5%。间断性的更新进度条，否则会因更新过于频繁而导致进度条不刷新  */
                if (updateCount == 0
                        || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
                    updateCount += down_step;
                    Log.i("QAQ", "downloadCount"+downloadCount);
                    Log.i("QAQ", "down_step"+down_step);
                    Log.i("QAQ", "updateCount"+updateCount);
                    Log.i("QAQ", "readsize"+readsize);
                    this.publishProgress(updateCount);  /*调用publishProgress()方法跳转到onProgressUpdate()实时更新UI*/
                }
            }
            outputStream.close();
            inputStream.close();
            is.close();
            //Resources res = getResources();
            //Bitmap bmp = BitmapFactory.decodeResource(res,R.drawable.ic_launcher);//这里只是一个标志，实际意义为下载完成  中间无故障
            File file2 = new File("/data/data/com.mingzhi.testsystemapp/files/kaodeguo.apk");

            String path2 = "/data/data/com.mingzhi.testsystemapp/files/kaodeguo.apk";
            String permission = "666";

            try {
                String command = "chmod " + permission + " " + path2;
                Runtime runtime = Runtime.getRuntime();
                runtime.exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("QAQ", "file2-------"+file2);
            return file2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
        /*更新进度条  */
    protected void onProgressUpdate(Integer... values) {
        Log.i("QAQ", "onProgressUpdate..." + values[0]);
        builder.setContentText("下载进度：" + values[0] + "%");
        builder.setProgress(100, values[0], false);
        Notification no = builder.build();
        no.flags = Notification.FLAG_NO_CLEAR;
        manager.notify(1, no);
        no = null;
    }

    @Override
        /*下载完成后  */
    protected void onPostExecute(File result) {
        Log.i("QAQ", "onPostExecute...");
        if (result != null) {
            builder.setProgress(0, 0, true);
            builder.setContentText("下载完成，点击安装。");
            Notification no = builder.build();
            no.flags = Notification.FLAG_AUTO_CANCEL;
            no.defaults = Notification.DEFAULT_SOUND;
            manager.notify(1, no);
            // installApp();
        } else {
            builder.setProgress(0, 0, true);
            builder.setContentText("下载失败..");
            manager.notify(1, builder.build());
        }
    }

    private void installApp() {
        File file = new File("/data/data/com.mingzhi.testsystemapp/files/kaodeguo.apk");
        	/*String filePath = MyConfig.DOWNLOAD_PATH;
        	File file = new File(filePath, "kaodeguo.apk");    	*/

        if(!file.exists()) {
            return;
        }
        // 跳转到新版本应用安装页面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}