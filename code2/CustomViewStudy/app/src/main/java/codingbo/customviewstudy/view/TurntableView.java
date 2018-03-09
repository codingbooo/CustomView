package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * 大转盘控件
 */

public class TurntableView extends View {
    private static final String TAG = "TurntableView";

    private List<TurntableInfo> mList;


    private Paint mPaint;

    private TextPaint mTextPaint;
    private final int MARGIN = 30;
    private RectF mArcRectF;
    private float mCY;
    private float mCX;
    private float mAngle;
    private float mCurrentAngle;


    public TurntableView(Context context) {
        super(context);
    }

    public TurntableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TurntableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mArcRectF = new RectF();
    }

    public void setData(List<TurntableInfo> list) {
        mList = list;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float radius;

        float marginX;
        float marginY;

        if (width >= height) {
            radius = height / 2 - MARGIN;

            marginX = width / 2 - radius;
            marginY = MARGIN;
        } else {
            radius = width / 2 - MARGIN;

            marginX = MARGIN;
            marginY = height / 2 - radius;
        }

        mCX = marginX + radius;
        mCY = marginY + radius;

        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        // draw circle
//        canvas.drawCircle(cX, cY, radius, mPaint);

        //draw center of circle
        canvas.drawPoint(mCX, mCY, mPaint);


//        if (mList == null) {
//            return;
//        }

//        int count = mList.size();
        int count = 7;

        mArcRectF.left = marginX;
        mArcRectF.top = marginY;
        mArcRectF.right = marginX + radius * 2;
        mArcRectF.bottom = marginY + radius * 2;

        float startAngle;
        float sweepAngle = 360 / count;
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < count; i++) {
            startAngle = sweepAngle * i + mAngle;
            if (i == count - 1) {
                sweepAngle = 360 - sweepAngle * (i - 1);
            }
            canvas.drawArc(mArcRectF, startAngle, sweepAngle, true, mPaint);
        }
    }

    class TurntableInfo {
        int id;
        String msg;
        Bitmap icon;
        int resId;
    }

    float lastX;
    float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                mAngle += getAngle(x, y, lastX, lastY, mCX, mCY);
                Log.d(TAG, "mAngle: " + mAngle);
                invalidate();
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_UP:
                lastY = 0;
                lastY = 0;
                break;
            default:

                break;
        }


        return true;
    }

    private float getAngle(float x, float y, float lastX, float lastY, float cX, float cY) {
        float a = (float) Math.sqrt(Math.pow((cX - x), 2) + Math.pow((cY - y), 2));
        float b = (float) Math.sqrt(Math.pow((cX - lastX), 2) + Math.pow((cY - lastY), 2));
        float c = (float) Math.sqrt(Math.pow((lastX - x), 2) + Math.pow((lastY - y), 2));
//        cosC=﹙b²+a²-c²﹚/﹙2ab﹚
        return (float) Math.acos((a * a + b * b - c * c) / (2 * a * b));
    }

    public void setAngle(float angle) {
        mAngle = angle;
        invalidate();
    }

    public float getAngle() {

        return mAngle;
    }

}
