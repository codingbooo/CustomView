package codingbo.viewlibrary.unreadView;

import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
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
    private int defaultRadius;


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
        mTextPaint.setTextSize(50);
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
        defaultRadius = Math.min(width, height) / 2;
        mCRadius = defaultRadius;

        mFX = mCX + 300;
        mFY = mCY + 300;
        mFRadius = mCRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {


        float dY = mFY - mCY;
        float dX = mFX - mCX;
        mDistance = Math.sqrt(Math.hypot(dX, dY));

        mCRadius = (int) Math.abs(defaultRadius - mDistance);

        Log.d(TAG, "mDistance: " + mDistance);
        Log.d(TAG, "mCRadius: " + mCRadius);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);
        //draw center circle
        canvas.drawCircle(mCX, mCY, mCRadius, mPaint);

        //draw finger circle
        canvas.drawCircle(mFX, mFY, mFRadius, mPaint);


        double angle;
        if (dX == 0) {
            angle = 0;
        } else {
            angle = -Math.atan(dY / dX);
        }

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

        mPaint.setColor(Color.RED);
//        mPaint.setStyle(Paint.Style.STROKE);

        mPath.reset();
        mPath.moveTo(c1x, c1y);
        mPath.cubicTo(c1x, c1y, cX, cY, f1x, f1y);
        mPath.lineTo(f2x, f2y);
        mPath.cubicTo(f2x, f2y, cX, cY, c2x, c2y);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
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
                break;
            case MotionEvent.ACTION_UP:
//                mFX = mCX;
//                mFY = mCY;

                ObjectAnimator animator = ObjectAnimator.ofObject(this, "point", new PointFEvaluator(),
                        new PointF(x, y), new PointF(mCX, mCY));
                animator.setDuration(300);
                animator.start();

                break;

            default:
                break;
        }
        return true;
    }

    public void setPoint(PointF pointF) {
        mFX = pointF.x;
        mFY = pointF.y;
        invalidate();
    }

    public PointF getPoint() {
        return new PointF(mFX, mFY);
    }
}
