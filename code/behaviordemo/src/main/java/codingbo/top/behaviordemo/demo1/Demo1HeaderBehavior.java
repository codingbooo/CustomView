package codingbo.top.behaviordemo.demo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.OverScroller;

/**
 * Created by bob
 * on 2018/11/28.
 */
public class Demo1HeaderBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "Demo1HeaderBehavior";
    private final OverScroller mOverScroller;
    private int mHeight;
    private View mChild;
    private boolean onScrolling;

    public Demo1HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOverScroller = new OverScroller(context);
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        boolean b = super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        mChild = child;
        mHeight = child.getHeight();
        return b;
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
            return;
        }
        //上滑
        float translationY = child.getTranslationY();

//        if (translationY < -mHeight + 100 && isOpen()) {
//            if (!onScrolling) {
//                closeHeader();
//            }
//            consumed[1] = dy;
//            return;
//        }

        if (translationY > -mHeight) {
            float toTransY = translationY - dy;
            if (toTransY > -mHeight) {
                mChild.setTranslationY(toTransY);
                consumed[1] = dy;
            } else {
                mChild.setTranslationY(-mHeight);
                consumed[1] = (int) (-mHeight - toTransY);
            }
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {
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

    public boolean isOpen() {
        return mChild.getTranslationY() > -mHeight;
    }


    public void showHeader() {
        flingTo(mChild, 0);
    }

    public void closeHeader() {
        flingTo(mChild, -mHeight);
    }

    private void flingTo(final View child, float toTransY) {
        mOverScroller.startScroll(0, (int) child.getTranslationY(),
                0, (int) (toTransY - child.getTranslationY()),
                200);
        onScrolling = true;
        child.postOnAnimation(new Runnable() {
            @Override
            public void run() {
                if (mOverScroller.computeScrollOffset()) {
                    child.setTranslationY(mOverScroller.getCurrY());
                    child.postOnAnimation(this);
                } else {
                    onScrolling = false;
                }
            }
        });
    }
}
