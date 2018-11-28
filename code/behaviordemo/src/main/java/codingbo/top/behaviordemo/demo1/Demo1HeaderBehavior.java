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
public class Demo1HeaderBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "Demo1HeaderBehavior";
    private int mHeight;

    public Demo1HeaderBehavior() {
    }

    public Demo1HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull View child, @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        mHeight = child.getHeight();
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull View child, @NonNull View target,
                                  int dx, int dy, @NonNull int[] consumed, int type) {
        Log.d(TAG, "onNestedPreScroll: " + dy);
        if (dy < 0) {
            return;
        }

        //上滑
        float translationY = child.getTranslationY();
        Log.d(TAG, "onNestedPreScroll: " + translationY);


        if (translationY > -mHeight) {
            float toTransY = translationY - dy;
            if (toTransY > -mHeight) {
                child.setTranslationY(toTransY);
                consumed[1] = dy;
            } else {
                child.setTranslationY(-mHeight);
                consumed[1] = (int) (-mHeight - toTransY);
            }
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {

        Log.d(TAG, "dyConsumed: " + dyConsumed + ",  dyUnconsumed: " + dyUnconsumed);

        //下滑
        if (dyUnconsumed >= 0) {
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
}
