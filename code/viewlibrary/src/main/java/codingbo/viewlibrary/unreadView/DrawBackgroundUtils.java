package codingbo.viewlibrary.unreadView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 绘制背景工具类
 * Created by bob
 * on 17.9.29.
 */

public class DrawBackgroundUtils {


    /**
     * 绘制 两边为圆形中间为矩形的背景
     *
     * @param canvas 画布
     * @param paint  笔
     * @param width  view宽度
     * @param height view高度
     */
    public static void drawCircleBg(Canvas canvas, Paint paint, int width, int height) {
        drawCircleBg(canvas, paint, width, height, 0, 0);
    }

    /**
     * 绘制 两边为圆形中间为矩形的背景
     *
     * @param canvas  画布
     * @param paint   笔
     * @param width   view宽度
     * @param height  view高度
     * @param offsetX X轴中心偏移量
     * @param offsetY Y轴中心偏移量
     */
    public static void drawCircleBg(Canvas canvas, Paint paint, int width, int height, float offsetX, float offsetY) {
        if (canvas == null || paint == null || width <= 0 || height <= 0) {
            throw new RuntimeException("parameter error");
        }
        int radius;
        RectF leftCircle;
        RectF rightCircle;
        RectF topCircle;
        RectF bottomCircle;
        RectF centerRect;

        float dx = offsetX;
        float dy = offsetY;

        leftCircle = new RectF();
        rightCircle = new RectF();
        topCircle = new RectF();
        bottomCircle = new RectF();
        centerRect = new RectF();

        int circleRectLength;
        if (width >= height) {
            circleRectLength = height;
            radius = height / 2;
            leftCircle.set(0, 0, circleRectLength, circleRectLength);
            rightCircle.set(width - circleRectLength, 0, width, circleRectLength);
            centerRect.set(radius, 0, width - radius, circleRectLength);
        } else {
            circleRectLength = width;
            radius = width / 2;
            topCircle.set(0, 0, circleRectLength, circleRectLength);
            bottomCircle.set(0, height - circleRectLength, circleRectLength, height);
            centerRect.set(0, radius, circleRectLength, height - radius);
        }


        leftCircle.offset(dx, dy);
        rightCircle.offset(dx, dy);
        topCircle.offset(dx, dy);
        bottomCircle.offset(dx, dy);
        centerRect.offset(dx, dy);

        paint.setStyle(Paint.Style.FILL);
        //绘制左半圆
        canvas.drawArc(leftCircle, 90, 270, true, paint);
        //绘制右半圆
        canvas.drawArc(rightCircle, 270, 450, true, paint);
        //绘制上半圆
        canvas.drawArc(topCircle, 180, 360, true, paint);
        //绘制下半圆
        canvas.drawArc(bottomCircle, 0, 180, true, paint);
        //绘制中心
        canvas.drawRect(centerRect, paint);
    }
}
