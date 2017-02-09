package aloha.shiningstarbase.base;

/**
 * Created by Aloha <br>
 * -explain 父类presenter 分发器
 * @Date 2016/9/26 15:05
 * @version 1.0.0
 */

public interface IBasePresenter<V extends IBaseView> {

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 15:21
     * @explain 绑定view
     */
    void lockContentView(V view);

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 15:25
     * @explain set Model数据源
     */
    //void setModel();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 15:23
     * @explain 解绑view
     */
    void unlockContentView();

    /**
     * Created by Aloha <br>
     * @Date 2016/9/26 15:24
     * @explain get 绑定的view
     */
    V getContentView();

}
