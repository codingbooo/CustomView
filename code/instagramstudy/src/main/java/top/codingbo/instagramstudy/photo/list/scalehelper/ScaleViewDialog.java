package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import top.codingbo.instagramstudy.R;

/**
 *
 */
public class ScaleViewDialog extends Dialog {
    private static final String TAG = "ScaleViewDialog";

    private final View mTargetView;
    private FloatContainerLayout mContainer;
    private View mImage;
    private int[] mPos;
    private int mWidth;
    private int mHeight;

    public ScaleViewDialog(@NonNull Context context, View targetView) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mTargetView = targetView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scale_dialog);
        mContainer = findViewById(R.id.container);
        mImage = findViewById(R.id.iv_image);


        // FIXME: 2019/6/21 高度总是比原图小一点
        FloatContainerLayout.LayoutParams params = (FloatContainerLayout.LayoutParams) mImage.getLayoutParams();
        params.x = mPos[0];
        params.y = mPos[1];
        params.width = mWidth;
        params.height = mHeight;
        mImage.setLayoutParams(params);

        Log.d(TAG, "onCreate mWidth: " + mImage.getWidth() + ", mHeight: " + mImage.getHeight());
    }

    public void setLayout(int[] pos, int width, int height) {
        mPos = pos;
        mWidth = width;
        mHeight = height;
        Log.d(TAG, "setLayout mWidth: " + mWidth + ", mHeight: " + mHeight);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
//        Window window = getWindow();
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams layoutParams = window.getAttributes();
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(layoutParams);
    }

}
