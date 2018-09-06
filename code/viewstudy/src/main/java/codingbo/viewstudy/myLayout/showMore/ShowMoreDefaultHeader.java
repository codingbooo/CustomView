package codingbo.viewstudy.myLayout.showMore;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
 * on 2018/9/6.
 *
 * @author bob
 */
public class ShowMoreDefaultHeader implements ShowMoreHeader {
    private static final String TAG = "ShowMoreDefaultHeader";
    private LinearLayout mLayout;
    private final ImageView mIv;
    private final TextView mTv;
    private ObjectAnimator mAnimator;

    public ShowMoreDefaultHeader(Context context) {

        mLayout = getLayout(context);

        mIv = getImageView(context);

        mTv = getTextView(context);

        mLayout.addView(mIv);
        mLayout.addView(mTv);
    }

    private TextView getTextView(Context context) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(params);
        tv.setText("加载数据...");
        return tv;
    }

    private ImageView getImageView(Context context) {
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_refresh);
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }

    private LinearLayout getLayout(Context context) {
        LinearLayout layout = new LinearLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(0, 100, 0, 100);
        return layout;
    }

    public static ShowMoreDefaultHeader getInstance(Context context) {
        return new ShowMoreDefaultHeader(context);
    }


    @Override
    public void onPosition(int y, int max) {
        Log.d(TAG, "onPosition: " + y);
        mIv.setRotation(y);

        setRotation(y == max);
    }

    private void setRotation(boolean b) {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(mIv, "rotation", 0, 360);
        }
        if (b && !mAnimator.isRunning()) {
            mAnimator.setDuration(500);
            mAnimator.start();
        } else if (!b && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    @Override
    public View getView() {
        return mLayout;
    }
}
