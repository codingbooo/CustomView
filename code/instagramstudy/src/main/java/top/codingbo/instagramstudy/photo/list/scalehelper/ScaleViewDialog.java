package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import top.codingbo.instagramstudy.R;

/**
 *
 */
public class ScaleViewDialog extends Dialog {
    private static final String TAG = "ScaleViewDialog";

    private final View mTargetView;
    private ScaleLayout mContainer;
    private int[] mPos;
    private int mWidth;
    private int mHeight;
    private ViewGroup mTargetParentView;
    private View mPlaceHolder;
    private ViewGroup.LayoutParams mTagViewParams;

    public ScaleViewDialog(@NonNull Context context, View targetView) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mTargetView = targetView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scale_dialog);
        mContainer = findViewById(R.id.container);
    }

    @Override
    public void show() {
        super.show();
        bindView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindView();
    }

    private void bindView() {
        //创建占位View
        mPlaceHolder = new View(getContext());
        mPlaceHolder.setId(mTargetView.getId());
        mTagViewParams = mTargetView.getLayoutParams();
        mTargetParentView = (ViewGroup) mTargetView.getParent();

        //移除原View
        mTargetParentView.removeView(mTargetView);

        //添加到Dialog
        mContainer.scaleByPoint(mPos[0], mPos[1], mWidth, mHeight);
        mContainer.addView(mTargetView);

        mTargetParentView.addView(mPlaceHolder, mTagViewParams);
    }

    private void unbindView() {
        mContainer.removeView(mTargetView);
        mTargetParentView.removeView(mPlaceHolder);
        mTargetParentView.addView(mTargetView, mTagViewParams);
    }

    public void setLayout(int[] pos, int width, int height) {
        mPos = pos;
        mWidth = width;
        mHeight = height;
        if (mContainer != null) {
//            mContainer.scaleByPoint(pos[0], pos[1], width, height);
            mContainer.scaleFromCenter(width, height);
            mContainer.moveTo(pos[0], pos[1]);
        }
    }
}
