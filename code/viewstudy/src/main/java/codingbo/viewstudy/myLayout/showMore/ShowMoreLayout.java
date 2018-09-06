package codingbo.viewstudy.myLayout.showMore;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import codingbo.viewstudy.R;

/**
 * 上拉刷新 下拉加载
 * 分体式
 */
public class ShowMoreLayout extends ViewGroup {

//    ImageView mHeaderView;

    private View mHeaderView;
    private View mContentView;
    private int mHeaderHeight;
    private int mOffsetY;
    private float mLastX;
    private float mLastY;


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


        ImageView headerView = getHeaderView(getContext());
        mHeaderView = headerView;

        addView(headerView);
    }

    @NonNull
    private ImageView getHeaderView(Context context) {
        ImageView imageView = new ImageView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 300);
        imageView.setLayoutParams(params);
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_refresh);
        imageView.setImageDrawable(drawable);
        imageView.setBackgroundColor(Color.WHITE);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
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


                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;

        return true;
    }

    private void moveY(float dy) {
        mOffsetY += dy;
        requestLayout();
    }

    public void showRefreshView(boolean show) {

    }

    public void showMoreView(boolean show) {

    }

    interface ShowMoreListener {

        void onRefresh();

        void onMore();

    }
}
