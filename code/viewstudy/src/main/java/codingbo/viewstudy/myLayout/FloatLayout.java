package codingbo.viewstudy.myLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

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
//            measureChildWithMargins(child,
//                    widthMeasureSpec, 0, heightMeasureSpec, 0);
//            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
//            maxWidth = Math.max(maxWidth, child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
//            maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);


            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lp = child.getLayoutParams();
            maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());

        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(maxWidth, widthMode),
                MeasureSpec.makeMeasureSpec(maxHeight, heightMode));
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        int left = 0;
        int top = 0;
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
                float deltaX = x - mDownX;
                float deltaY = y - mDownY;
                Log.d(TAG, "delta: " + deltaX + " : " + deltaY);
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

        Log.d(TAG, "parentSize : " + pW + "," + pH);
        Log.d(TAG, "childSize : " + cW + "," + cH);

        params.rightMargin -= x;
        params.bottomMargin -= y;

        if (params.rightMargin < 0) {
            params.rightMargin = 0;
        } else if (params.rightMargin > pW - cW) {
            params.rightMargin = pW - cW;
        }

        if (params.bottomMargin < 0) {
            params.bottomMargin = 0;
        } else if (params.bottomMargin > pH - cH) {
            params.bottomMargin = pH - cH;
        }
//        Log.d(TAG, params.leftMargin
//                + "  " + params.topMargin
//                + "  " + params.bottomMargin
//                + "  " + params.rightMargin);

        requestLayout();
    }
}
