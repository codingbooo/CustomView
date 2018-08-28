package codingbo.viewstudy.myLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import codingbo.viewstudy.R;

/**
 * 砖头布局
 */
public class BricksLayout extends ViewGroup {
    public static final int DEFAULT_ITEM_GAP = 20;


    private int mItemGap = DEFAULT_ITEM_GAP;

    public BricksLayout(Context context) {
        this(context, null);
    }

    public BricksLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BricksLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BricksLayout);
        mItemGap = (int) ta.getDimension(R.styleable.BricksLayout_item_gap, DEFAULT_ITEM_GAP);
        ta.recycle();
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


    public int getItemGap() {
        return mItemGap;
    }

    public void setItemGap(int itemGap) {
        mItemGap = itemGap;
        requestLayout();
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
                usedHeight += maxRowHeight + mItemGap;

                left = paddingLeft + lp.leftMargin;
                top = usedHeight + lp.topMargin;

                usedWidth = paddingLeft + cWAndMargin + mItemGap;
                maxRowHeight = cHAndMargin;
            } else {
                // 多个 不超过父view宽度
                left = usedWidth + lp.leftMargin;
                top = usedHeight + lp.topMargin;

                maxRowHeight = maxRowHeight > cHAndMargin ? maxRowHeight : cHAndMargin;
                usedWidth += cWAndMargin + mItemGap;
            }

            right = left + cW;
            bottom = top + cH;
            child.layout(left, top, right, bottom);
        }
    }
}
