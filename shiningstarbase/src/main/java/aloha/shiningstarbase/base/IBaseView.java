package aloha.shiningstarbase.base;

import android.content.Context;

/**
 * Created by Aloha
 * @Date 2016/9/26 14:50
 * @explain 父类视图接口
 * @version 1.0.0
 */

public interface IBaseView {

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 14:52
     * @explain 显示内容view
     */
    void showContentView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 14:55
     * @explain get 视图Context
     */
    Context getContextView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 14:50
     * @explain 弹出Toast.short
     */
    void showToastShort(String msg);

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 14:50
     * @explain 弹出Toast.long
     */
    void showToastLong(String msg);

    /**
     * Created by Aloha <br>
     * @Date 2016/9/28 11:04
     * @explain 显示Dialog View
     */
    void showDialogView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/28 11:04
     * @explain 隐藏Dialog View
     */
    void hideDialogView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 14:53
     * @explain 显示加载view
     */
    void showLoadingView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 14:53
     * @explain 隐藏加载view
     */
    void hideLoadingView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 14:56
     * @explain 显示错误view
     */
    void showErrorView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/28 11:03
     * @explain 隐藏错误view
     */
    void hideErrorView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 14:58
     * @explain 显示进度view(默认加载中)
     */
    void showProgressView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 14:58
     * @explain 显示进度view 带tips
     */
    void showProgressView(String msg);

    /**
     * Created by Aloha <br>
     * @Date 2016/9/28 11:03
     * @explain 隐藏进度view
     */
    void hideProgressView();
}
