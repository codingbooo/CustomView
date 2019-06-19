package top.codingbo.instagramstudy.photo.list;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
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
            Log.d(TAG, "onScale: " + factor);

//            detector.getCurrentSpan()

//            float scaleX = mScaleX * factor;
//            float scaleY = mScaleY * factor;

//            detector.getCurrentSpan()

//            setScaleX(factor);
//            setScaleY(factor);

//            setScaleX(2);
//            setScaleY(2);

//            detector.getFocusX();

//            mRight *= factor;
//            mBottom *= factor;


            // todo 改为基于点的位置
            mRect.right *= factor;
            mRect.bottom *= factor;

//            layout(mLeft,
//                    mTop,
//                    (int) (mRight * factor),
//                    (int) (mBottom * factor));


            float dx = detector.getFocusX() - mPointF.x;
            float dy = detector.getFocusY() - mPointF.y;
            Log.d(TAG, "onScale: dx, dy = " + dx + ", " + dy);
            mRect.offset(dx, dy);

            layout((int) mRect.left,
                    (int) mRect.top,
                    (int) mRect.right,
                    (int) mRect.bottom);


            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleEnd: ");
//            showDefault();
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

    private void showDefault() {
        setScaleX(1);
        setScaleY(1);
    }
}
