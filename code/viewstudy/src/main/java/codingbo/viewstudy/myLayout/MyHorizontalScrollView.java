package codingbo.viewstudy.myLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author bob
 * @date 2018/8/2
 */
public class MyHorizontalScrollView extends ViewGroup {
    private static final String TAG = "MyHorizontalScrollView";

    /**
     * 摩擦系数
     */
    private static final int FRICTION_COEFFICIENT = 2;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private float mLastX;
    private float mLastY;
    private int mWidthPixels;
    private float mInterceptX;
    private float mInterceptY;

    public MyHorizontalScrollView(Context context) {
        this(context, null);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int measureWidth = 0;
        int measureHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            measureWidth += view.getMeasuredWidth();
            measureHeight = measureHeight > view.getHeight() ? measureHeight : view.getHeight();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = height;
        }
        setMeasuredDimension(measureWidth, measureHeight);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        int h = getHeight();
        int w = getWidth();

        int left = l;
        int top = t;
        int right = left;
        int bottom = top;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            left = right;

            right += child.getMeasuredWidth();

            bottom = top + (h > child.getMeasuredHeight() ? child.getMeasuredHeight() : h);

            child.layout(left, top, right, bottom);
        }

        mWidthPixels = getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        float x = ev.getX();
        float y = ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    //    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        float x = ev.getX();
//        float y = ev.getY();
//
//        boolean intercept = false;
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                intercept = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float deltaX = x - mInterceptX;
//                float deltaY = y - mInterceptY;
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    intercept = true;
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//
//                break;
//            default:
//                break;
//        }
//        mLastX = x;
//        mLastY = y;
//
//        mInterceptX = x;
//        mInterceptY = y;
//        return intercept;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);

        float x = event.getX();
        float y = event.getY();

        int scrollX = getScrollX();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;

                if (scrollX - deltaX > 0 && scrollX - deltaX < (getWidth() - mWidthPixels)) {
                    scrollBy((int) -deltaX, 0);
                }

                break;
            case MotionEvent.ACTION_UP:

                mVelocityTracker.computeCurrentVelocity(30);

                float xVelocity = mVelocityTracker.getXVelocity();

                int time = (int) Math.abs(xVelocity / FRICTION_COEFFICIENT);

                int dx = (int) (xVelocity * time / 2);

                if (scrollX - dx < 0) {
                    dx = scrollX;
                } else if (scrollX - dx > (getWidth() - mWidthPixels)) {
                    dx = scrollX - getWidth() + mWidthPixels;
                }
                mScroller.startScroll(getScrollX(), 0, -dx, 0, time * 10);
                invalidate();
                mVelocityTracker.clear();

                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;

        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
