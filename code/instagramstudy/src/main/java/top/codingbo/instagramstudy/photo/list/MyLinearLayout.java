package top.codingbo.instagramstudy.photo.list;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by bob
 * on 2019/6/20.
 */
public class MyLinearLayout extends LinearLayout {
    private static final String TAG = "MyLinearLayout";

    public MyLinearLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MyLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public MyLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        Log.d(TAG, "init: " + getId());
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        Log.d(TAG, "onMeasure: " + getId());
        super.onMeasure(widthSpec, heightSpec);
        Log.d(TAG, "onMeasure: " + getId());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: " + getId());
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout: " + getId());
    }

    @Override
    public void onDraw(Canvas c) {
        Log.d(TAG, "onDraw: " + getId());
        super.onDraw(c);
        Log.d(TAG, "onDraw: " + getId());
    }
}
