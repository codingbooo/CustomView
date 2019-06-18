package top.codingbo.instagramstudy.photo.picker;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
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
public class DispatchLinearLayout extends LinearLayout {
    private static final String TAG = "NestedLinearLayout";

    public int mTopViewHeight = ViewUtils.dip2px(300);
    public int mTopBarHeight = ViewUtils.dip2px(50);
    private OverScroller mScroller;
    private boolean inTop;
    private float mDownY;
    private boolean isDragging;

    public DispatchLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setNestedScrollingEnabled(true);
//        }

        mScroller = new OverScroller(context);
//        Log.d(TAG, "init mTopViewHeight: " + mTopViewHeight);
//        Log.d(TAG, "init mTopBarHeight: " + mTopBarHeight);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll: ");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
        Log.d(TAG, "onNestedScrollAccepted: ");
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View child = getChildAt(1);
        child.measure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return super.dispatchTouchEvent(ev);

//        float x = ev.getX();
        float y = ev.getY();
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mDownY = y;
        }
        if (ev.getActionMasked() == MotionEvent.ACTION_UP) {
            isDragging = false;
            switchScroll();
        }

        if (isDragging) {
            Log.d(TAG, "拖动拦截: ");
            scroll(y);
            return true;
        } else if (y < mTopViewHeight && !inTop) {
            Log.d(TAG, "打开状态 开始拦截: ");
            scroll(y);
            isDragging = true;
            return true;
        } else if ((y < mTopBarHeight && inTop)) {
            Log.d(TAG, "关闭状态 开始拦截: ");
            scroll(y);
            isDragging = true;
            return true;
        } else {
            isDragging = false;
            return super.dispatchTouchEvent(ev);
        }
    }

    private void scroll(float pointy) {
        float scrolly;
        if (inTop) {
            Log.d(TAG, "scroll: " + (mDownY - pointy));
            scrolly = mTopViewHeight - mTopBarHeight + (mDownY - pointy);
        } else {
            scrolly = Math.min(mTopViewHeight - pointy, mDownY - pointy);
        }
        scrolly = scrolly < 0 ? 0 : scrolly;
        scrolly = scrolly > (mTopViewHeight - mTopBarHeight) ? (mTopViewHeight - mTopBarHeight) : scrolly;
        Log.d(TAG, "scrollY: " + scrolly);
        setScrollY((int) scrolly);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void switchScroll() {
        boolean moveToTop = getScrollY() < mTopViewHeight / 2;
        if (moveToTop) {
            scrollToBottom();
        } else {
            scrollToTop();
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
