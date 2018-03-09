package codingbo.customviewstudy.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by bob
 * on 17.9.8.
 */

public class CircleProgressView extends View implements ProgressView {

    private int mProgress = 100;
    private int totalCount;
    private Paint mPaint;
    private RectF mRect;
    private TextPaint mTextPaint;

    private final int MARGIN = 20;
    private Rect mTextRect;
    private ObjectAnimator mAnimator;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#303F9F"));
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.parseColor("#aaaaaa"));
        mRect = new RectF();
        mTextRect = new Rect();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        mRect.left = MARGIN;
        mRect.top = MARGIN;
        mRect.right = width - MARGIN;
        mRect.bottom = height - MARGIN;

        float startAngle = 90;
        float currentAngle = 360F / totalCount * mProgress;

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        int ringWidth = 30;
        mPaint.setStrokeWidth(ringWidth);
        Log.d("haha", "onDraw: " + currentAngle);
        mPaint.setColor(Color.parseColor("#303F9F"));
        canvas.drawArc(mRect, startAngle, currentAngle, false, mPaint);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1);
//        canvas.drawRect(mRect, mPaint);
//        canvas.drawLine(MARGIN, mRect.width() / 2 + MARGIN, mRect.right, mRect.width() / 2 + MARGIN, mPaint);
//        canvas.drawLine(mRect.width() / 2 + MARGIN, mRect.top, mRect.width() / 2 + MARGIN, mRect.bottom, mPaint);


        int textSize = (int) ((mRect.width() - ringWidth) / 4);
//        int textSize = 100;
        mTextPaint.setTextSize(textSize);
        mTextPaint.setStrokeWidth(20);
//        DecimalFormat decimalFormat = new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//        String p = decimalFormat.format((float) mProgress / totalCount * 100) + "%";//format 返回的是字符串

        String p = (int) ((float) mProgress / totalCount * 100) + "%";//format 返回的是字符串


        mTextPaint.getTextBounds(p, 0, p.length(), mTextRect);
        float x = mRect.width() / 2 + MARGIN - mTextRect.width() / 2;
        float y = mRect.height() / 2 + MARGIN + mTextRect.height() / 2;

        canvas.drawText(p, x, y, mTextPaint);
    }

    @Override
    public void setMax(int max) {
        totalCount = max;
    }

    @Override
    public void setProgress(int progress) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        mAnimator = ObjectAnimator.ofInt(this, "startAnimator", progress);
        mAnimator.start();
    }

    public void setStartAnimator(int progress) {
        mProgress = progress;
        invalidate();
    }

    public int getStartAnimator() {
        return getProgress();
    }

    @Override
    public int getProgress() {
        return mProgress;
    }
}
