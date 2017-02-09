package aloha.shiningstarbase.util.lol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;

import com.aloha.starworld.base.MyApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author  Aloha
 * @version 2015-11-12 下午3:25:22
 * @explain 用户头像下载类
 */
public class PictureDownload {

	private Context mContext;
	private String mfileName = null;  //存储文件名
	private Bitmap bitmap = null;
	private String mimageUrl = null;	 //读取文件Url地址

	/**
	 * @param imageUrl  读取文件Url地址
	 * @param fileName	存储文件名(必须加上图片后缀名)
	 */
	public PictureDownload(final Context context,final String imageUrl,final String fileName) {
		mContext = context;
		mimageUrl = imageUrl;
		mfileName = fileName;
		new AsyncTask<Void, Void, Boolean>() {
			//任务开始执行之前调用，用于进行一些界面上的初始化操作
			protected void onPreExecute() {
				//pd.setMessage("Dead!!!");
				//pd.show();
			};

			/**
			 * 处理耗时任务。完成任务之后通过return语句返回执行结果。
			 * @return
			 */
			@Override
			protected Boolean doInBackground(Void... params) {
				//mimageUrl = "http://p2.gexing.com/G1/M00/45/BC/rBACE1LClweg0zsmAAAfRAWtVoA600_200x200_3.jpg?recache=20131108";
				InputStream is = null;
				URL url = null;
				HttpURLConnection connection = null;
				try {
					Log.i("QAQ","打开图片下载连接成功");
					url = new URL(mimageUrl);
					connection = (HttpURLConnection) url.openConnection();
					connection.setConnectTimeout(4000);
					connection.setReadTimeout(4000);
					connection.setDoInput(true);
					connection.connect();

					//将得到的数据转化成InputStream
					is = connection.getInputStream();
					//将InputStream转换成Bitmap
					bitmap = BitmapFactory.decodeStream(is);
					is.close();

					/*创建项目文件夹*/
					File dirFile = new File(MyApplication.DOWNLOAD_PATH+"head_peoplenearby/");
			        if(!dirFile.exists()){
			            dirFile.mkdir();
			        }
			        File myPictureFile = new File(dirFile, mfileName);
			        //if(!myPictureFile.exists()){
		        	BufferedOutputStream fileout = new BufferedOutputStream(new FileOutputStream(myPictureFile));
			        /*compress() Bitmap压缩方法*/
			        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileout);
			        fileout.flush();
			        fileout.close();
					Log.i("QAQ","图片下载完毕");
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					Log.i("QAQ","[getNetWorkBitmap->] URL协议、格式或者路径错误 -MalformedURLException");
					e1.printStackTrace();
				}catch (IOException e) {
					// TODO Auto-generated catch block
					Log.i("QAQ","[getNetWorkBitmap->] IOException IO异常-- 或  FileNotFoundException 异常");
					e.printStackTrace();
				}
				return true;
			}

			/**
			 * 发送数据到外部链接
			 * onPostExecute()是在doInBackground()方法执行完毕之后,会将doInBackground()的返回值(服务器端返回数据)作为该方法的传入参数传入。
			 */
			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
			}

		}.execute();
	}

	 /**
     * @explain 获取下载图片
     */
    public Drawable getImage(){
    	String path = MyApplication.DOWNLOAD_PATH+"head_peoplenearby/"+mfileName;
    	Drawable drawable = null;
    	File mFile = new File(path);
        if (mFile.exists()) {
        	/*根据文件名去获取File图片文件*/
        	Bitmap bitmap = BitmapFactory.decodeFile(path);
            if (bitmap!=null) {
            	/*获取屏幕大小*/
        		WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
            	int width = wm.getDefaultDisplay().getWidth();
            	bitmap = ImageTools.createBitmapBySize(bitmap,width/5,width/5);
            	bitmap = ImageTools.toRoundCorner(bitmap,width/10);
            	drawable = new BitmapDrawable(bitmap);
			}
        }
        return drawable;
    }
}
