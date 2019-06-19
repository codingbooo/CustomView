package top.codingbo.instagramstudy.photo.picker;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import top.codingbo.instagramstudy.ViewUtils;

/**
 * Created by bob
 * on 2019/6/18.
 */
public class NestedLinearLayout extends LinearLayout {
    private static final String TAG = "NestedLinearLayout";

    public int mTopViewHeight = ViewUtils.dip2px(300);
    public int mTopBarHeight = ViewUtils.dip2px(50);
    public int maxScrollRange = mTopViewHeight - mTopBarHeight;
    private OverScroller mScroller;
    //当前手指位置
    private float mPointy;

    private boolean inTop;


    public NestedLinearLayout(Context context) {
        super(context);
        init();
    }

    public NestedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new OverScroller(getContext());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mPointy = ev.getY();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll: ");
        //拦截纵向滑动
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.d(TAG, "已拦截滑动: ");
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View child = getChildAt(1);
        int size = MeasureSpec.getSize(heightMeasureSpec) - mTopBarHeight;
        int mode = MeasureSpec.getMode(heightMeasureSpec);

        child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(size, mode));

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // 拦截条件
        // 1. 往上滑 划出 recyclerView的时候
        // 2. 往下滑 recyclerView滑到头的时候
        // dy > 0时,为向下滑动

        Log.d(TAG, "onNestedPreScroll: " + dx);

        if (!inTop && mPointy < mTopViewHeight) {

            int consumedy = dy;

            if (getScrollY() - consumedy > maxScrollRange) {
                consumedy = getScrollY() - maxScrollRange;
            }
            consumed[1] = consumedy;

            scrollBy(0, consumedy);
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG, "onNestedScroll: ");
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        scrollBy(0, dyUnconsumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
//        return true;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
        switchScroll();
    }

    public void switchScroll() {
        boolean shouldMoveToTop;
        if (inTop) {
            shouldMoveToTop = getScrollY() > mTopViewHeight / 4 * 3;
        } else {
            shouldMoveToTop = getScrollY() > mTopViewHeight / 4;
        }

        if (shouldMoveToTop) {
            scrollToTop();
        } else {
            scrollToBottom();
        }
    }

    public void scrollToTop() {
        int dy = mTopViewHeight - mTopBarHeight - getScrollY();
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
