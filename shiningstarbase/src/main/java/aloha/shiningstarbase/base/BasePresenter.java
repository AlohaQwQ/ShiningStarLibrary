package aloha.shiningstarbase.base;

import java.lang.ref.WeakReference;
import java.util.Map;
import aloha.shiningstarbase.logger.LogUtil;

/**
 * Created by Aloha <br>
 * -explain 父类Presenter
 * @Date 2016/9/26 17:10
 * @version 1.0.0
 */

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter {

    //传递view
    private WeakReference<V> mView;

    /**
     * Created by Aloha <br>
     * @Date 2016/9/29 14:15
     * @explain 绑定view
     */
    @Override
    public void lockContentView(IBaseView view) {
        this.mView = new WeakReference<V>((V) view);
        LogUtil.biu(getClass().toString());
    }

    /**
     * Created by Aloha <br>
     * @Date 2016/9/29 14:16
     * @explain 解绑view
     */
    @Override
    public void unlockContentView() {
        if (mView!=null){
            mView.clear();
            mView = null;
        }
    }

    /**
     * Created by Aloha <br>
     * -explain 获取 presenter绑定的view
     * @Date 2016/10/10 10:42
     */
    @Override
    public V getContentView() {
        if (mView!=null){
            return mView.get();
        }
        return null;
    }

    protected void addRequestAsyncTask() {

    }

    /**
     * Created by Aloha <br>
     * @Date 2016/9/29 15:28
     * @explain 数据回调，由子类实现并自行处理response
     * @param status
     * @param status
     * @param status
     * @date
     **/
    protected abstract void onResponseAsyncDeal(final int status, final String message, final String result, final String requestID);

    protected void onResponseAsyncDeal(int status, String message, Map<String, Object> resultMap, String requestID){}
}
