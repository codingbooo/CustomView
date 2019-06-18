package top.codingbo.instagramstudy.photo.picker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import top.codingbo.instagramstudy.ViewUtils;

/**
 * Created by boliang
 * on 2019-06-16.
 */
public class NestedLinearLayout2 extends LinearLayout {
    private static final String TAG = "NestedLinearLayout";

    public int mTopViewHeight = ViewUtils.dip2px(300);
    public int mTopBarHeight = ViewUtils.dip2px(50);
    public int maxScorllYRange = mTopViewHeight - mTopBarHeight;


    private OverScroller mScroller;
    private boolean inTop;
    private float mPointY;
    private boolean mDragging;

    public NestedLinearLayout2(Context context) {
        super(context);
        init(context, null);
    }

    public NestedLinearLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NestedLinearLayout2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mScroller = new OverScroller(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View child = getChildAt(1);
        child.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mPointY = ev.getY();
        if (ev.getActionMasked() == MotionEvent.ACTION_UP && mDragging) {
            switchScroll();
//            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {

        Log.d(TAG, "onStartNestedScroll: ");
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
        Log.d(TAG, "onNestedScrollAccepted: ");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        super.onNestedPreScroll(target, dx, dy, consumed);
        // 条件
        // 1 y 在 topView 上面
        // 2

        if (mPointY < mTopViewHeight && !inTop) {
            mDragging = true;
            int consumedY = dy;

            if (getScrollY() + consumedY > maxScorllYRange) {
                consumedY = maxScorllYRange - getScrollY();
            }
            consumed[1] = consumedY;
            scrollBy(0, consumedY);
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

//        int scrolly = getScrollY();
//        scrolly = scrolly + dyUnconsumed <0 ?

        if(inTop){
            scrollBy(0, dyUnconsumed);
        }
    }

    public void switchScroll() {
        int scrollY = getScrollY();


        boolean scrollToTop = true;

        if (inTop) {
            scrollToTop = scrollY > maxScorllYRange / 4 * 3;
        } else {
            scrollToTop = scrollY > maxScorllYRange / 4;
        }

        if (scrollToTop) {
            scrollToTop();
        } else {
            scrollToBottom();
        }
    }


    public void scrollToTop() {
        int dy = maxScorllYRange - getScrollY();
        mScroller.startScroll(getScrollX(), getScrollY(), 0, dy, 500);
        invalidate();
        inTop = true;
    }


    public void scrollToBottom() {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, -getScrollY(), 500);
        invalidate();
        inTop = false;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            setScrollY(mScroller.getCurrY());
            invalidate();
        }
    }
}
