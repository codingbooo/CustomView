package codingbo.viewstudy.thumb.thumbView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import codingbo.viewstudy.R;

/**
 * 手指View
 */

public class ThumbView extends View {

    private static final String TAG = "ThumbView";

    private Bitmap mScaleXYThumbNormal;
    private Bitmap mScaleXYShining;
    private Bitmap mScaleXYThumbUp;

    private Bitmap mStandardThumbNormal;
    private Bitmap mStandardShining;
    private Bitmap mStandardThumbUp;

    private Point mCenterPoint;
    private Paint mPaint;
    private Point mThumbPoint;
    private Point mShiningPoint;

    private boolean mLiked;

    private float mScaleXY = 1F;
    private Matrix mScaleXYMatrix;

    private float mCircleRadius = 0;
    private int mAnimAlpha = 0;
    private Paint mCirclePaint;
    private boolean mShowShining;

    public ThumbView(Context context) {
        this(context, null);
    }

    public ThumbView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mStandardThumbNormal = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mStandardShining = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);
        mStandardThumbUp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);

        mScaleXYThumbNormal = mStandardThumbNormal.copy(mStandardThumbNormal.getConfig(), false);
        mScaleXYShining = mStandardShining.copy(mStandardShining.getConfig(), false);
        mScaleXYThumbUp = mStandardThumbUp.copy(mStandardThumbUp.getConfig(), false);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#ff0000"));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(6);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterPoint = new Point(getWidth() / 2, getHeight() / 2);

        mThumbPoint = new Point(mCenterPoint.x - mScaleXYThumbUp.getWidth() / 2,
                mCenterPoint.y - mScaleXYThumbUp.getHeight() / 2);

        mShiningPoint = new Point(mThumbPoint.x + mScaleXYShining.getWidth() / 8,
                mThumbPoint.y - mScaleXYShining.getHeight() / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        Log.d(TAG, "AT_MOST: " + MeasureSpec.AT_MOST);
        Log.d(TAG, "EXACTLY: " + MeasureSpec.EXACTLY);
        Log.d(TAG, "UNSPECIFIED: " + MeasureSpec.UNSPECIFIED);

        Log.d(TAG, "widthMode: " + widthMode);
        Log.d(TAG, "heightMode: " + heightMode);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mStandardThumbNormal.getHeight() * 3 / 2, widthMode);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mStandardThumbNormal.getHeight() * 3 / 2, heightMode);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mLiked) {
            if (mShowShining) {
                canvas.drawBitmap(mScaleXYShining, mShiningPoint.x, mShiningPoint.y, mPaint);
            }
            canvas.drawBitmap(mScaleXYThumbUp, mThumbPoint.x, mThumbPoint.y, mPaint);
        } else {
            canvas.drawBitmap(mScaleXYThumbNormal, mThumbPoint.x, mThumbPoint.y, mPaint);
        }

        mCirclePaint.setAlpha(mAnimAlpha);
        if (mLiked) {
            canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCircleRadius, mCirclePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showDownAnim();
                return true;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                showUpAnim();
                postInvalidate();
                mLiked = !mLiked;
                performClick();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void showDownAnim() {

        ObjectAnimator scaleXY = ObjectAnimator.ofFloat(this, "ScaleXY",
                1, 0.9f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.play(scaleXY);
        set.start();
    }

    private void showUpAnim() {

        ObjectAnimator scaleXY = ObjectAnimator.ofFloat(this, "ScaleXY",
                0.9f, 1.1f, 1);
        ObjectAnimator circleRadius = ObjectAnimator.ofFloat(this, "CircleRadius",
                0, mStandardThumbUp.getWidth());
        ObjectAnimator animAlpha = ObjectAnimator.ofInt(this, "AnimAlpha",
                255, 0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.play(scaleXY)
                .with(circleRadius)
                .with(animAlpha);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mShowShining = mLiked;
            }
        });
        set.start();
    }

    public float getCircleRadius() {
        return mCircleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        mCircleRadius = circleRadius;
    }

    public int getAnimAlpha() {
        return mAnimAlpha;
    }

    public void setAnimAlpha(int animAlpha) {
        this.mAnimAlpha = animAlpha;
    }

    public float getScaleXY() {
        return mScaleXY;
    }

    public void setScaleXY(float scaleXY) {
        mScaleXY = scaleXY;
        mScaleXYMatrix = new Matrix();
        mScaleXYMatrix.postScale(mScaleXY, mScaleXY);

        mScaleXYThumbNormal = bitmapScale(mStandardThumbNormal, mScaleXYMatrix);
        mScaleXYThumbUp = bitmapScale(mStandardThumbUp, mScaleXYMatrix);
        mScaleXYShining = bitmapScale(mStandardShining, mScaleXYMatrix);

        postInvalidate();
    }

    private Bitmap bitmapScale(Bitmap bitmap, Matrix matrix) {
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }


    public boolean isLiked() {
        return mLiked;
    }

    public void setLiked(boolean liked) {
        mLiked = liked;
        showUpAnim();
//        postInvalidate();
    }

}
