package top.codingbo.instagramstudy.photo.list;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Toast;

/**
 * Created by bob
 * on 2019/6/19.
 */
public class ScaleImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "ScaleImageView";

    private boolean isBig;
    private ScaleGestureDetector mScaleGestureDetector;
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;
    private RectF mRect;
    private boolean scaling;
    private PointF mPointF;
    private RectF mResetRect;

    public ScaleImageView(Context context) {
        super(context);
        init(context);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1 || scaling) {

            boolean b = mScaleGestureDetector.onTouchEvent(event);
            getParent().requestDisallowInterceptTouchEvent(b);

//            Log.d(TAG, "onTouchEvent: " + b);
            return b;
        }


        return super.onTouchEvent(event);

//        boolean b = mScaleGestureDetector.onTouchEvent(event);
//        Log.d(TAG, "onTouchEvent: " + b);
//        return b;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {


        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleBegin: ");

            mLeft = getLeft();
            mTop = getTop();
            mRight = getRight();
            mBottom = getBottom();

            mRect = new RectF(mLeft, mTop, mRight, mBottom);

            scaling = true;

            mPointF = new PointF(detector.getFocusX(), detector.getFocusY());

            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float factor = detector.getScaleFactor();
//            Log.d(TAG, "onScale: " + factor);

            scaleImage = factor;

            // 基于两指中间点的位置
            float wid = mRect.width() * (factor - 1);
            float hei = mRect.height() * (factor - 1);
            Log.d(TAG, "wid:hei =  " + wid + "," + hei);
//            mRect.left -= wid;
//            mRect.top -= hei;
//            mRect.right += wid;
//            mRect.bottom += hei;
            mRect.inset(-wid, -hei);

            //todo 双指平移
//            float dx = detector.getFocusX() - mPointF.x;
//            float dy = detector.getFocusY() - mPointF.y;
//            Log.d(TAG, "onScale: dx, dy = " + dx + ", " + dy);
//            mRect.offset(dx, dy);


//            Log.d(TAG, "onScale mRect: " + mRect.left
//                    + "," + mRect.top
//                    + "," + mRect.right
//                    + "," + mRect.bottom
//            );

//            mRect.right *= factor;
//            mRect.bottom *= factor;

            relayout(mRect);
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleEnd: ");
            showDefault();
//            scaling = false;
        }
    }

    public void scale() {
        if (isBig) {
            showDefault();
        } else {
            showBig();
        }
    }

    public void showBig() {
        Toast.makeText(getContext(), "big", Toast.LENGTH_SHORT).show();
    }

    private float scaleImage;

    public float getScaleImage() {
        return scaleImage;
    }

    public void setScaleImage(float scaleImage) {
        float l = mLeft + (mRect.left - mLeft) * scaleImage;
        float t = mTop + (mRect.top - mTop) * scaleImage;
        float r = mRight + (mRect.right - mRight) * scaleImage;
        float b = mBottom + (mRect.bottom - mBottom) * scaleImage;
        mResetRect.set(l, t, r, b);
        relayout(mResetRect);
    }

    private void relayout(RectF rectF) {
//        Log.d(TAG, "relayout: " + (int) rectF.left
//                + "," + (int) rectF.top
//                + "," + (int) rectF.right
//                + "," + (int) rectF.bottom
//        );

        layout((int) rectF.left,
                (int) rectF.top,
                (int) rectF.right,
                (int) rectF.bottom);
    }

    private void showDefault() {
        mResetRect = new RectF(mRect);
        ObjectAnimator.ofFloat(this, "scaleImage", 1, 0)
                .setDuration(300)
                .start();
        scaling = false;
    }
}
