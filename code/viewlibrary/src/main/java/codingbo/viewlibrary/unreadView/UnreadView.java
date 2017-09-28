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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿QQ未读小红点
 */

public class UnreadView extends View {
    private static final String TAG = "UnreadView";
    /**
     * 手指距离View的距离
     */
    private double mDistance;
    //view
    private Paint mPaint;
    //center circle
    private int mCX;
    private int mCY;
    private int mCRadius;
    //finger circle
    private float mFX;
    private float mFY;
    private int mFRadius;

    //text
    private TextPaint mTextPaint;
    private float PADDING = 10;
    private int unreadCount = 99;
    private Rect mTextBounds;
    private int textSize = 20;
    private Path mPath;
    private int defaultRadius;
    private Paint mPointPaint;


    public UnreadView(Context context) {
        this(context, null);
    }

    public UnreadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize(attrs, defStyleAttr);
    }

    private void initialize(AttributeSet attrs, int attr) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(50);
        mTextBounds = new Rect();

        mPath = new Path();

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStrokeWidth(10);
        mPointPaint.setStrokeCap(Paint.Cap.ROUND);


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

        mFX = mCX + 300;
        mFY = mCY - 300;
        mFRadius = mCRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {


        float dY = mFY - mCY;
        float dX = mFX - mCX;
        mDistance = Math.hypot(dX, dY);

        mCRadius = (int) (defaultRadius - mDistance / 10);
        if (mCRadius <= 0) {
            mCRadius = 1;
        }


        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);
        //draw center circle
        canvas.drawCircle(mCX, mCY, mCRadius, mPaint);

        //draw finger circle
        canvas.drawCircle(mFX, mFY, mFRadius, mPaint);

        float cX = mCX / 2 + mFX / 2;
        float cY = mCY / 2 + mFY / 2;

        mode1(canvas, dX, dY, cX, cY);
