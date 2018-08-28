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
        int usedWidth = 0;
        int usedHeight = 0;

        int maxRowHeight = 0;

        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        int cW;
        int cH;

        int maxWidth = getMeasuredWidth();

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            cW = child.getMeasuredWidth();
            cH = child.getMeasuredHeight();

            if (cW + usedWidth > maxWidth) {
                // 单个或多个 超过父view宽度
                usedHeight += maxRowHeight;

                left = 0;
                top = usedHeight;

                maxRowHeight = cH;
                usedWidth = cW;

            } else {
                // 多个 不超过父view宽度
                left = usedWidth;
                top = top;

                maxRowHeight = maxRowHeight > cH ? maxRowHeight : cH;
                usedWidth += cW;
            }

            right = left + cW;
            bottom = top + cH;
            child.layout(left, top, right, bottom);
        }
    }
}
