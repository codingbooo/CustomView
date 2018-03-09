package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bob
 * on 17.9.8.
 */

public class AnimatorView extends View {

    private Paint mPaint;
    private int mProgress;


    public AnimatorView(Context context) {
        super(context);
    }

    public AnimatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Rect mRect;

    {
        mProgress = 0;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect(20, 20, mProgress, 40);
    }


    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setStartAnimator(int progress) {
        mProgress = progress;
        invalidate();
    }

    public int getStartAnimator() {
        return mProgress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
        int margin = 500;
        int l = margin;
        int t = margin;
        int r = mProgress + margin;
        int b = margin;

        canvas.drawLine(l, t, r, b, mPaint);
        String p = mProgress + "%";
        Rect rect = new Rect();
        mPaint.setTextSize(40);
        mPaint.getTextBounds(p, 0, p.length(), rect);
        int tY = b + rect.height() / 2;
        int tX = mProgress >= 0 ? r + 20 : r - 20 - rect.width();
        canvas.drawText(p, tX, tY, mPaint);
    }
}
