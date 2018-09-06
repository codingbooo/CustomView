package codingbo.viewstudy.myLayout.showMore;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 上拉刷新 下拉加载
 * 分体式
 */
public class ShowMoreLayout extends ViewGroup {

    /**
     * 正常状态
     */
    public static final int STATUS_NORMAL = 0;
    /**
     * 下拉刷新状态
     */
    public static final int STATUS_REFRESHING = 2;

    private View mHeaderView;
    private View mContentView;
    private int mHeaderHeight;
    private int mOffsetY;
    private float mLastX;
    private float mLastY;
    private ShowMoreDefaultHeader mHeader;


    public ShowMoreLayout(Context context) {
        this(context, null);
    }

    public ShowMoreLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowMoreLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //健壮性判断
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

    private void layoutHeader(View header, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        int right = r;
        int bottom = 0;

        top = -mHeaderHeight + mOffsetY;
        bottom = mOffsetY;

        mHeader.onStatus(STATUS_REFRESHING, mOffsetY, mHeaderHeight);
        header.layout(left, top, right, bottom);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        return super.onTouchEvent(event);

        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                float dx = x - mLastX;
                float dy = y - mLastY;

                moveY(dy);


                break;
            case MotionEvent.ACTION_UP:

                moveToNormalPosY();

                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;

        return true;
    }

    private void moveToNormalPosY() {
        ObjectAnimator animator = ObjectAnimator.ofInt(this,
                "offsetY", mOffsetY, 0);
        animator.setDuration(500);
        animator.start();
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
        mOffsetY = offsetY;
        requestLayout();
    }


    /**
     * 显示刷新界面
     */
    public void showRefreshView(boolean show) {

    }

    public void showMoreView(boolean show) {

    }

    interface ShowMoreListener {

        void onRefresh();

        void onMore();

    }
}
