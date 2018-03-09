package codingbo.customviewstudy.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import codingbo.customviewstudy.R;

import static java.lang.Double.NaN;

/**
 * Created by bob
 * on 17.9.13.
 */

public class JianHangMenuView extends ViewGroup {
    private static final String TAG = "JianHangMenuView";
    private int[] mRes;
    private String[] mTitles;
    private double mX;
    private double mY;
    public static final int MARGIN = 100;
    public double deflectionAngle = 0;
    private int mCX;
    private int mCY;
    private double mAngle;
    private int mRadius;

    public JianHangMenuView(Context context) {
        this(context, null);
    }

    public JianHangMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JianHangMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

    }

    public void setData(int[] res, String[] titles) {
        if (res == null || titles == null) {
            throw new RuntimeException("res titles can not be null");
        }
        mRes = res;
        mTitles = titles;

        int count = Math.min(res.length, titles.length);

        for (int i = 0; i < count; i++) {
            View view = View.inflate(getContext(), R.layout.item_jianshe_view_firstchild, null);
            ImageView mneueimag = (ImageView) view.findViewById(R.id.img_menue);
            TextView menuetext = (TextView) view.findViewById(R.id.tv_menue);
            mneueimag.setBackgroundResource(mRes[i]);
            menuetext.setText(mTitles[i]);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ");
                }
            });
            addView(view);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }


      /*  int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int min = Math.min(width, height);
//        setMeasuredDimension(min, min);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int childWidth = 150;
        int childHeight = 200;
        int childMode = MeasureSpec.EXACTLY;

        int childWidthMeasure = MeasureSpec.makeMeasureSpec(childWidth, childMode);
        int childHeightMeasure = MeasureSpec.makeMeasureSpec(childHeight, childMode);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
//            getChildAt(i).measure(childMeasure, childMeasure);
//            measureChild(getChildAt(i), childWidthMeasure, childHeightMeasure);
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }*/

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mCX = (r - l) / 2;
        mCY = (b - t) / 2;

        mRadius = (Math.min(mCX, mCY) - MARGIN);
        int count = getChildCount();

        float angle = 360 / count;

        for (int i = 0; i < count; i++) {
            int x;
            int y;
            double cAngle = angle * i + deflectionAngle % 360;
            mX = mRadius * Math.sin(Math.toRadians(cAngle));
            x = (int) mX + mCX;
            mY = -mRadius * Math.cos(Math.toRadians(cAngle));
            y = (int) mY + mCY;
            View child = getChildAt(i);
//            int height1 =
//            int width = 100;
//            int height = 150;
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            int cL = x - width / 2;
            int cT = y - height / 2;
            int cR = x + width / 2;
            int cB = y + height / 2;
            child.layout(cL, cT, cR, cB);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mCX, mCY, mRadius, paint);
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        float x = event.getX();
        float y = event.getY();
        double angle;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                lastTime = System.currentTimeMillis();
                lastAngle = getAngle(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                angle = getAngle(x, y);
                deflectionAngle += (angle - lastAngle);
                requestLayout();
                lastAngle = angle;
                lastX = x;
                lastY = y;
                lastTime = System.currentTimeMillis();

                Log.d(TAG, "ACTION_MOVE: " + "X:" + x + "Y:" + y);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP: " + "X:" + x + "Y:" + y);
                double hypot = Math.hypot(x - lastX, y - lastY);
                Log.d(TAG, "hypot: " + hypot);
                if (hypot < 10) {
                    break;
                }


                lastUpAngle = deflectionAngle;
                angle = getAngle(x, y);
                Log.d(TAG, "angle: " + angle);
                Log.d(TAG, "lastUpAngle: " + lastUpAngle);

                //计算此时的速度 角度/s

                long timeMillis = System.currentTimeMillis();
                Log.d(TAG, "lastTime: " + lastTime);
                Log.d(TAG, "timeMillis: " + timeMillis);

                double v = (angle - lastUpAngle) / (timeMillis - lastTime <= 0 ? 1 : (timeMillis - lastTime));
                Log.d(TAG, "v: " + v);
                if (Math.abs(v) > 360) {
                    if (v > 0) {
                        v = 360;
                    } else {
                        v = -360;
                    }

                }
                v = v > 360 ? 360 : v;


                //设置速度损耗 180 角度/s

                int k = 180;
                Log.d(TAG, "k: " + k);

                // 计算还能转多少 多长时间

                long t = (long) (Math.abs(v) / k);
                Log.d(TAG, "t: " + t);
//                long t = 1000;

                int a = (int) v * (int) t / 2;

                Log.d(TAG, "a: " + a);
                if (t > 0) {

                    ObjectAnimator animator = ObjectAnimator.ofInt(this, "angle", 0, a)
                            .setDuration((long) t * 1000);
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.start();
                }

//                ObjectAnimator animator = ObjectAnimator.ofFloat(this, "angle", 90)
//                        .setDuration(1000);
//                animator.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        initAngle();
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        initAngle();
//                    }
//
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                animator.setInterpolator(new DecelerateInterpolator());
//                animator.start();


//                lastX = 0;
//                lastY = 0;
//                lastAngle = 0;
//                lastTime = 0;
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void initAngle() {
        angle = 0;
    }

    float lastX;
    float lastY;
    double lastAngle;
    long lastTime;


    float angle;
    double lastUpAngle;

    public void setAngle(int angle) {
        Log.d(TAG, "setAngle: " + angle);
        this.angle = angle;
        deflectionAngle = lastUpAngle + angle;
        requestLayout();
    }

    public float getAngle() {
        return angle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private double getAngle(float x, float y) {
        float a = x - mCX;
        float b = y - mCY;
        mAngle = Math.asin(b / Math.hypot(a, b)) * 180 / Math.PI;
        return a > 0 ? mAngle : -mAngle;
    }
}
