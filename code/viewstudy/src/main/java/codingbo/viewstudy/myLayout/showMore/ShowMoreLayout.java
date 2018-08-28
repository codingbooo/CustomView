package codingbo.viewstudy.myLayout.showMore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 上拉刷新 下拉加载
 * 分体式
 */
public class ShowMoreLayout extends ViewGroup {

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
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
