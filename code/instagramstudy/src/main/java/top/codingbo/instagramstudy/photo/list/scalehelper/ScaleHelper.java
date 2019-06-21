package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * 缩放帮助类
 * <p>
 * 1. 监听触摸事件
 * 2. 找到目标View
 * 3. 创建Dialog 传输目标文件
 * 3.1 创建设定位置宽度的Layout
 * 4. 缩放
 * 5. 释放目标View
 * <p>
 * https://github.com/okaybroda/ImageZoom
 */
public class ScaleHelper {

    private final Context mContext;
    private ScaleGestureDetector mScaleGestureDetector;
    private View mTargetView;

    private ScaleHelper(Context context) {
        mContext = context;
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public static ScaleHelper getInstance(Context context) {
        return new ScaleHelper(context);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    public void setTagView(View v) {
        mTargetView = v;
        ScaleViewDialog dialog = new ScaleViewDialog(mContext, mTargetView);

        int[] pos = new int[2];
        mTargetView.getLocationOnScreen(pos);
//        mTargetView.getLocationInWindow(pos);
        dialog.setLayout(pos, mTargetView.getWidth(), mTargetView.getHeight());
        dialog.show();

    }

    class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
