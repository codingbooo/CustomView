package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by bob
 * on 2019/6/24.
 */
public class ScaleLayout extends FrameLayout implements ScaleParent {
    private static final String TAG = "ScaleLayout";
    private View mTargetView;
    private float mX;
    private float mY;
    private float mWidth;
    private float mHeight;
    //初始位置
    private float mInitX;
    private float mInitY;
    private float mInitWidth;
    private float mInitHeight;

    private float backFactor;
    private float mDx;
    private float mDy;
    private float mDWidth;
    private float mDHeight;

    public ScaleLayout(@NonNull Context context) {
        super(context);
    }

    public ScaleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setTargetView(getChildAt(0));
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        setTargetView(child);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        layoutTarget();
    }

    private void setTargetView(View view) {
        if (view == null) {
            return;
        }
        mTargetView = view;

        mX = mTargetView.getX();
        mY = mTargetView.getY();

        mWidth = mTargetView.getWidth();
        mHeight = mTargetView.getHeight();
    }

    public void setInitPosition(float x, float y, float width, float height) {
        mX = mInitX = x;
        mY = mInitY = y;
        mWidth = mInitWidth = width;
        mHeight = mInitHeight = height;
    }

    @Override
    public void moveTo(int x, int y) {
        mX = x;
        mY = y;
        layoutTarget();
    }

    @Override
    public void moveBy(int dx, int dy) {
        mX += dx;
        mY += dy;
        layoutTarget();
    }

    @Override
    public void scaleTo(int width, int height) {
        mWidth = width;
        mHeight = height;
        layoutTarget();
    }

    public void scaleFromCenter(int width, int height) {
        mX -= (width - mWidth) / 2F;
        mY -= (height - mHeight) / 2F;
        mWidth = width;
        mHeight = height;
        layoutTarget();
    }

    @Override
    public void scaleByPoint(int x, int y, int width, int height) {
//        RectF f = new RectF(mX, mY, mX + mWidth, mY + mHeight);

        // xy 相对中心偏移量
        float dx = (x - mX) / mWidth * (width - mWidth);
        float dy = (y - mY) / mHeight * (height - mHeight);

        // 扩大中心偏移量
        float dcx = (mWidth - width) / 2F;
        float dcy = (mHeight - height) / 2F;

        mX = mX + dx + dcx;
        mY = mY + dy + dcy;

        mWidth = width;
        mHeight = height;
        layoutTarget();
    }

    private void layoutTarget() {
        if (mTargetView != null) {
            mTargetView.layout(
                    (int) mX,
                    (int) mY,
                    (int) (mX + mWidth),
                    (int) (mY + mHeight));
        }
    }

    public void backToInitPosition(Animator.AnimatorListener listener) {
        mDx = mX - mInitX;
        mDy = mY - mInitY;
        mDWidth = mWidth - mInitWidth;
        mDHeight = mHeight - mInitHeight;

        ObjectAnimator animator = ObjectAnimator
                .ofFloat(this, "backFactor", 1, 0)
                .setDuration(300);
        animator.addListener(listener);
        animator.start();
    }

    public float getBackFactor() {
        return backFactor;
    }

    public void setBackFactor(float backFactor) {
        this.backFactor = backFactor;
        mX = mInitX + mDx * backFactor;
        mY = mInitY + mDy * backFactor;
        mWidth = (int) (mInitWidth + mDWidth * backFactor);
        mHeight = (int) (mInitHeight + mDHeight * backFactor);
        layoutTarget();
    }
}
