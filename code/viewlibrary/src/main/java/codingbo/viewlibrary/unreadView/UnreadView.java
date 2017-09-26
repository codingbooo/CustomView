package codingbo.viewlibrary.unreadView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 仿QQ未读小红点
 */

public class UnreadView extends View {
    private static final String TAG = "UnreadView";
    /**
     * 手指距离View的距离
     */
    private double mDistance;
    //view
    private Paint mPaint;
    //center circle
    private int mCX;
    private int mCY;
    private int mCRadius;
    //finger circle
    private float mFX;
    private float mFY;
    private int mFRadius;

    //text
    private TextPaint mTextPaint;
    private float PADDING = 10;
    private int unreadCount = 99;
    private Rect mTextBounds;
    private int textSize = 20;
    private Path mPath;


    public UnreadView(Context context) {
        this(context, null);
    }

    public UnreadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize(attrs, defStyleAttr);
    }

    private void initialize(AttributeSet attrs, int attr) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);

        mTextBounds = new Rect();

        mPath = new Path();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();
        mCX = width / 2;
        mCY = height / 2;
        mCRadius = Math.min(width, height) / 2;

        mFX = width / 2 + 300;
        mFY = height / 2 + 300;
        mFRadius = Math.min(width, height) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);
        //draw center circle
        canvas.drawCircle(mCX, mCY, mCRadius, mPaint);

        //draw finger circle
        canvas.drawCircle(mFX, mFY, mFRadius, mPaint);


        mDistance = Math.sqrt(Math.hypot(mCX - mFX, mCY - mFY));
        double angle;
        if (mCX - mFX == 0) {
            angle = 0;
        } else {
            angle = Math.atan(Math.abs((mCY - mFY) / (mCX - mFX))) / Math.PI * 180;
        }

        Log.d(TAG, "angle: " + angle);


        //考虑象限
        float c1x = (float) (mCX - Math.sin(angle) * mCRadius);
        float c1y = (float) (mCY - Math.cos(angle) * mCRadius);

        float c2x = (float) (mCX + Math.sin(angle) * mCRadius);
        float c2y = (float) (mCY + Math.cos(angle) * mCRadius);

        float f1x = (float) (mFX - Math.sin(angle) * mFRadius);
        float f1y = (float) (mFY - Math.cos(angle) * mFRadius);

        float f2x = (float) (mFX + Math.sin(angle) * mFRadius);
        float f2y = (float) (mFY + Math.cos(angle) * mFRadius);

        float cX = mCX / 2 + mFX / 2;
        float cY = mCY / 2 + mFY / 2;

//        mPath.moveTo(c1x, c1y);
//        mPath.rQuadTo(cX, cY, f2x, f2y);
//        mPath.lineTo(f1x, f1y);
//        mPath.rQuadTo(cX, cY, c2x, c2y);
//        mPath.close();


//        mPath.moveTo(c1x, c1y);
//        mPath.lineTo(cX, cY);
//        mPath.lineTo(f1x, f1y);
//        mPath.lineTo(f2x, f2y);
//        mPath.lineTo(cX, cY);
//        mPath.lineTo(c2x, c2y);
//        mPath.close();

//        Log.d(TAG, "c1: " + "(" + c1x + "," + c1y + ")");
//        Log.d(TAG, "c2: " + "(" + c2x + "," + c2y + ")");
//        Log.d(TAG, "f1: " + "(" + f1x + "," + f1y + ")");
//        Log.d(TAG, "f2: " + "(" + f2x + "," + f2y + ")");

//        canvas.drawPath(mPath, mPaint);


        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(c1x, c1y, mPaint);
        canvas.drawPoint(c2x, c2y, mPaint);
        canvas.drawPoint(f1x, f1y, mPaint);
        canvas.drawPoint(f2x, f2y, mPaint);
        canvas.drawPoint(cX, cY, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                mFX = x;
                mFY = y;
                invalidate();
                getParent().requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                mFX = mCX;
                mFY = mCY;
                invalidate();
                getParent().requestLayout();
                break;

            default:
                break;
        }
        return true;
    }
}
