package codingbo.viewstudy.myLayout.showMore;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by bob
 * on 2018/9/10.
 */
public class ShowMoreLayout2 extends ViewGroup {
    private static final String TAG = "ShowMoreLayout";
    /**
     * 正常状态
     */
    public static final int STATUS_NORMAL = 0;
    /**
     * 下拉状态
     */
    public static final int STATUS_DRAGGING = 1;
    /**
     * 刷新状态
     */
    public static final int STATUS_REFRESHING = 2;


    private View mHeaderView;
    private View mContentView;
    private int mHeaderHeight;
    private ShowMoreDefaultHeader mHeader;
    private Scroller mScroller;

    public ShowMoreLayout2(Context context) {
        this(context, null);
    }

    public ShowMoreLayout2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowMoreLayout2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //TODO 健壮性判断
        View mContent = getChildAt(0);

        mContentView = mContent;


        mHeader = ShowMoreDefaultHeader.getInstance(getContext());
        View headerView = mHeader.getView();
        mHeaderView = headerView;

        addView(headerView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutHeader(mHeaderView, l, t, r, b);

        layoutContent(mContentView, l, t, r, b);
    }

    private void layoutContent(View content, int l, int t, int r, int b) {
        int left = l;
        int top = t;
        int right = r;
        int bottom = b;
        content.layout(left, top, right, bottom);

    }

    private void layoutHeader(View header, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        int right = r;
        int bottom = 0;

        top = -mHeaderHeight;
        bottom = 0;

        mHeader.onDragging(STATUS_REFRESHING, 0, mHeaderHeight);
        header.layout(left, top, right, bottom);

    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //1 向上滑的时候 如果scrollY < 0 拦截
        //2 向下滑的时候 如果子view不能向下滑 拦截

        boolean interceptScroll;

        if (dy > 0) {
            //1 向上滑的时候 如果scrollY < 0 拦截
            interceptScroll = getScrollY() < 0;
        } else {
            //2 向下滑的时候 如果子view不能向下滑 拦截
            interceptScroll = !target.canScrollVertically(-1);
        }

        if (interceptScroll) {
            //拦截
            consumed[1] = dy;
            moveYTo(dy);
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//        Log.d(TAG, "onNestedScroll dyConsumed: " + dyConsumed + ", dyUnconsumed: " + dyUnconsumed);
    }

//    @Override
//    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
//        return getScrollY() < 0;
//        // 如果 header 正在展示 则消耗此时间
//
//        boolean headerIsShow = getScrollY() < 0;
////        Log.d(TAG, "onNestedFling headerIsShow: " + headerIsShow);
//        if (!headerIsShow) {
//            return false;
//        }
//
//        boolean showHeader = getScrollY() < -mHeaderHeight / 2;
//
//        Log.d(TAG, "onNestedFling showHeader: " + showHeader);
//
//        if (showHeader) {
//            //open
//            showHeaderView();
//        } else {
//            //close
//            closeHeaderView();
//        }
//
//        return true;
//    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        return super.onNestedFling(target, velocityX, velocityY, consumed);

        // 如果 header 正在展示 则消耗此时间

        boolean headerIsShow = getScrollY() < 0;
//        Log.d(TAG, "onNestedFling headerIsShow: " + headerIsShow);
        if (!headerIsShow) {
            return false;
        }

        boolean showHeader = getScrollY() < -mHeaderHeight / 2;

        Log.d(TAG, "onNestedFling showHeader: " + showHeader);

        if (showHeader) {
            //open
            showHeaderView();
        } else {
            //close
            closeHeaderView();
        }

        return true;
    }

    private void closeHeaderView() {
//        moveYTo(-getScrollY());

        Log.d(TAG, "closeHeaderView: --------------------------------");
        scroller(-getScrollY());
    }

    private void showHeaderView() {
        Log.d(TAG, "showHeaderView: ----------------------------");
//        moveYTo(-mHeaderHeight);
        scroller(-mHeaderHeight - getScrollY());
    }

    private void moveYTo(int dy) {
        int y = getScrollY() + dy;
        y = y >= 0 ? 0 : y;
        y = y <= -mHeaderHeight ? -mHeaderHeight : y;
//        Log.d(TAG, "scrollTo: " + y);
        scrollTo(0, y);
    }

    private void scroller(int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        Log.d(TAG, "computeScroll: ");
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
