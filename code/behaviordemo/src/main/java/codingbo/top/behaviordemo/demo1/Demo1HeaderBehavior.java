package codingbo.top.behaviordemo.demo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bob
 * on 2018/11/28.
 */
public class Demo1HeaderBehavior extends BaseBehavior {
    private static final String TAG = "Demo1HeaderBehavior";

    public Demo1HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull View child, @NonNull View target,
                                  int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy < 0 || onScrolling) {
            return;
        }
        //上滑
        float translationY = child.getTranslationY();

        if (translationY < -mHeaderHeight + 100 && isOpen()) {
            if (!onScrolling) {
                closeHeader();
            }
            consumed[1] = dy;
            return;
        }

        if (translationY > -mHeaderHeight) {
            float toTransY = translationY - dy;
            if (toTransY > -mHeaderHeight) {
                child.setTranslationY(toTransY);
                consumed[1] = dy;
            } else {
                child.setTranslationY(-mHeaderHeight);
                consumed[1] = (int) (-mHeaderHeight - toTransY);
            }
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {
        //下滑
        if (dyUnconsumed >= 0 || !isOpen() || onScrolling) {
            return;
        }
        float translationY = child.getTranslationY();
        if (translationY < 0) {
            float toTransY = translationY - dyUnconsumed;
            if (toTransY > 0) {
                toTransY = 0;
            }
            child.setTranslationY(toTransY);
        }
    }

    @Override
    public boolean isOpen() {
        return mView.getTranslationY() > -mHeaderHeight;
    }


    @Override
    public void showHeader() {
        flingTo(mView, 0);
    }

    @Override
    public void closeHeader() {
        flingTo(mView, -mHeaderHeight);
    }


}
