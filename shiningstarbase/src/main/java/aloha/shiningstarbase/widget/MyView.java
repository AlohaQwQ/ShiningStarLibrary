package aloha.shiningstarbase.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View{

	//设置wrap_content时候View的大小
	private int defaultWidth = 100;
	private int defaultHeight = 100;
	//画笔 用于画圆
	private Paint p = new Paint();

	//View当前的位置
	private int rawX = 0;
	private int rawY = 0;
	//View之前的位置
	private int lastX = 0;
	private int lastY = 0;

	public MyView(Context context){
		super(context);
	}
	public MyView(Context context, AttributeSet set) {
		super(context, set);
	}

	//画一个红色，圆心为View中心点，半径为View宽度的圆
	public void onDraw(Canvas canvas){
		//Log.e("onDraw执行","true");
		p.setColor(Color.RED);
		int x = this.getLeft() + this.getWidth()/2;
		int y = this.getTop() + this.getHeight()/2;
		canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, p);
	}


	/**
	 * 使用layout方法实现滑动
	 * 注意onTouchEvent的相应区域为View的范围
	 */
	public boolean onTouchEvent(MotionEvent event){
		//Log.e("onTouchEvent执行","true");
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				//Log.e("ACTION","down");
				//获取手指落下的坐标并保存
				rawX = (int)(event.getRawX());
				rawY = (int)(event.getRawY());
				lastX = rawX;
				lastY = rawY;
				break;
			case MotionEvent.ACTION_MOVE:
				//Log.e("ACTION","move");
				//手指拖动时，获得当前位置
				rawX = (int)event.getRawX();
				rawY = (int)event.getRawY();
				//手指移动的x轴和y轴偏移量分别为当前坐标-上次坐标
				int offsetX = rawX - lastX;
				int offsetY = rawY - lastY;
				//通过View.layout来设置左上右下坐标位置
				//获得当前的left等坐标并加上相应偏移量
				layout(getLeft() + offsetX,
						getTop() + offsetY,
						getRight() + offsetX,
						getBottom() + offsetY);
				//移动过后，更新lastX与lastY
				lastX = rawX;
				lastY = rawY;
				break;
		}
		return true;
	}

	//简单的重写onMeasure
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
		if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
			setMeasuredDimension(defaultWidth,defaultHeight);
		}else if(widthSpecMode == MeasureSpec.AT_MOST){
			setMeasuredDimension(defaultWidth,heightSpecSize);
		}else if(heightSpecMode == MeasureSpec.AT_MOST){
			setMeasuredDimension(widthSpecSize, defaultHeight);
		}else{
			setMeasuredDimension(widthSpecSize, heightSpecSize);
		}
	}
}
