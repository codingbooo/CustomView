package codingbo.viewstudy.myLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by bob
 * on 2018/8/8.
 */
public class FloatLayout extends ViewGroup {
    private static final String TAG = "FloatLayout";
    private float mDownX;
    private float mDownY;
    private float mLastX;
    private float mLastY;
    private int mTouchSlop;

    public FloatLayout(Context context) {
        this(context, null);
    }

    public FloatLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int maxWidth = 0;
        int maxHeight = 0;
        int count = getChildCount();
        if (count <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            View child = getChildAt(0);
            measureChildWithMargins(child,
                    widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            maxWidth = Math.max(maxWidth, child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(maxWidth + getPaddingLeft() + getPaddingRight(), widthMode),
                    MeasureSpec.makeMeasureSpec(maxHeight + getPaddingTop() + getPaddingBottom(), heightMode));
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        int left = getPaddingLeft() + lp.leftMargin;
        int top = getPaddingTop() + lp.bottomMargin;
        int right = left + child.getMeasuredWidth();
        int bottom = top + child.getMeasuredHeight();
        child.layout(left, top, right, bottom);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        float x = ev.getRawX();
        float y = ev.getRawY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mDownX;
                float deltaY = y - mDownY;
                if (Math.abs(deltaX) > mTouchSlop || Math.abs(deltaY) > mTouchSlop) {
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        float y = ev.getRawY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE: {

                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                moveTo(deltaX, deltaY);
                break;
            }
            case MotionEvent.ACTION_UP: {
                x = -1;
                y = -1;
                break;
            }
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void moveTo(float x, float y) {
        if (!(getLayoutParams() instanceof FrameLayout.LayoutParams)) {
            return;
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        View parent = (View) getParent();

        int pW = parent.getWidth();
        int pH = parent.getHeight();

        int cW = getMeasuredWidth();
        int cH = getMeasuredHeight();

        int maxHorizontalMargins = pW - cW;
        int maxVerticalMargins = pH - cH;


//        if ((params.gravity & Gravity.TOP) == 0) {
//            如果 TOP  转换为 BOTTOM bottomMargin = MAX
//            params.gravity |= Gravity.BOTTOM;
//            params.bottomMargin = maxBottomMargin;
//        }

//        if ((params.gravity & Gravity.START) != 0) {
//            //如果 START  转换为 END   rightMargin = MAX
//            params.gravity |= Gravity.END;
//            params.rightMargin = maxRightMargin;
//        }
//
//        params.gravity = Gravity.END | Gravity.BOTTOM;


        params.leftMargin += x;
        params.topMargin += y;

        params.rightMargin -= x;
        params.bottomMargin -= y;

//        if (params.rightMargin < 0 || params.leftMargin > maxHorizontalMargins) {
//            params.rightMargin = 0;
//            params.leftMargin = maxHorizontalMargins;
//        } else if (params.rightMargin > maxHorizontalMargins || params.leftMargin < 0) {
//            params.rightMargin = maxHorizontalMargins;
//            params.leftMargin = 0;
//        }
//
//        if (params.bottomMargin < 0 || params.topMargin > maxVerticalMargins) {
//            params.bottomMargin = 0;
//            params.topMargin = maxVerticalMargins;
//        } else if (params.bottomMargin > maxVerticalMargins || params.topMargin < 0) {
//            params.bottomMargin = maxVerticalMargins;
//            params.topMargin = 0;
//        }

        if (params.rightMargin < 0) {
            params.rightMargin = 0;
        } else if (params.rightMargin > maxHorizontalMargins) {
            params.rightMargin = maxHorizontalMargins;
        }

        if (params.bottomMargin < 0) {
            params.bottomMargin = 0;
        } else if (params.bottomMargin > maxVerticalMargins) {
            params.bottomMargin = maxVerticalMargins;
        }


//        Log.d(TAG, "parentSize : " + pW + "," + pH);
//        Log.d(TAG, "childSize : " + cW + "," + cH);
//
//        Log.d(TAG, "max bottom margin: " + (pH - cH));
//        Log.d(TAG, "bottom margin: " + params.bottomMargin);
//
//        Log.d(TAG, "max right margin: " + (pW - cW));
//        Log.d(TAG, "right margin: " + params.rightMargin);

        requestLayout();
    }
}
