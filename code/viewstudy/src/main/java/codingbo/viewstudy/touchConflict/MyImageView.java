package codingbo.viewstudy.touchConflict;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by bob
 * on 2018/7/30.
 */
public class MyImageView extends ImageView {
    private static final String TAG = "MyImageView";

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        boolean touchEvent = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent: " + touchEvent);
        return touchEvent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: ");
        boolean b = super.dispatchTouchEvent(event);
        Log.d(TAG, "dispatchTouchEvent: " + b);
        return b;
    }
}
