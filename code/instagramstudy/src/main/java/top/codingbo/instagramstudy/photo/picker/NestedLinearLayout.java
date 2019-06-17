package top.codingbo.instagramstudy.photo.picker;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import top.codingbo.instagramstudy.ViewUtils;

/**
 * Created by boliang
 * on 2019-06-16.
 */
public class NestedLinearLayout extends LinearLayout {
    private static final String TAG = "NestedLinearLayout";

    public int mTopViewHeight = ViewUtils.dip2px(300);
    public int mTopBarHeight = ViewUtils.dip2px(50);
    private OverScroller mScroller;
    private boolean inTop;

    public NestedLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setNestedScrollingEnabled(true);
        }

        mScroller = new OverScroller(context);
    }

//    @Override
//    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        Log.d(TAG, "onStartNestedScroll: ");
//        return true;
//    }
//
//    @Override
//    public void onNestedScrollAccepted(View child, View target, int axes) {
//        super.onNestedScrollAccepted(child, target, axes);
//        Log.d(TAG, "onNestedScrollAccepted: ");
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View child = getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);

//        float x = ev.getX();
//        float y = ev.getY();

    }

    public void switchScroll(){
        if(inTop){
            scrolltoBottom();
        } else {
            scrollToTop();
        }
    }


    public void scrollToTop() {
        int dy = mTopViewHeight - mTopBarHeight;
//        int dy = 50;
        mScroller.startScroll(getScrollX(), getScrollY(), 0, dy, 500);
        invalidate();
        inTop = true;
    }


    public void scrolltoBottom() {
        int dy = mTopViewHeight - mTopBarHeight;
//        int dy = 50;
        mScroller.startScroll(getScrollX(), getScrollY(), 0, -dy, 500);
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
