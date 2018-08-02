package codingbo.viewstudy.touchConflict;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by bob
 * on 2018/7/30.
 */
public class MyViewPager extends ViewPager {

    private static final String TAG = "MyViewPager";

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.d(TAG, "dispatchTouchEvent: ");
        boolean b = super.dispatchTouchEvent(ev);
        Log.d(TAG, "dispatchTouchEvent: " + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: ");
        boolean b = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "onInterceptTouchEvent: " + b);
        return b;

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.d(TAG, "onTouchEvent: ");
        boolean b = super.onTouchEvent(e);
        Log.d(TAG, "onTouchEvent: " + b);
        return b;
    }
}
