package codingbo.customviewstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import codingbo.customviewstudy.R;


/**
 * Created by aishua on 2017/5/16.
 */

public class JiansheCirView extends ViewGroup {
    private static final String TAG = "JiansheCirView-";
    private Paint mPaint;
    private int x_coordinate;
    private int y_coordinate;
    private Context mContext;

    //    当每秒的移动达到该值的时候则认为是快速移动
    private static final int FLINGABLE_VALUE = 300;
    private static final int NOCLICK_VALUE = 3;

//    private int mRadius;

    public JiansheCirView(Context context) {
        super(context);
    }

    public JiansheCirView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        initAttrs(context, attrs);


    }

    public JiansheCirView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.JiansheCirView);
        x_coordinate = (int) mTypedArray.getDimension(R.styleable.JiansheCirView_x_coordinate, 30);
        y_coordinate = (int) mTypedArray.getDimension(R.styleable.JiansheCirView_y_coordinate, 30);
//        mRadius = (int) mTypedArray.getDimension(R.styleable.JiansheCirView_radius, getResources().getDimension(R.dimen.jianshe_radius));
        mTypedArray.recycle();

    }

    private void init() {

        setClickable(true);
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setColor(Color.RED);

    }

    public void setItemMenu(int[] ImagViewID, final String[] texts) {

        View view;
        String text;
        if (ImagViewID == null && texts == null) {

            throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
        }
        for (int i = 0; i < 6; i++) {
            final int j = i;
            view = View.inflate(mContext, R.layout.item_jianshe_view_firstchild, null);
            ImageView mneueimag = (ImageView) view.findViewById(R.id.img_menue);
            TextView menuetext = (TextView) view.findViewById(R.id.tv_menue);
            mneueimag.setBackgroundResource(ImagViewID[i]);
            menuetext.setText(texts[i]);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemOnclickListener.onclick(v, j);
                }
            });
            addView(view);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childcout = getChildCount();
        for (int i = 0; i < childcout; i++) {

            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(x_coordinate * 2, x_coordinate * 2);
//        LogUtils.v(TAG + "measureWidth--->" + measureWidth(widthMeasureSpec) + "--measureHeight--->" + measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        return size;
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        return size;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

//        LogUtils.v(TAG+"--onLayout()");

        int count = getChildCount();
        int divisionAngle = 360 / count;
        int x;
        int y;
        for (int i = 0; i < count; i++) {

            View view = getChildAt(i);
            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();
            int hypotenuse = (int) Math.sqrt(viewHeight / 2 * viewHeight / 2 + viewWidth / 2 * viewWidth / 2);

//          LogUtils.v(TAG + "view--->" + viewWidth);

            startAngle %= 360;
//            LogUtils.v(TAG + startAngle);
            x = (int) (x_coordinate - hypotenuse + (x_coordinate - hypotenuse) * Math.sin(Math.toRadians(startAngle)));
            y = (int) (x_coordinate - hypotenuse - (x_coordinate - hypotenuse) * Math.cos(Math.toRadians(startAngle)));

//            LogUtils.v(TAG + "x-->" + x + "--y--->" + y);

            view.layout(x, y, x + hypotenuse * 2, y + hypotenuse * 2);
//            view.layout(x - viewWidth / 2, y- viewHeight /2, viewWidth + x, y + viewHeight);
//            LinearLayout
            startAngle += divisionAngle;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawColor(getResources().getColor(R.color.blank));
//        LogUtils.v(TAG+"---onDraw()");
        canvas.drawCircle(x_coordinate, x_coordinate, x_coordinate, mPaint);

    }

    float startAngle = 0;
    private float mLastX = 0;
    private float mLastY = 0;
    private long mDownTime;
    private int mTmpAngle;
    private AutoFlingRunnable autoFlingRunnable;
    private boolean isFling = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;
                if (isFling) {
                    removeCallbacks(autoFlingRunnable);
                    isFling = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                float start = getAngle(mLastX, mLastY);
                float end = getAngle(x, y);
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {

                    startAngle += end - start;
                    mTmpAngle += end - start;
                } else {

                    startAngle += start - end;
                    mTmpAngle += start - end;
                }
//                LogUtils.v(TAG + "startAngle-->" + startAngle + "---start--->" + start + "---end--->" + end);
                requestLayout();
                mLastX = x;
                mLastY = y;
//                LogUtils.v(TAG + "mTmpAngle---" + mTmpAngle);
                // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击

                break;

            case MotionEvent.ACTION_UP:

//                每秒钟旋转的角度
                float anglePerSecond = mTmpAngle * 1000 / (System.currentTimeMillis() - mDownTime);
//                LogUtils.v(TAG + "anglePerSecond---" + anglePerSecond);
                if (Math.abs(anglePerSecond) > FLINGABLE_VALUE) {
//                    执行快速移动
                    post(autoFlingRunnable = new AutoFlingRunnable(anglePerSecond));
                }
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {

                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private float getAngle(float xTouch, float yTouch) {

        double x = xTouch - x_coordinate;
        double y = yTouch - x_coordinate;
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    private int getQuadrant(float x, float y) {

        int tmpX = (int) (x - x_coordinate);
        int tmpY = (int) (y - x_coordinate);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }

    }

    public class AutoFlingRunnable implements Runnable {
        private float velocity;

        public AutoFlingRunnable(float velocity) {

            this.velocity = velocity;
        }

        @Override
        public void run() {

            // 如果小于20,则停止
            if (Math.abs(velocity) < 10) {
                isFling = false;
                return;
            }
            isFling = true;
            startAngle += velocity / 30;
            velocity /= 1.06;
//            LogUtils.v(TAG + "velocity--->" + velocity);
            postDelayed(this, 30);
            requestLayout();

        }
    }

    public ItemOnclickListener itemOnclickListener;

    public void setOnItemOnclickListener(ItemOnclickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

    public interface ItemOnclickListener {

        public void onclick(View view, int pos);
    }
}


