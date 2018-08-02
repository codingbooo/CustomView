package codingbo.viewstudy.myLayout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 图片展示布局
 * <p>
 * <p>
 * 1    2
 * 3
 * 4  5  6
 */
public class PhotoLayout extends ViewGroup {

    public static final int MAX_CHILD_COUNT = 6;
    private static final String TAG = "PhotoLayout";
    public static final int ITEM_GAP = 4;

    public PhotoLayout(Context context) {
        this(context, null);
    }

    public PhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
//        for (int i = 0; i < MAX_CHILD_COUNT; i++) {
//            ImageView child = new ImageView(getContext());
//            child.setBackgroundColor(Color.argb(255, 10 * i + 180, 10 * i + 180, 10 * i + 180));
//            addView(child);
//        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

//        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: ");
        Log.d(TAG, "l: " + l);
        Log.d(TAG, "t: " + t);
        Log.d(TAG, "r: " + r);
        Log.d(TAG, "b: " + b);

        int width = getWidth();
        int height = getHeight();

        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int childWidth = child.getWidth();
            int childHeight = child.getHeight();

            switch (i) {
                case 0: {
                    left = l;
                    top = t;
                    right = width / 5 * 3 + left;
                    bottom = height / 3 * 2 + top;
                    break;
                }
                case 1:
                    left = right;
                    top = top;
                    right = r;
                    bottom = bottom / 2;
                    break;
                case 2:
                    left = left;
                    right = right;
                    top = bottom;
                    bottom += bottom;
                    break;
                case 3:
                    left = t;
                    top = bottom;
                    right = width / 3;
                    bottom = b;
                    break;
                case 4:
                    left = right;
                    top = top;
                    right += right;
                    bottom = b;
                    break;
                case 5:
                    left = right;
                    top = top;
                    right += right;
                    bottom = b;
                    break;
                default:
                    break;
            }
            child.layout(left + ITEM_GAP, top + ITEM_GAP, right - ITEM_GAP, bottom - ITEM_GAP);
        }
    }
}