//        mode2(canvas, cX, cY);


    }

    private void mode2(Canvas canvas, float cX, float cY) {
        //        List<PointF> cPointList = getPointOfContact(new PointF(mCX, mCY), mCRadius, new PointF(mFX, mFY));
        List<PointF> fPointList = getPointOfContact(new PointF(mFX, mFY), mFRadius, new PointF(mCX, mCY));

        canvas.drawPoint(cX, cY, mPointPaint);
        if (fPointList != null) {
            for (PointF p : fPointList) {
                canvas.drawPoint(p.x, p.y, mPointPaint);
            }
        }


//        if (cPointList != null) {
//            for (PointF p : cPointList) {
//                canvas.drawPoint(p.x, p.y, mPointPaint);
//            }
//        }

//        if (cPointList != null && cPointList.size() == 2
//                && fPointList != null && fPointList.size() == 2) {
//
//            PointF c1 = cPointList.get(0);
//            PointF c2 = cPointList.get(1);
//            PointF f1 = fPointList.get(0);
//            PointF f2 = fPointList.get(1);
//
//            drawAdhesion(canvas, new PointF(cX, cY), c1, c2, f2, f1);
//        }
    }

    private void mode1(Canvas canvas, float dX, float dY, float cX, float cY) {
        double angle;
        if (dX == 0) {
            angle = 0;
        } else {
            angle = -Math.atan(dY / dX);
        }

        float c1x = (float) (mCX - Math.sin(angle) * mCRadius);
        float c1y = (float) (mCY - Math.cos(angle) * mCRadius);

        float c2x = (float) (mCX + Math.sin(angle) * mCRadius);
        float c2y = (float) (mCY + Math.cos(angle) * mCRadius);

        float f1x = (float) (mFX - Math.sin(angle) * mFRadius);
        float f1y = (float) (mFY - Math.cos(angle) * mFRadius);

        float f2x = (float) (mFX + Math.sin(angle) * mFRadius);
        float f2y = (float) (mFY + Math.cos(angle) * mFRadius);


        drawAdhesion(canvas, new PointF(cX, cY),
                new PointF(c1x, c1y),
                new PointF(c2x, c2y),
                new PointF(f1x, f1y),
                new PointF(f2x, f2y));
    }

    private void drawAdhesion(Canvas canvas, PointF c, PointF c1, PointF c2, PointF f1, PointF f2) {
        mPaint.setColor(Color.RED);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPath.reset();
        mPath.moveTo(c1.x, c1.y);
        mPath.cubicTo(c1.x, c1.y, c.x, c.y, f1.x, f1.y);
        mPath.lineTo(f2.x, f2.y);
        mPath.cubicTo(f2.x, f2.y, c.x, c.y, c2.x, c2.y);
        mPath.close();
        canvas.drawPath(mPath, mPaint);


//        canvas.drawPoint(c.x, c.y, mPointPaint);
//        canvas.drawPoint(c1.x, c1.y, mPointPaint);
//        canvas.drawPoint(c2.x, c2.y, mPointPaint);
//        canvas.drawPoint(f1.x, f1.y, mPointPaint);
//        canvas.drawPoint(f2.x, f2.y, mPointPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                mFX = x;
                mFY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

//                mFX = x;
//                mFY = y;
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


    /**
     * 根据圆外一点 求与圆的两个切点
     *
     * @param circlePoint 圆心
     * @param radius      半径
     * @param outPoint    圆外一点
     * @return 切点  2个切点 在圆外 1个切点在圆上  null为不存在(圆内)
     */
    public List<PointF> getPointOfContact(PointF circlePoint, float radius, PointF outPoint) {
        if (circlePoint == null || outPoint == null || radius <= 0) {
            return null;
        }

        List<PointF> result = new ArrayList<>();

        float dY = outPoint.y - circlePoint.y;
        float dX = outPoint.x - circlePoint.y;
        double distance = Math.hypot(dX, dY);
        if (radius > distance) {
            return result;
        } else if (radius == distance) {
            //在圆上
            result.add(outPoint);
            return result;
        }


        PointF p0 = new PointF();
        PointF p1 = new PointF();

        // 两点的斜率

        double k = dY / dX;

        /*
         * 象限
         */
        int quadrant = getQuadrant(circlePoint, outPoint);
        /*
            两个角度

            切线 圆心 连线夹角 a1  (0, 90)

            连线 和水平的夹角 a2

            计算偏移量

            a1 - a2
            a1 + a2

         */

        double a1 = Math.acos(radius / distance);
        double a2 = Math.atan(k);

        switch (quadrant) {
            case 1:
                Log.d(TAG, "quadrant: " + 1);
                break;
            case 2:
                a2 += Math.PI;
                Log.d(TAG, "quadrant: " + 2);
                break;
            case 3:
                a2 += Math.PI;
                Log.d(TAG, "quadrant: " + 3);
                break;
            case 4:
                a2 += Math.PI * 2;
                Log.d(TAG, "quadrant: " + 4);
                break;
        }

        p0.x = (float) (circlePoint.x + Math.cos(a2 - a1) * radius);
        p0.y = (float) (circlePoint.y + Math.sin(a2 - a1) * radius);
        result.add(p0);

        p1.x = (float) (circlePoint.x - Math.cos((a2 + a1) + Math.PI) * radius);
        p1.y = (float) (circlePoint.y - Math.sin((a2 + a1) + Math.PI) * radius);
        result.add(p1);

        Log.d(TAG, "a1 : " + (a1 / Math.PI * 180));
        Log.d(TAG, "a2 : " + (a2 / Math.PI * 180));

        return result;
    }

    private int getQuadrant(PointF cp, PointF op) {
        if (op.x >= cp.x && op.y >= cp.y) {
            return 1;
        } else if (op.x < cp.x && op.y >= cp.y) {
            return 2;
        } else if (op.x < cp.x && op.y < cp.y) {
            return 3;
        } else if (op.x >= cp.x && op.y < cp.y) {
            return 4;
        }
        return 1;
    }
}
