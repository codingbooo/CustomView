package codingbo.viewstudy.myLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by bob
 * on 2018/8/3.
 */
public class MyListView extends ListView {

    private float mDispatchX;
    private float mDispatchY;
    private int mTouchSlop;

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        float x = ev.getX();
        float y = ev.getY();


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = Math.abs(x - mDispatchX);
                float deltaY = Math.abs(y - mDispatchY);

                if (deltaX > mTouchSlop && deltaX > deltaY) {
                    getParent().requestDisallowInterceptTouchEvent(false);

                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mDispatchX = x;
        mDispatchY = y;


        return super.dispatchTouchEvent(ev);
    }
}
