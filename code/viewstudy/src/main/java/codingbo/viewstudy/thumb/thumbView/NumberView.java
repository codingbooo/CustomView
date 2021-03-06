package codingbo.viewstudy.thumb.thumbView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by bob
 * on 18.3.7.
 */

public class NumberView extends View {
    private static final String TAG = "NumberView";
    private int mCount = 99;
    private String[] mNumArray;
    private String mOldStrNumber;
    private String mNewStrNumber;

    private int mGap = 1;
    private TextPaint mTextPaint;
    private Point mCPoint;//center point
    private Point mOldNumPoint;
    private Point mNewNumPoint;

    private boolean showNew;

    private int mTextOffsetY;

    private int mTextAlpha = 0;
    private Rect mRect;

    public NumberView(Context context) {
        this(context, null);
    }

    public NumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#cccccc"));
        mTextPaint.setTextSize(60);

        mCPoint = new Point();

        mOldNumPoint = new Point();

        mNewNumPoint = new Point();

        mRect = new Rect();

        setCount(false, 0);
        getPoint();

        setOnClickListener(v -> {
            showNew = !showNew;
            if (showNew) {
                animUp();
            } else {
                animDown();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: ");
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        getPoint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    mRect.width() + getPaddingLeft() + getPaddingRight(), widthMode);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    mRect.height() + getPaddingTop() + getPaddingBottom(), heightMode);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
        float width = mTextPaint.measureText(mNumArray[0]);
        mTextPaint.setAlpha(255);
        canvas.drawText(mNumArray[0], mOldNumPoint.x, mOldNumPoint.y, mTextPaint);
        mTextPaint.setAlpha(255 - mTextAlpha);
        canvas.drawText(mNumArray[1], mOldNumPoint.x + width, mOldNumPoint.y - mTextOffsetY, mTextPaint);
        mTextPaint.setAlpha(mTextAlpha);
        canvas.drawText(mNumArray[2], mNewNumPoint.x + width, mNewNumPoint.y - mTextOffsetY, mTextPaint);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                animDown();
//                return true;
//            case MotionEvent.ACTION_MOVE:
//
//                break;
//            case MotionEvent.ACTION_UP:
//                animUp();
//                postInvalidate();
////                mLiked = !mLiked;
//                performClick();
//                return true;
//            default:
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    @Override
//    public boolean performClick() {
//        return super.performClick();
//    }

    public Rect getRect() {
        return mRect;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(boolean isBig, int count) {
        showNew = isBig;

        mCount = showNew ? count - 1 : count;

        mOldStrNumber = String.valueOf(mCount);
        mNewStrNumber = String.valueOf(mCount + mGap);
        mNumArray = NumbUtils.calculator(mCount, mGap);

        mTextPaint.getTextBounds(mNewStrNumber, 0, mNewStrNumber.length(), mRect);

        if (showNew) {
            animUp();
        }/* else {
            setTextOffsetY(0);
        }*/

        requestLayout();

        postInvalidate();
    }

    private void getPoint() {
        Log.d(TAG, "getPoint: ");

        mTextPaint.getTextBounds(mNewStrNumber, 0, mNewStrNumber.length(), mRect);

//        float width = mTextPaint.measureText(mOldStrNumber);

        mCPoint.set(getWidth() / 2, getHeight() / 2);
        mOldNumPoint.set(mCPoint.x - mRect.width() / 2, mCPoint.y + mRect.height() / 2);
        mNewNumPoint.set(mOldNumPoint.x, mOldNumPoint.y + mRect.height());
    }

    public void animUp() {
        ObjectAnimator animUp = ObjectAnimator.ofInt(this, "TextOffsetY",
                0, mRect.height());
        ObjectAnimator alpha = ObjectAnimator.ofInt(this, "TextAlpha",
                0, 255);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(300)
                .play(animUp)
                .with(alpha);
        set.start();

    }

    public void animDown() {
        ObjectAnimator animDown = ObjectAnimator.ofInt(this, "TextOffsetY",
                mRect.height(), 0);
        ObjectAnimator alpha = ObjectAnimator.ofInt(this, "TextAlpha",
                255, 0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(300)
                .play(animDown)
                .with(alpha);
        set.start();
    }

    public int getTextAlpha() {
        return mTextAlpha;
    }

    public void setTextAlpha(int textAlpha) {
        mTextAlpha = textAlpha;
        postInvalidate();
    }


    public int getTextOffsetY() {
        return mTextOffsetY;
    }

    public void setTextOffsetY(int textOffsetY) {
        this.mTextOffsetY = textOffsetY;
        postInvalidate();
//        invalidate();
    }

    public int getGap() {
        return mGap;
    }

    public void setGap(int gap) {
        mGap = gap;
    }
}
