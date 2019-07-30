package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import top.codingbo.instagramstudy.R;

/**
 * Created by bob
 * on 2019/6/24.
 */
public class ScaleLayoutActivity extends AppCompatActivity {
    private static final String TAG = "ScaleLayoutActivity";

    ScaleLayout mScaleLayout;
    private int mY = 0;
    private float factor = 0;
    private ScaleGestureDetector mScaleGestureDetector;
    ImageView mIvImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_layout);
        mIvImage = findViewById(R.id.iv_image);

        mScaleLayout = findViewById(R.id.scaleLayout);
        mScaleLayout.scaleTo(800, 800);
        mScaleLayout.moveTo(100, 400);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        HandlerThread thread = new HandlerThread("ViewUpdate");

        Handler handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                
            }
        };
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
        Log.d(TAG, "setY: " + y);
        mScaleLayout.moveTo(mY, mY);
        mScaleLayout.scaleTo(500, 500);
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
        int size = (int) (200 * factor);
//        mScaleLayout.scaleTo(size, size);
//        mScaleLayout.scaleFromCenter(size, size);
//        mScaleLayout.scaleByPoint(mY, mY, size, size);
    }

    class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        private float mFocusX;
        private float mFocusY;

        private int[] pos = new int[2];
        private int mWidth;
        private int mHeight;

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
//            Log.d(TAG, "onScaleBegin: ");
//            mIvImage.getLocationInWindow(pos);
//            mIvImage.getLocationOnScreen(pos);
            pos[0] = (int) mIvImage.getX();
            pos[1] = (int) mIvImage.getY();

            mFocusX = detector.getFocusX();
            mFocusY = detector.getFocusY();
            Log.d(TAG, "onScaleBegin: " + pos[0] + "," + pos[1]);
            mWidth = mIvImage.getWidth();
            mHeight = mIvImage.getHeight();
            mScaleLayout.setInitPosition(pos[0], pos[1],
                    mWidth, mHeight);
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
//            Log.d(TAG, "onScale: ");

//            int x = (int) (pos[0] + detector.getFocusX() - mFocusX);
//            int y = (int) (pos[1] + detector.getFocusY() - mFocusY);
//            Log.d(TAG, "onScale: " + x + "," + y);
//            mScaleLayout.moveTo(x, y);


            int dx = (int) (detector.getFocusX() - mFocusX);
            int dy = (int) (detector.getFocusY() - mFocusY);
            mFocusX = detector.getFocusX();
            mFocusY = detector.getFocusY();
            mScaleLayout.moveBy(dx, dy);

            float factor = detector.getScaleFactor();
//            int width = (int) (mIvImage.getWidth() * factor);
//            int height = (int) (mIvImage.getHeight() * factor);
//            mScaleLayout.scaleTo(width, height);

            int width = (int) (mWidth * factor);
            int height = (int) (mHeight * factor);

//            mScaleLayout.scaleByPoint((int) detector.getFocusX(), (int) detector.getFocusY(), width, height);
            mScaleLayout.scaleFromCenter(width, height);


            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleEnd: ");
            mScaleLayout.backToInitPosition(new AnimatorListenerAdapter() {
            });
        }
    }
}
