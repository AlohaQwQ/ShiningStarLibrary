package aloha.shiningstarbase.util.lol;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

import com.aloha.starworld.base.MyApplication;
import com.aloha.starworld.net.AsyncLoadNetworkPic;

import java.io.File;

/**
 * 网络图片 Test fromHtml ImageGetter类，获取网络图片异步类
 * fragment_title3.setText(Html.fromHtml(title3, mImageGetter, null));
 * @author Susie
 */
public class NetworkImageGetter implements Html.ImageGetter{

	/**网络图片name*/
    private String pictrueName = null;

    /**处理完成后需要向外部返回处理好的图片*/
    private File file = null;
    private Drawable drawable;

    /**
     * @param pictrueName 文件保存名称
     */
	public NetworkImageGetter(String pictrueName){
		this.pictrueName = pictrueName;
	}

    @Override
    public Drawable getDrawable(String source) {
        // 封装路径 。picName 保存图片名称
    	File mFile = new File(MyApplication.DOWNLOAD_PATH + "exam_picture/");
        if (!mFile.exists()) {
        	mFile.mkdirs();
        }
    	file = new File(MyApplication.DOWNLOAD_PATH + "exam_picture/", pictrueName);
		//file.createNewFile();
        Log.i("QAQ","下载图片-pictrueName-"+pictrueName);
        // 判断是否以http开头
        if(source.startsWith("http")) {
            // 判断路径是否存在
            if(!file.exists()) {
            	/*不存在即开启异步任务加载网络图片*/
            	AsyncLoadNetworkPic networkPic = new AsyncLoadNetworkPic(pictrueName);
                networkPic.execute(source);
            	/*String qq = file.getAbsolutePath();
            	//createFromPath()根据路径创建一个drawable   getAbsolutePath()：返回抽象路径名的绝对路径名字符串。
                drawable = Drawable.createFromPath(file.getAbsolutePath());
                //drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                drawable.setBounds(0, 0, 500, 300);*/
                /*// 不存在即开启异步任务加载网络图片
            	Handler handler = new Handler(){
        			public void handleMessage(Message msg) {
        				//接收处理好的图片
        				file = (File)msg.obj;
        			};
        		};
                AsyncLoadNetworkPic networkPic = new AsyncLoadNetworkPic(pictrueName,handler);
                networkPic.execute(source);*/
            } else {
            	/*存在根据文件名去获取File图片文件*/
            	Bitmap bitmap = BitmapFactory.decodeFile(MyApplication.DOWNLOAD_PATH + "exam_picture/"+pictrueName);
            	if(bitmap == null){
            		/*异步下载可能还没完成。此时bitmap 为 null，因此重新执行一遍，确保bitmap不为null。*/
            		getDrawable(source);
            	} else {
            		Log.i("QAQ","下载图片-bitmap-"+bitmap);
                	int height = bitmap.getHeight();
                	int width = bitmap.getWidth();
                	Log.i("QAQ", "bitmap-height-"+height);
                	Log.i("QAQ", "bitmap-width-"+width);
                	if (width > MyApplication.WINDOW_WIDTH) {
        				width = MyApplication.WINDOW_WIDTH -50;
        			}
                	drawable = new BitmapDrawable(bitmap);
                	drawable.setBounds(0, 0, width, height); // 5:3
    			}
			}
        }
        Log.i("QAQ","下载图片-drawable-"+drawable);
        return drawable;
    }
}