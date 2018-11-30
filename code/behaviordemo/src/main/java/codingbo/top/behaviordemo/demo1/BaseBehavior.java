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
 * on 2018/11/29.
 */
public abstract class BaseBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "BaseBehavior";

    public enum State {
        Closed,
        Open,
        Closing
    }

    protected final OverScroller mOverScroller;
    protected View mView;
    protected View mHeadView;
    protected int mHeaderHeight;
    protected boolean onScrolling;
    protected boolean posByScrollFling;


    public BaseBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOverScroller = new OverScroller(context);
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        boolean b = super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        mView = child;
        mHeadView = parent.getChildAt(0);
        mHeaderHeight = mHeadView.getMeasuredHeight();
        return b;
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull View child, @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        posByScrollFling = false;
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    public abstract State getCloseStatus();

    public abstract void showHeader();

    public abstract void closeHeader();

    protected void flingTo(final View child, final float toTransY) {
        mOverScroller.startScroll(0, (int) child.getTranslationY(),
                0, (int) (toTransY - child.getTranslationY()),
                200);
        onScrolling = true;
        posByScrollFling = true;
        child.postOnAnimation(new Runnable() {
            @Override
            public void run() {
                if (mOverScroller.computeScrollOffset()) {
                    child.setTranslationY(mOverScroller.getCurrY());
                    child.postOnAnimation(this);
                } else {
                    child.setTranslationY(toTransY);
                    onScrolling = false;
                }
            }
        });
    }
}
