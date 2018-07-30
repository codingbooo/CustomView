package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bob
 * on 17.7.11.
 */

public class CustomView extends View {

    private Paint mPaint;
    private Rect mRect;
    private RectF mRectF;
    private Path mPath;
    private Path mPath1;

    public CustomView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int attr) {


        setPaint();

        mPath = new Path();
        mPath1 = new Path();

//        mPath.moveTo(500, 1100);
//        mPath.lineTo(430, 1000);
//        mPath.moveTo(500, 1100);
//        mPath.lineTo(570, 1000);
//
//        mPath.addArc(430, 1000, 500, 1100, 0, 90);
//        mPath.addArc(500, 1000, 570, 1100, 0, 90);


//        mPath.moveTo(600, 1000);
//        mPath.lineTo(300, 600);
//        mPath.moveTo(600, 1000);
//        mPath.lineTo(900, 600);

//        mPath.addArc(100, 900, 300, 1100, 180, 180);
//        mPath.addArc(300, 900, 500, 1100, 180, 180);

        mPath.addArc(200, 200, 400, 400, -225, 225);
        mPath.arcTo(400, 200, 600, 400, -180, 225, false);
        mPath.lineTo(400, 542);
//        mPath.lineTo(200, 342);
    }

    /**
     * Paint.setColor(int color) 设置颜色
     * Paint.setTextSize(float textSize) 设置文字大小
     * Paint.setStrokeWidth(float width) 设置线条宽度
     * Paint.setAntiAlias(boolean aa) 设置抗锯齿开关
     * Paint.setStyle(Style style) 设置绘制模式
     */
    private void setPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setColor(Color.parseColor("#99000000"));
//        mPaint.setColor(Color.argb(255,255,255,255));

        mPaint.setAntiAlias(true);

//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mRect = new Rect(100, 100, 300, 300);
        mRectF = new RectF(300, 300, 500, 500);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 255, 255, 255);
//        canvas.drawColor(Color.parseColor("#66778899"));
//        canvas.drawCircle(200, 200, 100, mPaint);

//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(100, 100, 300, 300, mPaint);
//        canvas.drawRect(mRect, mPaint);

//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(mRectF, mPaint);
//        canvas.drawRect(300, 300, 400, 400, mPaint);

//        mPaint.setColor(Color.RED);
//        mPaint.setStrokeWidth(20);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        canvas.drawPoint(50, 50, mPaint);
//
//        mPaint.setColor(Color.RED);
//        mPaint.setStrokeWidth(20);
//        mPaint.setStrokeCap(Paint.Cap.BUTT);
//        canvas.drawPoint(100, 100, mPaint);
//
//        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
//        canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
//                8 /* 一共绘制 8 个数（4 个点）*/, mPaint);
//        mPaint.setStrokeWidth(1);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawOval(300, 300, 500, 400, mPaint);
//
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawOval(300, 400, 500, 500, mPaint);
//
//        canvas.drawLine(400, 200, 400, 600, mPaint);
//
//        float[] lineList = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
//        canvas.drawLines(lineList, mPaint);
//
//
//        canvas.drawRoundRect(300, 50, 500, 150, 10, 0, mPaint);
//
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawArc(100, 600, 400, 800, 0, 80, true, mPaint);
//        canvas.drawArc(100, 600, 400, 800, 90, 80, false, mPaint);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawArc(100, 600, 400, 800, 180, 80, true, mPaint);
//        canvas.drawArc(100, 600, 400, 800, 270, 80, false, mPaint);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.RED);
//        mPath1.addCircle(300, 300, 200, Path.Direction.CW);
        mPaint.setStyle(Paint.Style.STROKE);
//        mPath1.lineTo(100, 100);
//        Path.rXXXXX 相对坐标 当前点为(0,0)
//        mPath1.rLineTo(100, 0);

//        mPath1.moveTo(0,0);
//        mPath1.quadTo(100,100,0,200);

//        mPath1.moveTo(0,100);
//        mPath1.quadTo(100,0,200,100);
//        mPath1.quadTo(300,0,400,100);
//        mPath1.quadTo(200,400,0,100);

//        mPath1.moveTo(200, 100);
//        mPath1.cubicTo(300, 0, 400, 100, 200, 400);
//        mPath1.cubicTo(0, 100, 100, 0, 200, 100);
//
//        mPaint.setColor(Color.parseColor("#55000000"));
//        mPath1.moveTo(100,0);
//        mPath1.lineTo(100,400);
//        mPath1.moveTo(200,0);
//        mPath1.lineTo(200,400);
//        mPath1.moveTo(300,0);
//        mPath1.lineTo(300,400);
//        mPath1.moveTo(400,0);
//        mPath1.lineTo(400,400);
//
//        mPath1.moveTo(0,100);
//        mPath1.lineTo(400,100);
//        mPath1.moveTo(0,200);
//        mPath1.lineTo(400,200);
//        mPath1.moveTo(0,300);
//        mPath1.lineTo(400,300);
//        mPath1.moveTo(0,400);
//        mPath1.lineTo(400,400);


        mPaint.setStyle(Paint.Style.STROKE);
        mPath1.addCircle(300,300,200, Path.Direction.CW);
        mPath1.addCircle(500,300,200, Path.Direction.CW);
        mPath1.moveTo(300,100);
        mPath1.lineTo(300, 500);

        mPath1.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(mPath1, mPaint);
    }
}
