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
    private ThumbView mThumbView;
    private NumberView mNumberView;

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

        mThumbView = new ThumbView(getContext());
        mNumberView = new NumberView(getContext());

//        mNumberView.setPadding(10, 10, 10, 10);
        mNumberView.setGap(1);
        mNumberView.setCount(true, 999);

        addView(mThumbView);
        addView(mNumberView);

        requestLayout();
    }

    public void setCount(boolean flag, int count) {
        mThumbView.setLiked(flag);
        mNumberView.setCount(flag, count);
    }

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
