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
     * 拖拽进度
     *
     * @param y    垂直位置
     * @param maxY 垂直最大位置
     */
    void onDragging(int status, int y, int maxY);


    /**
     * <h2>状态改变</h2>
     * <p>
     * 状态类型:
     * <p>
     * {@link ShowMoreLayout#STATUS_NORMAL}正常状态
     * <p>
     * {@link ShowMoreLayout#STATUS_DRAGGING}下拉状态
     * <p>
     * {@link ShowMoreLayout#STATUS_REFRESHING}刷新状态
     * <p>
     * {@link ShowMoreLayout#STATUS_FINISH}完成回收状态
     *
     * @param status    当前状态
     * @param oldStatus 上个状态
     */
    void onStatusChanged(int status, int oldStatus);

    /**
     * 返回一个View实例
     */
    View getView();

}
