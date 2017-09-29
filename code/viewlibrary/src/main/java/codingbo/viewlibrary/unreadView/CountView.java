package codingbo.viewlibrary.unreadView;

import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by bob
 * on 17.9.29.
 */

public class CountView extends android.support.v7.widget.AppCompatTextView {
    private static final int MIN_RADIUS = 20;
    private Paint mPaint;

    private int mCX;
    private int mCY;
    private float mFX;
    private float mFY;

    private int defaultRadius;
    private Paint mPointPaint;
    private int mCRadius;
    private int mFRadius;

    public CountView(Context context) {
        this(context, null);
    }

    public CountView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int attr) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();

        mCX = width / 2;
        mCY = height / 2;
        defaultRadius = Math.min(width, height) / 2;
        mCRadius = defaultRadius;

        mFX = mCX;
        mFY = mCY;
        mFRadius = mCRadius;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //绘制底view
        float dx = mFX - mCX;
        float dy = mFY - mCY;
        double distance = Math.hypot(dx, dy);

        if (dx == 0 && dy == 0) {
            //静止
            DrawBackgroundUtils.drawCircleBg(canvas, mPaint, getWidth(), getHeight());
        } else {
            //动态
            mCRadius = (int) (defaultRadius - distance / 10);
            if (mCRadius > MIN_RADIUS) {
                canvas.drawCircle(mCX, mCY, mCRadius, mPaint);
            } else {
                mCRadius = 0;
            }
        }

        //绘制随手指移动view
//        canvas.drawCircle(mFX, mFY, mFRadius, mPaint);
        DrawBackgroundUtils.drawCircleBg(canvas, mPaint, getWidth(), getHeight(), mFX - getWidth() / 2, mFY - getHeight() / 2);

        //绘制粘连效果
        if (distance > 0) {
            drawAdhesion(canvas, mPaint, new PointF(mCX, mCY), new PointF(mFX, mFY), mCRadius, mFRadius);
        }

        //绘制文本
        String text = getText().toString();
        TextPaint paint = getPaint();
        paint.setColor(Color.WHITE);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int txtOx = (int) (mFX - rect.width() / 2);
        int txtOy = (int) (mFY + rect.height() / 2);
        rect.offset(txtOx, txtOy);
        canvas.drawText(text, rect.left, rect.bottom, paint);
//        super.onDraw(canvas);
    }


    private void drawAdhesion(Canvas canvas, Paint paint, PointF cp, PointF fp, int cRadius, int fRadius) {
        if (cRadius <= MIN_RADIUS || fRadius <= MIN_RADIUS) {
            return;
        }
        float dY = fp.y - cp.y;
        float dX = fp.x - cp.x;
        float cX = cp.x / 2 + fp.x / 2;
        float cY = cp.y / 2 + fp.y / 2;
        double angle;
        if (dX == 0) {
            angle = 0;
        } else {
            angle = -Math.atan(dY / dX);
        }

        float c1x = (float) (cp.x - Math.sin(angle) * cRadius);
        float c1y = (float) (cp.y - Math.cos(angle) * cRadius);

        float c2x = (float) (cp.x + Math.sin(angle) * cRadius);
        float c2y = (float) (cp.y + Math.cos(angle) * cRadius);

        float f1x = (float) (fp.x - Math.sin(angle) * fRadius);
        float f1y = (float) (fp.y - Math.cos(angle) * fRadius);

        float f2x = (float) (fp.x + Math.sin(angle) * fRadius);
        float f2y = (float) (fp.y + Math.cos(angle) * fRadius);

        Path path = getPath(new PointF(cX, cY),
                new PointF(c1x, c1y),
                new PointF(c2x, c2y),
                new PointF(f1x, f1y),
                new PointF(f2x, f2y));

        canvas.drawPath(path, paint);
    }

    private Path getPath(PointF c, PointF c1, PointF c2, PointF f1, PointF f2) {
        Path path = new Path();
        path.reset();
        path.moveTo(c1.x, c1.y);
        path.cubicTo(c1.x, c1.y, c.x, c.y, f1.x, f1.y);
        path.lineTo(f2.x, f2.y);
        path.cubicTo(f2.x, f2.y, c.x, c.y, c2.x, c2.y);
        path.close();
        return path;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                mFX = x /*- getWidth() / 2*/;
                mFY = y /*- getHeight() / 2*/;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                ObjectAnimator animator = ObjectAnimator.ofObject(this, "point", new PointFEvaluator(),
                        new PointF(x, y), new PointF(mCX, mCY));
                animator.setDuration(300);
                animator.start();
                break;
            default:
                break;
        }
        return true;
    }

    public void setPoint(PointF pointF) {
        mFX = pointF.x;
        mFY = pointF.y;
        invalidate();
    }

    public PointF getPoint() {
        return new PointF(mFX, mFY);
    }
}
