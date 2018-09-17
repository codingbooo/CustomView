package codingbo.viewstudy.myLayout.showMore;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import codingbo.viewstudy.R;

/**
 * Created by bob
 * on 2018/9/17.
 */
public class DefaultHeader extends LinearLayout implements ShowMoreHeader {

    private ImageView mIv;
    private TextView mTv;
    private ObjectAnimator mAnimator;

    public static DefaultHeader getInstance(Context context) {
        return new DefaultHeader(context);
    }

    public DefaultHeader(Context context) {
        this(context, null);
    }

    public DefaultHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mIv = getImageView(context);
        mTv = getTextView(context);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.GRAY);
        setPadding(0, 100, 0, 100);

        addView(mIv);
        addView(mTv);
    }

    private ImageView getImageView(Context context) {
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_refresh);
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }


    private TextView getTextView(Context context) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(params);
        tv.setTextColor(Color.WHITE);
        return tv;
    }

    @Override
    public void onDragging(ShowMoreState status, int y, int maxY) {
        float degree = (float) y / maxY * 360;
        mIv.setRotation(degree);
    }

    @Override
    public void onStatusChanged(ShowMoreState status, ShowMoreState oldStatus) {
        setRotation(status == ShowMoreState.REFRESH);
        setTips(status);
    }

    private void setTips(ShowMoreState status) {
        String tips;
        if (status == ShowMoreState.REFRESH) {
            tips = "正在刷新...";
        } else {
            tips = "下拉刷新...";
        }
        mTv.setText(tips);
    }

    private void setRotation(boolean rotation) {
        if (!rotation) {
            //取消动画
            if (mAnimator != null && mAnimator.isRunning()) {
                mAnimator.cancel();
            }
            return;
        }

        //保持动画
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(mIv, "rotation", 0, 360);
            mAnimator.setDuration(500);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }

        if (!mAnimator.isRunning()) {
            mAnimator.start();
        }
    }

    @Override
    public View getView() {
        return this;
    }
}
