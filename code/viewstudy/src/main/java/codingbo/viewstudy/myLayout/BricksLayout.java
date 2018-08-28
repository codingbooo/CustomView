package codingbo.viewstudy.myLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 砖头布局
 */
public class BricksLayout extends ViewGroup {


    public BricksLayout(Context context) {
        this(context, null);
    }

    public BricksLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BricksLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();
        final int maxWidth = getMeasuredWidth() - getPaddingStart() - getPaddingEnd();

        int usedWidth = paddingLeft;
        int usedHeight = paddingTop;

        int maxRowHeight = 0;

        int left = paddingLeft;
        int top = paddingTop;
        int right;
        int bottom;

        int cW;
        int cH;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            cW = child.getMeasuredWidth();
            cH = child.getMeasuredHeight();

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int cHAndMargin = cH + lp.topMargin + lp.bottomMargin;
            final int cWAndMargin = cW + lp.leftMargin + lp.rightMargin;

            //超出范围
            boolean outBound = usedWidth + cWAndMargin > maxWidth;

            if (outBound) {
                // 单个或多个 超过父view宽度
                usedHeight += maxRowHeight;

                left = paddingLeft + lp.leftMargin;
                top = usedHeight + lp.topMargin;

                usedWidth = paddingLeft + cWAndMargin;
                maxRowHeight = cHAndMargin;
            } else {
                // 多个 不超过父view宽度
                left = usedWidth + lp.leftMargin;
                top = usedHeight + lp.topMargin;

                maxRowHeight = maxRowHeight > cHAndMargin ? maxRowHeight : cHAndMargin;
                usedWidth += cWAndMargin;
            }

            right = left + cW;
            bottom = top + cH;
            child.layout(left, top, right, bottom);
        }
    }
}
