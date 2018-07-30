package codingbo.viewstudy.thumb;

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
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import codingbo.viewstudy.R;

/**
 * Created by bob
 * on 18.3.6.
 */

public class ThumbView2 extends View {
    private static final String TAG = "ThumbView2";
    public static final int ANIM_DURATION = 300;

    private Bitmap mThumbNormal;
    private Bitmap mShining;
    private Bitmap mThumbUp;
    private Point mCenterPoint;
    private Paint mIconPaint;

    private Point mThumbPoint;
    private Point mShiningPoint;


    private boolean liked = false;
    private boolean mShowShining = false;

    private float mThumbScale = 1;
    //圈圈动画属性
    private int mCircleAlpha = 0;
    private float mCircleRadius = 0;
    private Paint mCirclePaint;
    private Path mCirclePath;
    private TextPaint mTextPaint;

    //text
    private String[] mTextNums;
    private int mCount;

    private Rect mTextRect;
    private Point mTextSmallerPoint;
    private Point mTextBiggerPoint;
    private int mSmallerY;
    private int mBiggerY;
    private int mTextOffsetY = 0;

    public ThumbView2(Context context) {
        this(context, null);
    }

    public ThumbView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mThumbNormal = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mShining = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);
        mThumbUp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);

        mIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //圆圈设置
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#ff0000"));
//        mCirclePaint.setTextSize(20f);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(6);

        mCirclePath = new Path();


        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mThumbUp.getHeight());
        mTextPaint.setColor(Color.parseColor("#cccccc"));
        mCount = 123456;
        mTextNums = calculator(mCount);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterPoint = new Point(getWidth() / 2, getHeight() / 2);

        int thumbX = mCenterPoint.x - mThumbUp.getWidth() / 2;
        int thumbY = mCenterPoint.y - mThumbUp.getHeight() / 2 /*+ mShining.getHeight() / 4*/;

        int shiningX = thumbX + mShining.getWidth() / 8;
        int shiningY = thumbY - mShining.getHeight() / 2;

        mThumbPoint = new Point(thumbX, thumbY);
        mShiningPoint = new Point(shiningX, shiningY);


        mTextRect = new Rect();
        mTextPaint.getTextBounds("aaaa", 0, 2, mTextRect);

        mTextSmallerPoint = new Point(mCenterPoint.x + mThumbUp.getWidth(),
                mCenterPoint.y + mTextRect.height() / 2);
        mTextBiggerPoint = new Point(mTextSmallerPoint.x, mTextSmallerPoint.y + mTextRect.height() * 3 / 2);

        mSmallerY = mTextSmallerPoint.y;
        mBiggerY = mTextBiggerPoint.y;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "action down");
                showDownAnim();
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "action move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "action up");

                liked = !liked;

                showUpAnim();
                performClick();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (liked) {
            if (mShowShining) {
                canvas.drawBitmap(mShining, mShiningPoint.x, mShiningPoint.y, mIconPaint);
            }
            canvas.drawBitmap(mThumbUp, mThumbPoint.x, mThumbPoint.y, mIconPaint);
        } else {
            canvas.drawBitmap(mThumbNormal, mThumbPoint.x, mThumbPoint.y, mIconPaint);
        }

        if (liked) {
            mCirclePaint.setAlpha(mCircleAlpha);
            canvas.drawPath(mCirclePath, mCirclePaint);
        }

        mTextPaint.setAlpha(255);
        String num0 = mTextNums[0];
        float gap = mTextPaint.measureText(num0)/* / num0.length()*/;
        canvas.drawText(num0, mTextSmallerPoint.x, mTextSmallerPoint.y, mTextPaint);

        if (liked) {
            mTextPaint.setAlpha(mCircleAlpha);
        } else {
            mTextPaint.setAlpha(255 - mCircleAlpha);
        }

        canvas.drawText(mTextNums[1], mTextSmallerPoint.x /*+ rect.width() */ + gap, mSmallerY, mTextPaint);

        if (!liked) {
            mTextPaint.setAlpha(mCircleAlpha);
        } else {
            mTextPaint.setAlpha(255 - mCircleAlpha);
        }
        canvas.drawText(mTextNums[2], mTextBiggerPoint.x /*+ rect.width() */ + gap, mBiggerY, mTextPaint);
    }

    private void showDownAnim() {
        ObjectAnimator scale = ObjectAnimator.ofFloat(this, "ThumbScale",
                1, 0.9f)
                .setDuration(ANIM_DURATION);
        scale.start();
    }

    private void showUpAnim() {
        ObjectAnimator scale = ObjectAnimator.ofFloat(this, "ThumbScale",
                0.9f, 1.1f, 1)
                .setDuration(ANIM_DURATION);
        scale.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mShowShining = liked;
            }
        });

        ObjectAnimator circleAnim = ObjectAnimator
                .ofFloat(this, "circleRadius",
                        mThumbNormal.getWidth() / 10, mThumbNormal.getWidth() / 2 * 1.4f)
                .setDuration(ANIM_DURATION);

        ObjectAnimator circleAlpha = ObjectAnimator.ofInt(this, "circleAlpha",
                255, 0)
                .setDuration(ANIM_DURATION);

        ObjectAnimator offsetY;
        if (liked) {
            offsetY = ObjectAnimator.ofInt(this, "TextOffsetY",
                    0, mTextRect.height() * 3 / 2);
        } else {
            offsetY = ObjectAnimator.ofInt(this, "TextOffsetY",
                    mTextRect.height() * 3 / 2, 0);
        }
        offsetY.setDuration(ANIM_DURATION);

        AnimatorSet set = new AnimatorSet();
        set.play(circleAnim)
                .with(circleAlpha)
                .with(offsetY)
                .with(scale);
        set.start();
    }

    public float getThumbScale() {
        return mThumbScale;
    }

    public void setThumbScale(float thumbScale) {
        mThumbScale = thumbScale;
        Matrix matrix = new Matrix();
        matrix.postScale(mThumbScale, mThumbScale);
        mThumbUp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mThumbUp = Bitmap.createBitmap(mThumbUp, 0, 0, mThumbUp.getWidth(), mThumbUp.getHeight(),
                matrix, true);

        mThumbNormal = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mThumbNormal = Bitmap.createBitmap(mThumbNormal, 0, 0, mThumbNormal.getWidth(), mThumbNormal.getHeight(),
                matrix, true);

        mShining = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);
        mShining = Bitmap.createBitmap(mShining, 0, 0, mShining.getWidth(), mShining.getHeight(),
                matrix, true);
        postInvalidate();
    }

    public int getCircleAlpha() {
        return mCircleAlpha;
    }

    public void setCircleAlpha(int circleAlpha) {
        mCircleAlpha = circleAlpha;
    }

    public void setCircleRadius(float radius) {
        mCircleRadius = radius;
        mCirclePath.reset();
        mCirclePath.addCircle(mCenterPoint.x, mCenterPoint.y,
                mCircleRadius, Path.Direction.CW);
        invalidate();
    }

    public float getCircleRadius() {
        return mCircleRadius;
    }

    public int getTextOffsetY() {
        return mTextOffsetY;
    }

    public void setTextOffsetY(int textOffsetY) {
        Log.d(TAG, "textOffsetY: " + textOffsetY);
        mTextOffsetY = textOffsetY;

        mSmallerY = mTextSmallerPoint.y - textOffsetY;
        mBiggerY = mTextBiggerPoint.y - textOffsetY;

        Log.d(TAG, "mSmallerY: " + mSmallerY);
        Log.d(TAG, "mBiggerY: " + mBiggerY);
        postInvalidate();
    }

    private String[] calculator(int count) {
        StringBuilder num0 = new StringBuilder();
        String num1 = String.valueOf(count);
        String num2 = String.valueOf(count + 1);

        if (num2.length() > num1.length()) {
            return getNumArray(num0.toString(), num1, num2);
        }
        int length = num1.length();

        for (int i = 0; i < length; i++) {
            if (num1.charAt(i) == num2.charAt(i)) {
                num0.append(num1.charAt(i));
            } else {
                num1 = num1.substring(i, num1.length());
                num2 = num2.substring(i, num2.length());
                break;
            }
        }
        return getNumArray(num0.toString(), num1, num2);
    }

    private String[] getNumArray(String num0, String num1, String num2) {
        return new String[]{num0, num1, num2};
    }
}
