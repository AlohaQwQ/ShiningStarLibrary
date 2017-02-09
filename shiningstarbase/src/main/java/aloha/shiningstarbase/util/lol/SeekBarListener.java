package aloha.shiningstarbase.util.lol;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

/**
 * @author  Aloha
 * @version 2015-11-8 下午7:17:56
 * @explain 滑动条SeekBar滑动监听事件。
 * 			在拖动条的任何地方按下鼠标时先调用onStartTrackingTouch一次，再调用onProgressChanged一次。
 * 			以后每拖动一下调用onProgressChanged一次。松开鼠标时调用onStopTrackingTouch一次
 */
public class SeekBarListener implements SeekBar.OnSeekBarChangeListener{

	private int qq = 0;

	final Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	});

	final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				//MyApplication.USER_CLICK_TEXTSIZE = qq;
				//Log.i("QAQ","handler:"+MyApplication.USER_CLICK_TEXTSIZE);
			}
		};
	};

	@Override
	/*当拖动条发生变化时调用该方法*/
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
		// TODO Auto-generated method stub
		Log.i("QAQ", seekBar.getProgress()+"onProgressChanged");
		qq = seekBar.getProgress();
		seekBar.setProgress(seekBar.getProgress());
	}

	@Override
	/*当用户开始滑动滑块时调用该方法（即按下鼠调用一次）*/
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		Log.i("QAQ", seekBar.getProgress()+"onStartTrackingTouch");
		//MyApplication.USER_CLICK_TEXTSIZE = seekBar.getProgress();
		qq = seekBar.getProgress();
		new Thread(thread).start();
		seekBar.setProgress(seekBar.getProgress());
	}

	@Override
	/*当用户结束对滑块滑动时,调用该方法（即松开鼠标）*/
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		Log.i("QAQ", seekBar.getProgress()+"onStopTrackingTouch");
		//MyApplication.USER_CLICK_TEXTSIZE = seekBar.getProgress();
		qq = seekBar.getProgress();
		seekBar.setProgress(seekBar.getProgress());
	}

}
