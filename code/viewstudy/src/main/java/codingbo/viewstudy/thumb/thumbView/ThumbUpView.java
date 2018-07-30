package codingbo.viewstudy.thumb.thumbView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by bob
 * on 18.3.12.
 */

public class ThumbUpView extends LinearLayout {
    private static final String TAG = "ThumbUpView";

    public ThumbUpView(Context context) {
        this(context, null);
    }

    public ThumbUpView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbUpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        setClipChildren(false);

        ThumbView thumbView = new ThumbView(getContext());
        NumberView numberView = new NumberView(getContext());

        numberView.setPadding(10, 10, 10, 10);
        numberView.setGap(1);
        numberView.setCount(999);

        addView(thumbView);
        addView(numberView);

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int count = getChildCount();
//        for (int i = 0; i < count; i++) {
//            View view = getChildAt(i);
//            int measuredWidth = view.getMeasuredWidth();
//            int measuredHeight = view.getMeasuredHeight();
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int count = getChildCount();
//        int totalWidth = 0;
//        for (int i = 0; i < count; i++) {
//            View view = getChildAt(i);
//            int measuredWidth = view.getMeasuredWidth();
////            int measuredHeight = view.getMeasuredHeight();
//            totalWidth += MeasureSpec.getSize(measuredWidth);
//        }
//
//        Log.d(TAG, "l: " + l);
//        Log.d(TAG, "t: " + t);
//        Log.d(TAG, "r: " + r);
//        Log.d(TAG, "b: " + b);
//        Log.d(TAG, "totalWidth: " + totalWidth);
//
//        l = (r - l) / 2 - totalWidth / 2;
//        t = (b - t) / 2;
//        for (int i = 0; i < count; i++) {
//            View view = getChildAt(i);
//            int width = MeasureSpec.getSize(view.getMeasuredWidth());
//            int height = MeasureSpec.getSize(view.getMeasuredHeight());
//
//            r = l + width;
//            b = t + height;
//            Log.d(TAG, "child l: " + l);
//            Log.d(TAG, "child t: " + t);
//            Log.d(TAG, "child r: " + r);
//            Log.d(TAG, "child b: " + b);
//            view.layout(l, t - height / 2, r, b);
//
//            l += width;
//        }
////        super.onLayout(changed, l, t, r, b);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int count = getChildCount();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < count; i++) {
                    View view = getChildAt(i);
                    view.dispatchTouchEvent(ev);
                }

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < count; i++) {
                    View view = getChildAt(i);
                    view.dispatchTouchEvent(ev);
                }
                break;
            default:
                break;
        }
        return true;
//        return super.dispatchTouchEvent(ev);
    }
}
