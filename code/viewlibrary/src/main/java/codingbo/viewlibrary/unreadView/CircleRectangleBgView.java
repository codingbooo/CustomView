package codingbo.viewlibrary.unreadView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制两边半圆 中间矩形背景效果
 * Created by bob
 * on 17.9.29.
 */

public class CircleRectangleBgView extends View {

    private Paint mPaint;

    private Path mPath;

    public CircleRectangleBgView(Context context) {
        this(context, null);
    }

    public CircleRectangleBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRectangleBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView(context, attrs, defStyleAttr);
    }

    private void intiView(Context context, AttributeSet attrs, int attr) {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);

    }


    {
        mPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //保证width >= height
        width = Math.max(width, height);
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, widthMode),
                MeasureSpec.makeMeasureSpec(height, heightMode));


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


    }

    @Override
    protected void onDraw(Canvas canvas) {

//        drawBackground(canvas);

        DrawBackgroundUtils.drawCircleBg(canvas, mPaint, getWidth(), getHeight());
    }


}
