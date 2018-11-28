package codingbo.top.behaviordemo.demo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by bob
 * on 2018/11/28.
 */
public class Demo1Behavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "Demo1Behavior";
    private int mHeaderMeasuredHeight;

    public Demo1Behavior() {
    }

    public Demo1Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {

        boolean b = super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        View header = parent.getChildAt(0);
        mHeaderMeasuredHeight = header.getMeasuredHeight();
        return b;

    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (params != null && params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
//            Log.d(TAG, "l: " + child.getLeft());
//            Log.d(TAG, "t: " + child.getTop());
//            Log.d(TAG, "r: " + child.getRight());
//            Log.d(TAG, "b: " + child.getBottom());
//            Log.d(TAG, "parent.getWidth : " + parent.getWidth());
//            Log.d(TAG, "parent.getHeight : " + parent.getHeight());
            //布局
            child.layout(0, 0, parent.getWidth(), parent.getHeight());
            //获取header宽度
            child.setTranslationY(mHeaderMeasuredHeight);
            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);

    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull View child, @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull View child, @NonNull View target,
                                  int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy < 0) {
            // 下滑不拦截
            return;
        }
        //上滑 优先拦截
        float translationY = child.getTranslationY();
        if (translationY > 0) {
            //拦截并移动
            float toTransY = 0;
            int consumedY = (int) translationY;
            if (translationY > dy) {
                toTransY = translationY - dy;
                consumedY = dy;
            }
            child.setTranslationY(toTransY);
            consumed[1] = consumedY;
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull View child, @NonNull View target,
                               int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {
        //下滑 拦截
        if (dyUnconsumed > 0) {
            return;
        }
        float translationY = child.getTranslationY();
        if (translationY < mHeaderMeasuredHeight) {
            float dY = mHeaderMeasuredHeight - translationY;
            float toTransY;
            if (dY > -dyUnconsumed) {
                toTransY = translationY - dyUnconsumed;
            } else {
                toTransY = mHeaderMeasuredHeight;
            }
            child.setTranslationY(toTransY);
        }
    }
}
