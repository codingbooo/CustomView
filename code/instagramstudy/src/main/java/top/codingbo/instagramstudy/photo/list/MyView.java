package top.codingbo.instagramstudy.photo.list;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by bob
 * on 2019/6/20.
 */
public class MyView extends View {
    private static final String TAG = "MyView";

    public MyView(@NonNull Context context) {
        super(context);
    }

    public MyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        Log.d(TAG, "onMeasure: ");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout: ");
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        Log.d(TAG, "onDraw: ");
    }
}
