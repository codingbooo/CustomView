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
public class ShowMoreLayout2 extends ViewGroup implements IShowMoreLayout {
    private static final String TAG = "ShowMoreLayout";

    private View mHeaderView;
    private View mContentView;
    private int mHeaderHeight;
    private ShowMoreHeader mHeader;
    private Scroller mScroller;

    private ShowMoreState mCurrentState = ShowMoreState.NORMAL;


    private ShowMoreListener mListener;

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

        header.layout(left, top, right, bottom);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //拦截纵向滑动事件
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // 1 向下滑的时候 如果子view不能向下滑 拦截
        // dy < 0 && !target.canScrollVertically(-1)
        // 2 下拉状态一定拦截
        // getStatus() == ShowMoreState.DRAGGING
        // 3 刷新状态一定不拦截
        // getStatus() == ShowMoreState.REFRESH
        boolean intercept = getStatus() != ShowMoreState.REFRESH &&
                (getStatus() == ShowMoreState.DRAGGING ||
                        (dy < 0 && !target.canScrollVertically(-1)));

        if (intercept) {
            consumed[1] = dy;
            moveYTo(dy);
            setStatus(ShowMoreState.DRAGGING);
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        // 滑动速度过慢 不对回调该方法

        // 拉动过程中拦截
        //  根据下拉的位置 决定 滑到初始状态 还是 刷新状态

        if (getStatus() != ShowMoreState.DRAGGING) {
            return false;
        }
        fling();

        return true;
    }

    private void fling() {
        // 滑动处理
        // 下滑y轴偏移量getScrollY()为负值
        // 1. 偏移量 < -mHeaderHeight / 2  展开 header
        // 2. 偏移量 > -mHeaderHeight / 2  关闭 header
        // 3. 偏移量 > 0时, 不作处理

        if (getScrollY() < -mHeaderHeight / 2) {
            showHeaderView();
        } else if (getScrollY() < 0) {
            closeHeaderView();
        }
    }

    @Override
    public void showRefreshView(boolean show) {
        if (show) {
            showHeaderView();
        } else {
            closeHeaderView();
        }
    }


    @Override
    public void onStopNestedScroll(View child) {
        //滑动结束时, 若在初始化位置 则修改改为 Normal
        if (getScrollY() >= 0) {
            setStatus(ShowMoreState.NORMAL);
            return;
        }

        //当手指滑动距离不足时,onNestedPreFling()方法不会被回调,
        //此时需要在此方法, 处理后续操作
        if (getStatus() == ShowMoreState.DRAGGING) {
            fling();
        }
    }


    private void closeHeaderView() {
        setStatus(ShowMoreState.NORMAL);
        scroller(-getScrollY());
    }

    private void showHeaderView() {
        setStatus(ShowMoreState.REFRESH);
        scroller(-mHeaderHeight - getScrollY());
    }

    private void setStatus(ShowMoreState refresh) {
        if (mCurrentState == refresh) {
            return;
        }
        if (mHeader != null) {
            mHeader.onStatusChanged(refresh, mCurrentState);
        }
        mCurrentState = refresh;
        if (mListener != null && mCurrentState == ShowMoreState.REFRESH) {
            mListener.onRefresh(this);
        }
    }

    private ShowMoreState getStatus() {
        return mCurrentState;
    }

    private void moveYTo(int dy) {
        int y = getScrollY() + dy;
        y = y >= 0 ? 0 : y;
        y = y <= -mHeaderHeight ? -mHeaderHeight : y;
        scrollTo(0, y);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        Log.d(TAG, "scrollTo: " + getScrollY());
        mHeader.onDragging(mCurrentState, getScrollY(), -mHeaderHeight);
    }

    private void scroller(int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void setListener(ShowMoreListener listener) {
        mListener = listener;
    }


}
