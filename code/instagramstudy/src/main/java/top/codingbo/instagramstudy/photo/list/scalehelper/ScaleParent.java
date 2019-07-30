package top.codingbo.instagramstudy.photo.list.scalehelper;

/**
 * Created by bob
 * on 2019/6/24.
 */
public interface ScaleParent {

    /**
     * 移动到指定位置
     *
     * @param x x
     * @param y y
     */
    void moveTo(int x, int y);

    void moveBy(int dx, int dy);

    /**
     * 缩放到制定大小
     *
     * @param width  w
     * @param height h
     */
    void scaleTo(int width, int height);

    /**
     * 以给定的点为原点 缩放
     *
     * @param x      x
     * @param y      y
     * @param width  w
     * @param height h
     */
    void scaleByPoint(int x, int y, int width, int height);
}
