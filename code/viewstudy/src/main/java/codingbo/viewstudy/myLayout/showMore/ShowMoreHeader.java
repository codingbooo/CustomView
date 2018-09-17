package codingbo.viewstudy.myLayout.showMore;

import android.view.View;

/**
 * header接口
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
    void onDragging(ShowMoreState status, int y, int maxY);


    /**
     * <h2>状态改变</h2>
     * <p>
     * 状态类型:
     * <p>
     * {@link ShowMoreState#NORMAL}正常状态
     * <p>
     * {@link ShowMoreState#DRAGGING}下拉状态
     * <p>
     * {@link ShowMoreState#REFRESH}刷新状态
     * <p>
     * {@link ShowMoreState#COMPLETE}完成回收状态
     *
     * @param status    当前状态
     * @param oldStatus 上个状态
     */
    void onStatusChanged(ShowMoreState status, ShowMoreState oldStatus);

    /**
     * 返回一个View实例
     */
    View getView();

}
