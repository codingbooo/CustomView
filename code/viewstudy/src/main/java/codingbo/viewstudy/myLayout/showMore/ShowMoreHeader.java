package codingbo.viewstudy.myLayout.showMore;

import android.view.View;

/**
 * Created by bob
 * on 2018/9/6.
 *
 * @author bob
 */
public interface ShowMoreHeader {
    /**
     *  todo 这个接口应该怎么定义
     *
     * 回调当前状态
     *
     * @param status 刷新状态
     * @param y      垂直位置
     * @param max    垂直最大位置
     *               <p>
     *               <p>
     * @see ShowMoreLayout
     */
    void onStatus(int status, int y, int max);

    /**
     * 返回一个View实例
     */
    View getView();

}
