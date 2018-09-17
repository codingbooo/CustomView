package codingbo.viewstudy.myLayout.showMore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 上拉刷新 下拉加载
 * 分体式
 */
public class ShowMoreLayout extends ViewGroup implements IShowMoreLayout {
//    /**
//     * 正常状态
//     */
//    public static final int STATUS_NORMAL = 0;
//    /**
//     * 下拉状态
//     */
//    public static final int STATUS_DRAGGING = 1;
//    /**
//     * 刷新状态
//     */
//    public static final int STATUS_REFRESHING = 2;
//    /**
//     * 完成回收状态
//     */
//    public static final int STATUS_FINISH = 3;


    private static final String TAG = "ShowMoreLayout";
    private ShowMoreState mCurrentStatus = ShowMoreState.NORMAL;

    private View mHeaderView;
    private View mContentView;
    private int mHeaderHeight;
    private int mOffsetY;
    private float mLastX;
    private float mLastY;
    private ShowMoreDefaultHeader mHeader;
    private ShowMoreListener mShowMoreListener;
    private ObjectAnimator mHeaderCloseAnimator;
    private ObjectAnimator mHeaderOpenAnimator;
    private NestedScrollingParentHelper mScrollingParentHelper;

    public ShowMoreLayout(Context context) {
        this(context, null);
    }

    public ShowMoreLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowMoreLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        return super.onStartNestedScroll(child, target, nestedScrollAxes);
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // target 请求滑动的子view(这里并不一定是mContentView，有可能是mContentView的子View)
        // dx dy  请求滑动的像素点
        // consumed 返回父view消费的距离。长度为2的数组，角标0代表x轴，角标1代表y轴

//        super.onNestedPreScroll(target, dx, dy, consumed);
//        Log.d(TAG, "onNestedPreScroll: dx :" + dx);
//        Log.d(TAG, "onNestedPreScroll: dy :" + dy);
//        Log.d(TAG, "onNestedPreScroll: consumed :" + Arrays.toString(consumed));

        //子view是否能向下滑动
        boolean childCanScrollDown = target.canScrollVertically(-1);
//        Log.d(TAG, "onNestedPreScroll canScrollVertically: " + childCanScrollDown);

//        consumed[0] = 0;
        boolean intercept = !childCanScrollDown || getOffsetY() > 0;
//        Log.d(TAG, "onNestedPreScroll intercept: " + intercept);
        if (intercept) {
            //拦截滑动事件;
            moveY(-dy);
            consumed[1] = dy;
        } else {
            //不拦截滑动事件
            consumed[1] = 0;
        }

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        return super.onNestedFling(target, velocityX, velocityY, consumed);
        //这个方法是up的时候回调

        Log.d(TAG, "onNestedFling: velocity X:" + velocityX);
        Log.d(TAG, "onNestedFling: velocity Y:" + velocityY);
        Log.d(TAG, "onNestedFling: consumed :" + consumed);

        if (getOffsetY() <= 0) {
            return false;
        }

        if (getOffsetY() >= mHeaderHeight / 2) {
            moveToHeaderOpen();
        } else {
            moveToHeaderClose();
        }

        return true;
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
        int top = t + mOffsetY;
        int right = r;
        int bottom = b + mOffsetY;
        content.layout(left, top, right, bottom);

    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
////        return super.onInterceptTouchEvent(ev);
//
//
////        Log.d(TAG, "onInterceptTouchEvent child can ScrollVertically: "
////                + mContentView.canScrollVertically(-1));
//        if (mContentView.canScrollVertically(-1)) {
//            return false;
//        }
//        float x = ev.getX();
//        float y = ev.getY();
//        boolean result = false;
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                result = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float dx = x - mLastX;
//                float dy = y - mLastY;
//                result = dy > 0;
//                break;
//            case MotionEvent.ACTION_UP:
//
//                result = false;
//                break;
//            default:
//                break;
//        }
//        mLastX = x;
//        mLastY = y;
//        return result;
//    }

    private void layoutHeader(View header, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        int right = r;
        int bottom = 0;

        top = -mHeaderHeight + mOffsetY;
        bottom = mOffsetY;

        mHeader.onDragging(ShowMoreState.REFRESH, mOffsetY, mHeaderHeight);
        header.layout(left, top, right, bottom);

    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
////        return super.onTouchEvent(event);
////        if (mCurrentStatus == STATUS_REFRESHING) {
////            return false;
////        }
//        float x = ev.getX();
//        float y = ev.getY();
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float dx = x - mLastX;
//                float dy = y - mLastY;
//                moveY(dy);
//                statusChanged(STATUS_DRAGGING);
//                break;
//            case MotionEvent.ACTION_UP:
//                if (mOffsetY >= mHeaderHeight / 3 * 2) {
//                    moveToHeaderOpen();
//                } else {
//                    moveToHeaderClose();
//                }
//                mLastX = 0;
//                mLastY = 0;
//                break;
//            default:
//                break;
//        }
//
//        mLastX = x;
//        mLastY = y;
//
//        return true;
//    }

    private void moveToHeaderOpen() {
        if (mOffsetY == mHeaderHeight) {
            statusChanged(ShowMoreState.REFRESH);
            return;
        }

        if (mHeaderOpenAnimator != null && mHeaderOpenAnimator.isRunning()) {
            mHeaderOpenAnimator.cancel();
        }

        mHeaderOpenAnimator = ObjectAnimator.ofInt(this,
                "offsetY", mOffsetY, mHeaderHeight);
        mHeaderOpenAnimator.setDuration(500);
        mHeaderOpenAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                statusChanged(ShowMoreState.REFRESH);
            }
        });
        mHeaderOpenAnimator.start();
        statusChanged(ShowMoreState.DRAGGING);
    }

    private void moveToHeaderClose() {
        if (mOffsetY == 0) {
            statusChanged(ShowMoreState.NORMAL);
            return;
        }

        if (mHeaderCloseAnimator != null && mHeaderCloseAnimator.isRunning()) {
            mHeaderCloseAnimator.cancel();
        }

        mHeaderCloseAnimator = ObjectAnimator.ofInt(this,
                "offsetY", mOffsetY, 0);
        mHeaderCloseAnimator.setDuration(300);
        mHeaderCloseAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                statusChanged(ShowMoreState.NORMAL);
            }
        });
        mHeaderCloseAnimator.start();
        statusChanged(ShowMoreState.DRAGGING);
    }

    private void moveY(float dy) {
        mOffsetY += dy;

        mOffsetY = mOffsetY >= mHeaderHeight ? mHeaderHeight : mOffsetY;
        mOffsetY = mOffsetY <= 0 ? 0 : mOffsetY;

        requestLayout();
    }

    public int getOffsetY() {
        return mOffsetY;
    }

    public void setOffsetY(int offsetY) {
        if (mOffsetY == offsetY) {
            return;
        }
        mOffsetY = offsetY;
        requestLayout();
    }

    private void statusChanged(ShowMoreState status) {
        if (status != mCurrentStatus) {
            mHeader.onStatusChanged(status, mCurrentStatus);
            mCurrentStatus = status;
        }

        if (mShowMoreListener != null && mCurrentStatus == ShowMoreState.REFRESH) {
            mShowMoreListener.onRefresh(this);
        }
    }

    /**
     * 显示刷新界面
     */
    @Override
    public void showRefreshView(boolean show) {
        if (show) {
            moveToHeaderOpen();
        } else {
            moveToHeaderClose();
        }
    }

    public void showMoreView(boolean show) {

    }
}
