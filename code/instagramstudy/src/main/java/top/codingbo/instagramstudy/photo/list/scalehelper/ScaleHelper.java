package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import top.codingbo.instagramstudy.R;

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
    private static final String TAG = "ScaleHelper";
    private static final int TAG_KEY = R.id.scaleHelperTagKey;
    private final Activity mActivity;
    private ScaleGestureDetector mScaleGestureDetector;
    private View mTargetView;
    private ScaleViewDialog mDialog;
    private float mX;
    private float mY;

    private ScaleHelper(Activity activity) {
        mActivity = activity;
        mScaleGestureDetector = new ScaleGestureDetector(activity, new ScaleListener());
    }

    public static ScaleHelper getInstance(Activity context) {
        return new ScaleHelper(context);
    }

    public boolean dispatchTouch(MotionEvent event) {
        if (event.getPointerCount() < 2) {
            return false;
        }

        if (mTargetView == null) {
            mTargetView = findTargetView(event);
            if (mTargetView == null) {
                Log.d(TAG, "没找到...");
                return false;
            }
        }
        Log.d(TAG, "找到了...");
        // 为何 一直返回true??
        boolean b = mScaleGestureDetector.onTouchEvent(event);
        Log.d(TAG, "dispatchTouch: " + b);
        return b;
    }

    private View findTargetView(MotionEvent event) {
        ViewGroup contentView = mActivity.findViewById(android.R.id.content);
        return findView(contentView, event);
    }

    private View findView(ViewGroup viewGroup, MotionEvent event) {
        Rect rect = new Rect();
        MotionEvent.PointerCoords pointerCoords1 = new MotionEvent.PointerCoords();
        event.getPointerCoords(0, pointerCoords1);

        MotionEvent.PointerCoords pointerCoords2 = new MotionEvent.PointerCoords();
        event.getPointerCoords(1, pointerCoords2);

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            int[] pos = new int[2];
            child.getLocationOnScreen(pos);

            rect.left = pos[0];
            rect.top = pos[1];
            rect.right = rect.left + child.getWidth();
            rect.bottom = rect.top + child.getHeight();

//            boolean contain = rect.contains((int) event.getX(0), (int) event.getY(0))
//                    && rect.contains((int) event.getX(1), (int) event.getY(1));

            boolean contain = rect.contains((int) pointerCoords1.x, (int) pointerCoords1.y)
                    && rect.contains((int) pointerCoords2.x, (int) pointerCoords2.y);

            if (child.getTag(TAG_KEY) != null && contain) {
                return child;
            }

            if (child instanceof ViewGroup) {
                View view = findView((ViewGroup) child, event);
                if (view != null) {
                    return view;
                }
            }
        }
        return null;
    }

    public void setTagView(View v) {
        v.setTag(TAG_KEY, new Object());
    }

    class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        private float mFocusX;
        private float mFocusY;
        private int mWidth;
        private int mHeight;

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleBegin: ");

            mX = mTargetView.getX();
            mY = mTargetView.getY();
            mWidth = mTargetView.getWidth();
            mHeight = mTargetView.getHeight();

            mFocusX = detector.getFocusX();
            mFocusY = detector.getFocusY();

            bindView(mTargetView);
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d(TAG, "onScale: ");
            int x = (int) (mX + detector.getFocusX() - mFocusX);
            int y = (int) (mY + detector.getFocusY() - mFocusY);

            float factor = detector.getScaleFactor();
            mDialog.setLayout(new int[]{x, y}, (int) (mTargetView.getWidth() * factor), (int) (mTargetView.getHeight() * factor));
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleEnd: ");
            unbindView(mTargetView);
            mTargetView = null;

        }
    }

    private void unbindView(View view) {
        if (mDialog == null) {
            return;
        }
        mDialog.dismiss();
    }

    private void bindView(View view) {
        mDialog = new ScaleViewDialog(mActivity, view);
        int[] pos = new int[2];
//        mTargetView.getLocationOnScreen(pos);
        view.getLocationInWindow(pos);
        mDialog.setLayout(pos, view.getWidth(), view.getHeight());
        mDialog.show();
    }
}
