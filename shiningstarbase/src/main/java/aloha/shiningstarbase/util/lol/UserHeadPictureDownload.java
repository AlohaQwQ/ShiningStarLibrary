package aloha.shiningstarbase.util.lol;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.aloha.starworld.base.MyApplication;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
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
public class UserHeadPictureDownload {

	private String mfileName = null;  //存储文件名
	private Bitmap bitmap = null;
	private String mimageUrl = null;	 //读取文件Url地址

	/**
	 * @param imageUrl  读取文件Url地址
	 * @param fileName	存储文件名(必须加上图片后缀名)
	 */
	public UserHeadPictureDownload(final String imageUrl,final String fileName) {
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
					File dirFile = new File(MyApplication.DOWNLOAD_PATH);
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
     * Get image from newwork
     * @param path The path of image
     * @return byte[]
     * @throws Exception
     */
    public byte[] getImage(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get image from newwork
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public InputStream getImageStream(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }
    /**
     * Get data from stream
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
}
