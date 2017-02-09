package aloha.shiningstarbase.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.aloha.starworld.R;
import com.aloha.starworld.base.MyApplication;
import com.aloha.starworld.net.NetConnection;
import com.aloha.starworld.util.lol.UserHeadPictureDownload;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Aloha
 * @version 2015-11-27 下午6:50:50
 * @explain Main Banner轮播
 */
public class BannerViewPager extends FrameLayout implements Runnable {

	private String url = "https://img.alicdn.com/tps/TB1R.NaKFXXXXbyXVXXXXXXXXXX-610-60.jpg"; 	//网络请求地址
	private String response = null;	 //处理网络请求之后返回值
	private Context mContext;

	// 轮播图图片数量
	private final static int IMAGE_COUNT = 5;
	// 自动轮播的时间间隔
	private final static int TIME_INTERVAL = 5;
	// 自动轮播启用开关
	private final static boolean isAutoPlay = true;

	// 自定义轮播图的资源ID
	private int[] imagesResIds;
	private List<Drawable> imagesDrawables = new ArrayList<Drawable>();

	// 放轮播图片的ImageView 的list
	private List<ImageView> imageViewsList;
	// 放圆点的View的list
	private List<View> dotViewsList;

	private ViewPager viewPager;
	// 当前轮播页
	private int currentItem = 0;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;
	// Handler
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public BannerViewPager(Context context) {
		this(context, null);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	public BannerViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	public BannerViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		// TODO Auto-generated constructor stub
		initData();
		//initBanner();
		initUI(mContext);
		if (isAutoPlay) {
			startPlay();
		}

	}

	/**
	 * 开始轮播图切换
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * 停止轮播图切换
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * 从网络上获取广告图 信息
	 */
	private void initBanner() {
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();
		NetConnection.sendRequest(url, new NetConnection.OnNetConnectionCallBack() {
			@Override
			public void onSuccess(String response) {
				parseJson(response);
			}

			@Override
			public void onError(String error) {

			}
		});
	}

	private void parseJson(String response){
		System.out.println("BannerViewPagerParseJson....");
		if (response != null) {
			try {
				System.out.println("广告图下载...");
				JSONObject jsonObject = new JSONObject(response);
				String nickname = jsonObject.getString("nickname");
				String photo = jsonObject.getString("photo");
				String imageUrl = jsonObject.getString("photo");
				String fileName = jsonObject.getString("photo");
				Drawable drawable1 = this.getBannerDrawable(url,"1.jpg");
				imagesDrawables.add(drawable1);
				Drawable drawable2 = this.getBannerDrawable(url,"2.jpg");
				imagesDrawables.add(drawable2);
			} catch (JSONException e) {
				System.out.println("JSONException..");
				e.printStackTrace();
			}
		} else if(response == null){
			System.out.println("response == null..");
		}
		initUI(mContext);
	}

	/**
	 * 初始化相关Data
	 */
	private void initData() {
		imagesResIds = new int[] {
				R.mipmap.ic_launcher,
				R.mipmap.ic_launcher
		};
		//initBanner();
/*		Drawable drawable1 = this.getBannerDrawable(url,"1.jpg");
		imagesDrawables.add(drawable1);
		Drawable drawable2 = this.getBannerDrawable(url,"1.jpg");
		imagesDrawables.add(drawable2);*/
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();
	}

	/**
	 * 初始化Views等UI
	 */
	private void initUI(Context context) {
		LayoutInflater.from(context).inflate(R.layout.banner_main, this, true);

		/*	for (int i = 0; i < imagesDrawables.size(); i++) {
			ImageView view = new ImageView(context);
			view.setImageDrawable(imagesDrawables.get(i));
			view.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(view);
		}*/

		for (int imageID : imagesResIds) {
			ImageView view = new ImageView(context);
			view.setImageResource(imageID);
			view.setScaleType(ScaleType.FIT_XY);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String url = "http://www.xiedajia.com"; 	//网络请求地址
					Uri  uri = Uri.parse(url.toString());
					Intent  intent = new  Intent(Intent.ACTION_VIEW, uri);
					mContext.startActivity(intent);
				}
			});
			imageViewsList.add(view);
		}

		viewPager = (ViewPager) findViewById(R.id.banner_viewPager);
		viewPager.setFocusable(true);

		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	private Drawable getBannerDrawable(String imageUrl,String fileName){
		/*根据文件名去获取File图片文件*/
		Drawable drawable = null;
    	Bitmap bitmap = BitmapFactory.decodeFile(MyApplication.DOWNLOAD_PATH+"picture_advert/"+fileName);
        if (bitmap!=null) {
        	/*获取屏幕大小*/
    		/*WindowManager wm = (WindowManager)getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        	int width = wm.getDefaultDisplay().getWidth();
        	bitmap = ImageTools.createBitmapBySize(bitmap,width/5,width/5);*/
        	drawable = new BitmapDrawable(bitmap);
        	int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Log.i("QAQ", "drawable-width-"+width+"-drawable-height-"+height);
        } else {
			new UserHeadPictureDownload(imageUrl, fileName);
			getBannerDrawable(imageUrl,fileName);
		}
		return drawable;
	}

	/**
	 * 填充ViewPager的页面适配器
	 *
	 * @author caizhiming
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			// ((ViewPag.er)container).removeView((View)object);
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(imageViewsList.get(position));
			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 *
	 * @author caizhiming
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 1:// 手势滑动，空闲中
				isAutoPlay = false;
				break;
			case 2:// 界面切换中
				isAutoPlay = true;
				break;
			case 0:// 滑动结束，即切换完毕或者加载完毕
					// 当前为最后一张，此时从右向左滑，则切换到第一张
				if (viewPager.getCurrentItem() == viewPager.getAdapter()
						.getCount() - 1 && !isAutoPlay) {
					viewPager.setCurrentItem(0);
				}
				// 当前为第一张，此时从左向右滑，则切换到最后一张
				else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					viewPager
							.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int pos) {
			// TODO Auto-generated method stub
			/*currentItem = pos;
			for (int i = 0; i < dotViewsList.size(); i++) {
				if (i == pos) {
					((View) dotViewsList.get(pos))
							.setBackgroundResource(R.drawable.ic_btn_grid_choose2);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.ic_btn_grid_choose3);
				}
			}*/
		}

	}

	/**
	 * 执行轮播图切换任务
	 *
	 * @author caizhiming
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	/**
	 * 销毁ImageView资源，回收内存
	 *
	 * @author caizhiming
	 */
	private void destoryBitmaps() {

		for (int i = 0; i < IMAGE_COUNT; i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				// 解除drawable对view的引用
				drawable.setCallback(null);
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
