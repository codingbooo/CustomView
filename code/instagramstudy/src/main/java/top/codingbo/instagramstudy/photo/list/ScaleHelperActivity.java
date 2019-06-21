package top.codingbo.instagramstudy.photo.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import top.codingbo.instagramstudy.R;
import top.codingbo.instagramstudy.photo.list.scalehelper.ScaleHelper;

/**
 * Created by bob
 * on 2019/6/21.
 */
public class ScaleHelperActivity extends AppCompatActivity {

    private ScaleHelper mScaleHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_helper);

        mScaleHelper = ScaleHelper.getInstance(this);

        View view = findViewById(R.id.iv_image);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScaleHelper.setTagView(v);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScaleHelper.onTouchEvent(event)
                || super.onTouchEvent(event);
    }
}
