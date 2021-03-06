package codingbo.viewstudy.touchConflict;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by bob
 * on 2018/7/30.
 */
public class MyRecyclerView extends RecyclerView {
    private static final String TAG = "MyRecyclerView";

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String actionName = "";
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionName = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                actionName = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                actionName = "ACTION_UP";
                break;
            default:
                break;
        }

        Log.d(TAG, "----------------" + actionName + "----------------- ");
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
