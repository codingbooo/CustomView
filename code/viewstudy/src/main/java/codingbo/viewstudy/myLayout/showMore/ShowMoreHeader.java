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
     * 回调 当前位置
     */
    void onPosition(int y, int max);

    /**
     * 返回一个View实例
     */
    View getView();
}
