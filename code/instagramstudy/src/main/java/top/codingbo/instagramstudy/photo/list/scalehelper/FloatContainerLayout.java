package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import top.codingbo.instagramstudy.R;


/**
 * 悬浮窗体容器类
 * todo 双指缩放 FloatView
 *
 * @author bob
 */
public class FloatContainerLayout extends ViewGroup {
    private static final String TAG = "FloatContainerLayout";
    public static final int CHILD_COUNT = 2;
    public static final int AUTO_EDGE_DURATION = 300;
    private boolean isSliding = false;
    private float mLastX;
    private float mLastY;
    private int mTouchSlop;
    private float mDownX;
    private float mDownY;
    private boolean mAutoEdge;

    public FloatContainerLayout(Context context) {
        this(context, null);
    }

    public FloatContainerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FloatContainerLayout);
        mAutoEdge = ta.getBoolean(R.styleable.FloatContainerLayout_auto_edge, false);

        ta.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //只测量两个Child 第一个为主View  第二个为悬浮View
        int count = getChildCount();
        if (count < CHILD_COUNT) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int maxWidth = 0;
        int maxHeight = 0;
        int childState = 0;

        for (int i = 0; i < CHILD_COUNT; i++) {
            View child = getChildAt(i);
            measureMainChildWithMargins(child, widthMeasureSpec, heightMeasureSpec);

            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            maxWidth = Math.max(maxWidth,
                    child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);

            maxHeight = Math.max(maxHeight,
                    child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    private void measureMainChildWithMargins(View child, int widthMeasureSpec, int heightMeasureSpec) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();

        int measureWidth = getMainChildMeasureSpec(widthMeasureSpec,
                getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin,
                lp.width);

        int measureHeight = getMainChildMeasureSpec(heightMeasureSpec,
                getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin,
                lp.height);

        child.measure(measureWidth, measureHeight);
    }

    private int getMainChildMeasureSpec(int spec, int padding, int childDimension) {
        int specSize = MeasureSpec.getSize(spec);
        int specMode = MeasureSpec.getMode(spec);

        int size = Math.max(0, specSize - padding);
        int resultSpecSize = 0;
        int resultSpecMode = 0;
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                if (childDimension >= 0) {
                    resultSpecSize = childDimension;
                    resultSpecMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                    resultSpecSize = size;
                    resultSpecMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    resultSpecSize = size;
                    resultSpecMode = MeasureSpec.AT_MOST;
                }
                break;
            case MeasureSpec.AT_MOST:
                if (childDimension >= 0) {
                    resultSpecSize = childDimension;
                    resultSpecMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                    resultSpecSize = size;
                    resultSpecMode = MeasureSpec.AT_MOST;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    resultSpecSize = size;
                    resultSpecMode = MeasureSpec.AT_MOST;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                if (childDimension >= 0) {
                    resultSpecSize = childDimension;
                    resultSpecMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                    resultSpecSize = size;
                    resultSpecMode = MeasureSpec.AT_MOST;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    resultSpecSize = size;
                    resultSpecMode = MeasureSpec.UNSPECIFIED;
                }
                break;
            default:
                break;
        }
        return MeasureSpec.makeMeasureSpec(resultSpecSize, resultSpecMode);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //l t r b 是父view的可用范围(全部范围(已经减去margin) - padding 等等) 相对坐标
        int count = getChildCount();

        if (count < CHILD_COUNT) {
            return;
        }

        final int parentLeft = getPaddingLeft();
        final int parentRight = r - l - getPaddingRight();

        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();

        layoutMainView(getChildAt(0), parentLeft, parentTop, parentRight, parentBottom);
        layoutFloatView(getChildAt(1), parentLeft, parentTop, parentRight, parentBottom);
    }

    private void layoutFloatView(View child, int l, int t, int r, int b) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();

        float x = lp.x;
        float y = lp.y;

        int left = l + lp.leftMargin + (int) x;
        int top = t + lp.topMargin + (int) y;

        //边界判断
        left = left < l ? l : left;
        top = top < t ? t : top;

        int right = left + child.getMeasuredWidth();
        int bottom = top + child.getMeasuredHeight();

        right = right > r ? r : right;
        bottom = bottom > b ? b : bottom;

        left = right - child.getMeasuredWidth();
        left = left < l ? l : left;

        top = bottom - child.getMeasuredHeight();
        top = top < t ? t : top;

        child.layout(left, top, right, bottom);
    }

    private void layoutMainView(View child, int l, int t, int r, int b) {

        int width = child.getMeasuredWidth();
        int height = child.getMeasuredHeight();
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int left = l + lp.leftMargin;
        int top = t + lp.topMargin;

        int right = left + width;
        int bottom = top + height;

        int parentR = r - lp.rightMargin;
        int parentB = b - lp.bottomMargin;

        right = right > parentR ? parentR : right;
        bottom = bottom > parentB ? parentB : bottom;

        Log.d(TAG, "left: " + left);
        Log.d(TAG, "top: " + top);
        Log.d(TAG, "right: " + right);
        Log.d(TAG, "bottom: " + bottom);
        child.layout(left, top, right, bottom);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count > CHILD_COUNT) {
            throw new RuntimeException("FloatContainerLayout only can container 2 elements");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isSliding && !touchInFloatView(event)) {
            return false;
        }
        //点击事件落到FloatView上 拦截事件
        float x = event.getRawX();
        float y = event.getRawY();

        boolean result = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                result = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mDownX;
                float deltaY = y - mDownY;
                if (Math.abs(deltaX) > mTouchSlop || Math.abs(deltaY) > mTouchSlop) {
                    result = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                x = 0;
                y = 0;
                mDownX = 0;
                mDownY = 0;
                result = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isSliding && !touchInFloatView(event)) {
            return false;
        }
        //点击事件落到FloatView上 拦截事件
        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;

                moveFloatView(deltaX, deltaY);
                isSliding = true;
                break;
            case MotionEvent.ACTION_UP:
                x = 0;
                y = 0;
                isSliding = false;
                if (mAutoEdge) {
                    goTheEdge();
                }
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void goTheEdge() {
        //判断离哪边近
        View view = getChildAt(1);

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();

        final int edgeLeft = getLeft() + getPaddingLeft();
        final int edgeRight = getRight() - getPaddingRight();

        int distance = 0;
        if (edgeRight - right > left - edgeLeft) {
            //向左滑动
            distance = -(left - edgeLeft);
        } else {
            //像右滑动
            distance = edgeRight - right;
        }
        //速度快变慢
        if (distance != 0) {

            ObjectAnimator animator = ObjectAnimator.ofInt(this, "moveToEdge", distance, 0);
            animator.setInterpolator(new DecelerateInterpolator());

             final int finalDistance = distance;
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    moveToEdge = finalDistance;
                }
            });
            animator.setDuration(AUTO_EDGE_DURATION).start();
        }
    }

    private int moveToEdge = 0;

    public int getMoveToEdge() {
        return moveToEdge;
    }

    public void setMoveToEdge(int moveToEdge) {
        moveFloatView(this.moveToEdge - moveToEdge, 0);
//        Log.d(TAG, "setMoveToEdge: " + (this.moveToEdge - moveToEdge));
        this.moveToEdge = moveToEdge;
    }

    private void moveFloatView(float dx, float dy) {
        if (getChildCount() < CHILD_COUNT) {
            return;
        }
        View view = getChildAt(1);
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        lp.x += dx;
        lp.y += dy;
//        Log.d(TAG, "moveFloatView dddd: " + dx + ":" + dy);
//        Log.d(TAG, "moveFloatView lppp: " + lp.x + ":" + lp.x);
        requestLayout();
    }

    private boolean touchInFloatView(MotionEvent event) {
        if (getChildCount() < CHILD_COUNT) {
            return false;
        }

        View view = getChildAt(1);

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        float x = event.getRawX();
        float y = event.getRawY();

//        Log.d(TAG, "touchInFloatView: " + x + ":" + y);
//        Log.d(TAG, "touchInFloatView: " + left + ":" + top + ":" + right + ":" + bottom);
//        Log.d(TAG, "touchInFloatView: " + (left < x && x < right && top < y && y < bottom));
        return left < x && x < right
                && top < y && y < bottom;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    class LayoutParams extends MarginLayoutParams {

        public float x;
        public float y;

        public static final int POSITION_DEFAULT = 0;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            final TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.FloatContainerLayout_Layout);
            x = a.getDimension(R.styleable.FloatContainerLayout_Layout_pos_x, POSITION_DEFAULT);
            y = a.getDimension(R.styleable.FloatContainerLayout_Layout_pos_y, POSITION_DEFAULT);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, float x, float y) {
            super(width, height);
            this.x = x;
            this.y = y;
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);

        }

        public LayoutParams(LayoutParams source) {
            super(source);
            this.x = source.x;
            this.y = source.y;
        }
    }
}
