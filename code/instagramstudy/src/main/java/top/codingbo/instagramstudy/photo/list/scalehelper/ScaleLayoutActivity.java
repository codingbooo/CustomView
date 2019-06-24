package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_layout);

        mScaleLayout = findViewById(R.id.scaleLayout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mScaleLayout.postOnAnimation(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator animatory = ObjectAnimator
                            .ofInt(ScaleLayoutActivity.this, "Y", 0, 600);
                    ObjectAnimator animatorScale = ObjectAnimator
                            .ofFloat(ScaleLayoutActivity.this, "factor", 1, 8);

                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(animatory, animatorScale);
                    set.setDuration(2000).start();
                }
            });
        }
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
        Log.d(TAG, "setY: " + y);
//        mScaleLayout.moveTo(mY, mY);
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
        int size = (int) (200 * factor);
//        mScaleLayout.scaleTo(size, size);
        mScaleLayout.scaleFromCenter(size, size);
//        mScaleLayout.scaleByPoint(mY, mY, size, size);
    }
}
