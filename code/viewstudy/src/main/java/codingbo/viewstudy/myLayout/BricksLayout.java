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
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingStart() - getPaddingEnd();
        int maxheight = 0;

        int rowHeight = 0;
        int rowWidth = 0;

        int layoutWidth = 0;
        int layoutHeight = 0;

        int cW;
        int cH;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(count);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            cW = child.getMeasuredWidth();
            cH = child.getMeasuredHeight();

            if (cW > maxWidth) {
                // 单个 超过父View宽度
                layoutWidth += rowWidth + cW;
                layoutHeight += rowHeight + cH;

                rowWidth = 0;
                rowHeight = 0;
            } else {
                if (cW + layoutWidth > maxWidth) {
                    // 多个 超过父view宽度
                    layoutWidth += rowWidth;
                    layoutHeight += rowHeight;

                    rowWidth = cW;
                    rowHeight = cH;
                } else {
                    // 多个 不超过父view宽度
                    rowWidth += cW;
                    rowHeight = cH > rowHeight ? cH : rowHeight;
                }
            }

            setMeasuredDimension(resolveSize(layoutWidth, widthMeasureSpec),
                    resolveSize(layoutHeight, heightMeasureSpec));
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
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

            cW = child.getMeasuredWidth();
            cH = child.getMeasuredHeight();

            if (cW > maxWidth) {
                // 单个 超过父View宽度
                // 另起一行
                left = 0;
                top = usedHeight + maxRowHeight;

                maxRowHeight = cH;
                usedWidth = cW;
                usedHeight += maxRowHeight;
                maxRowHeight = 0;

            } else {
                if (cW + usedWidth > maxWidth) {
                    // 多个 超过父view宽度

                    left = 0;
                    top = usedHeight + maxRowHeight;

                    maxRowHeight = cH;
                    usedWidth = cW;
                    usedHeight += maxRowHeight;
                    maxRowHeight = 0;

                } else {
                    // 多个 不超过父view宽度
                    left = usedWidth;
                    top = top;

                    maxRowHeight = maxRowHeight > cH ? maxRowHeight : cH;
                    usedWidth += cW;
                    
                }
            }

            right = left + cW;
            bottom = top + cH;
            child.layout(left, top, right, bottom);
        }
    }
}
