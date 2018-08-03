package codingbo.viewstudy.myLayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import codingbo.viewstudy.R;

/**
 * Created by bob
 * on 2018/8/3.
 */
public class MyProgressView extends View {
    private static final String TAG = "MyProgressView";
    private float mDegree;
    private Bitmap mBitmap;
    private Paint mPaint;
    private RectF mRect;
    private float mL;
    private float mT;
    private float mR;
    private float mB;
    private float mCX;
    private float mCY;
    private ObjectAnimator mRotate;

    public MyProgressView(Context context) {
        this(context, null);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.refresh);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new RectF();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mRotate = ObjectAnimator.ofFloat(this, "degree", 0, 360)
                .setDuration(1000);
        mRotate.setRepeatMode(ValueAnimator.RESTART);
        mRotate.setRepeatCount(ValueAnimator.INFINITE);
        mRotate.setInterpolator(new LinearInterpolator());
        mRotate.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRotate.cancel();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mL = getLeft() + getPaddingLeft();
        mT = getTop() + getPaddingTop();
        mR = getRight() - getPaddingRight();
        mB = getBottom() - getPaddingBottom();

        float imageWidth = mBitmap.getWidth();
        float imageHeight = mBitmap.getHeight();

        float scale = Math.max(imageHeight / (mB - mT), imageWidth / (mR - mL));

        imageHeight /= scale;
        imageWidth /= scale;

        mCX = mL / 2 + mR / 2;
        mCY = mT / 2 + mB / 2;

        mL = mCX - imageWidth / 2;
        mR = mL + imageWidth;
        mT = mCY - imageHeight / 2;
        mB = mT + imageHeight;

        mRect.set(mL, mT, mR, mB);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.rotate(mDegree, mCX, mCY);
        canvas.drawBitmap(mBitmap, null, mRect, mPaint);
        canvas.restore();
    }

    public float getDegree() {
        return mDegree;
    }

    public void setDegree(float degree) {
        mDegree = degree;
        invalidate();
    }

}
