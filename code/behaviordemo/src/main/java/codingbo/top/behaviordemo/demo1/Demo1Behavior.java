package codingbo.top.behaviordemo.demo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.OverScroller;

/**
 * Created by bob
 * on 2018/11/28.
 */
public class Demo1Behavior extends BaseBehavior {
    private static final String TAG = "Demo1Behavior";

    public Demo1Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (params != null && params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            //布局
            child.layout(0, 0, parent.getWidth(), parent.getHeight());
            //获取header宽度
            child.setTranslationY(mHeaderHeight);
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
        if (dy < 0 || onScrolling) {
            // 下滑不拦截
            return;
        }
        float translationY = child.getTranslationY();

        if (translationY < 100 && isOpen()) {
            if (!onScrolling) {
                closeHeader();
            }
            consumed[1] = dy;
            return;
        }

        //上滑 优先拦截
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
        if (dyUnconsumed > 0 || !isOpen() || onScrolling) {
            return;
        }
        //下滑 拦截
        float translationY = child.getTranslationY();
        if (translationY < mHeaderHeight) {
            float dY = mHeaderHeight - translationY;
            float toTransY;
            if (dY > -dyUnconsumed) {
                toTransY = translationY - dyUnconsumed;
            } else {
                toTransY = mHeaderHeight;
            }
            child.setTranslationY(toTransY);
        }
    }

    @Override
    public boolean isOpen() {
        return mView.getTranslationY() != 0;
    }

    @Override
    public void showHeader() {
        flingTo(mView, mHeaderHeight);
    }

    @Override
    public void closeHeader() {
        flingTo(mView, 0);
    }
}
