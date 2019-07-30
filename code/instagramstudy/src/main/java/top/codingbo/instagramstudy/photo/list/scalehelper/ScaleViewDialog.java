package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

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
    }

    public void bindView() {
        //创建占位View
        mPlaceHolder = new View(getContext());
        mPlaceHolder.setId(mTargetView.getId());
        mTagViewParams = mTargetView.getLayoutParams();
        mTargetParentView = (ViewGroup) mTargetView.getParent();

        //移除原View
        mTargetParentView.removeView(mTargetView);

        //添加到Dialog
        Log.d(TAG, "bindView: " + Arrays.toString(mPos));
        mContainer.addView(mTargetView);
        mContainer.moveTo(mPos[0], mPos[1]);
        mContainer.setInitPosition(mPos[0], mPos[1], mTargetView.getWidth(), mTargetView.getHeight());

        mTargetParentView.addView(mPlaceHolder, mTagViewParams);
    }

    public void unbindView() {
        mContainer.backToInitPosition(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContainer.removeView(mTargetView);
                mTargetParentView.removeView(mPlaceHolder);
                mTargetParentView.addView(mTargetView, mTagViewParams);
                dismiss();
            }
        });
    }

    public void relayout(int[] pos, int width, int height) {
        mPos = pos;
        mWidth = width;
        mHeight = height;
        if (mContainer != null) {
            mContainer.moveTo(pos[0], pos[1]);
            mContainer.scaleFromCenter(width, height);
        }
    }

    public void setLayout(int[] pos, int width, int height) {
        mPos = pos;
        mWidth = width;
        mHeight = height;
    }
}
