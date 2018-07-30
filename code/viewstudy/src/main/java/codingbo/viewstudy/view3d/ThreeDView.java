package codingbo.viewstudy.view3d;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import codingbo.viewstudy.R;

/**
 * Created by bob
 * on 18.3.19.
 */

public class ThreeDView extends View {
    private static final String TAG = "ThreeDView";

    private Camera mCamera;
    private Bitmap mBitmap;
    private PointF mPointF;
    private Paint mPaint;
    private float mDx;
    private float mDy;
    private float mOldx;
    private float mOldy;
    private float mX;
    private float mY;

    public ThreeDView(Context context) {
        this(context, null);
    }

    public ThreeDView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeDView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mCamera = new Camera();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image2);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointF = new PointF();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPointF = new PointF(getWidth() / 2 - mBitmap.getWidth() / 2, getHeight() / 2 - mBitmap.getHeight() / 2);
//        mPointF = new PointF(getWidth() / 2, getHeight() / 2);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        mCamera.save();
        mCamera.rotate(mDx / 10, mDy / 10, 0);

        canvas.translate(getWidth() / 2, getHeight() / 2);
        mCamera.applyToCanvas(canvas);

        canvas.translate(-getWidth() / 2, -getHeight() / 2);
        mCamera.restore();
        canvas.drawBitmap(mBitmap, mPointF.x, mPointF.y, mPaint);

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mX = event.getX();
        mY = event.getY();

        double a = mX - getWidth() / 2;
        double b = mY - getHeight() / 2;
        double c = Math.sqrt(a * a + b * b);

        double cosB = (a * a + c * c - b * b) / (2 * a * c);
        double sinB = Math.sqrt(1 - cosB * cosB);

        if (b < 0) {
            sinB = -sinB;
        }
        //x : -正弦
        float dx = -(float) (sinB * c);
        //y : 余弦
        float dy = (float) (cosB * c);


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                anim(mOldx, mOldy, dx, dy);
                return true;
            case MotionEvent.ACTION_MOVE:
                mDx = dx;
                mDy = dy;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                /*mOldx =*/ mDx = dx;
                /*mOldy =*/ mDy = dy;

                anim(mDx, mDy, 0, 0);
                performClick();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void anim(float fromX, float fromY, float toX, float toY) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(this, "dx", fromX, toX),
                ObjectAnimator.ofFloat(this, "dy", fromY, toY)
        );
        set.setDuration(300);
        set.start();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    public float getDx() {
        return mDx;
    }

    public void setDx(float dx) {
        mDx = dx;
        postInvalidate();
    }

    public float getDy() {
        return mDy;
    }

    public void setDy(float dy) {
        mDy = dy;
        postInvalidate();
    }
}
