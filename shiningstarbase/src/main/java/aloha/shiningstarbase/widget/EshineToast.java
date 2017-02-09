package aloha.shiningstarbase.widget;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


/**
 * 自定义的Toast组件
 * Created by chenmingzhen on 16-6-7.
 */
public class EshineToast {
    private static final int DEFAULT_DRAWABLE = -1;
    private static Toast sToast = null;
    private static boolean sShowing = false;

    /**
     * 展示指定资源字符串的Toast
     * @param stringResId string.xml中的字符串id
     * @param formatArgs 格式化内容
     */
    public static void showToast(int stringResId, Object... formatArgs) {
        /*//String text = ResourceUtils.getString(stringResId, formatArgs);
        showToast(text);*/
    }

    public static void showToast(String text) {
        /*View view = View.inflate(AppConstants.getApplication(), R.layout.view_toast, null);

        //过滤掉相同的toast
        if (null != sToast && sShowing) {
            String toastMsg = (String) sToast.getView().getTag();
            if (toastMsg.equals(text)) {
                return;
            }
        }

        view.setTag(text);
        setToastView(view, text, DEFAULT_DRAWABLE);
        showToastCenter(view, Toast.LENGTH_SHORT);*/
    }


    public static void showLongToast(String text) {
/*        View view = View.inflate(AppConstants.getApplication(), R.layout.view_toast,
                null);
        setToastView(view, text, DEFAULT_DRAWABLE);
        showToast(view, Toast.LENGTH_LONG);*/
    }

    private static void showToast(View view, int duration) {
        showToastCenter(view, duration);
    }

    /**
     * 使用自定义样式的toast样式
     * @param view
     * @param text
     * @param drawableResId toast内部的图标，默认隐藏
     */
    private static void setToastView(View view, String text, int drawableResId) {
       /* TextView toastDesc = (TextView) view.findViewById(R.id.toast_desc);
        toastDesc.setText(text);
        ImageView toastImage = (ImageView) view.findViewById(R.id.toast_image);
        if (drawableResId != DEFAULT_DRAWABLE) {
            toastImage.setVisibility(View.VISIBLE);
            toastImage.setBackgroundResource(drawableResId);
        }
        TextPaint paint = toastDesc.getPaint();
        float textLength = paint.measureText(text);
        float defaultToastTextLength = AppConstants.getApplication().getResources()
                .getDimension(R.dimen.dp_240);
        if (textLength > defaultToastTextLength) {
            ViewGroup.LayoutParams layoutParams = toastDesc.getLayoutParams();
            layoutParams.width = (int) defaultToastTextLength;
            toastDesc.setLayoutParams(layoutParams);
        }*/
    }

    /**
     * {@link EshineToast#showToast(int)}
     * @param view
     * @param duration
     */
    private static void showToastCenter(View view, int duration) {
        if (sToast != null) {
            sToast.cancel();
        }
       // sToast = new Toast(AppConstants.getApplication());
        sToast.setGravity(Gravity.BOTTOM, 0, 200);
        sToast.setView(view);
        showToast(duration);
    }

    /**
     * 如果duration <= 0 则默认选择的时间是 Toast.LENGTH_SHORT = 0 ，运行的时间为2s，
     * 如果duration = 1   则默认选择的时间是 Toast.LENGTH_LONG = 1  ，运行的时间为3.5s，
     * 其他运行的时间为 duration
     */
    private static void showToast(int duration) {
        int sleepTime = 0;

        if(Toast.LENGTH_SHORT == duration){
            sleepTime = 2000;
        }else if(Toast.LENGTH_LONG == duration){
            sleepTime = 3500;
        }else{
            if(0 > duration){
                duration = 2000;
            }

            sleepTime = duration;
        }

        if (0 < sleepTime) {
            sToast.show();
            sShowing = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (sToast != null){
                        sToast.getView().setTag("");
                        sToast.cancel();
                        sShowing = false;
                    }

                }
            }, sleepTime);
        }
    }

}
