package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by bob
 * on 17.9.19.
 */

public class TypeEvaluatorView extends View {

    public TypeEvaluatorView(Context context) {
        super(context);
    }

    public TypeEvaluatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TypeEvaluatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mColor = 0xff0000ff;

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
        invalidate();
    }


    private Paint mPaint;

    private PointF mPosition;

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public PointF getPosition() {
        return mPosition;
    }

    public void setPosition(PointF position) {
        mPosition = position;
        invalidate();
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setStyle(Paint.Style.FILL);

        mPosition = new PointF(300, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mColor);
        canvas.drawCircle(mPosition.x, mPosition.y, 200, mPaint);
    }
}
