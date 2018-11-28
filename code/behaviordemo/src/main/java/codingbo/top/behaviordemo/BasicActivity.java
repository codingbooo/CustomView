package codingbo.top.behaviordemo;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BasicActivity extends AppCompatActivity {

    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private BottomSheetDialog mDialog;

    CoordinatorLayout mCoordinatorLayout;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        mTextView = findViewById(R.id.tv_bottom);
        mBottomSheetBehavior = BottomSheetBehavior.from((View) mTextView);
        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setPeekHeight(0);

        mCoordinatorLayout = findViewById(R.id.coordinator);


        SwipeDismissBehavior dismissBehavior = new SwipeDismissBehavior();

        /*
         * //设置滑动的方向，有3个值
         * 1，SWIPE_DIRECTION_ANY 表示向左像右滑动都可以，
         * 2，SWIPE_DIRECTION_START_TO_END，只能从左向右滑
         * 3，SWIPE_DIRECTION_END_TO_START，只能从右向左滑
         */
        dismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);
        dismissBehavior.setStartAlphaSwipeDistance(0f);
        dismissBehavior.setSensitivity(0.2f);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mTextView.getLayoutParams();
        params.setBehavior(dismissBehavior);

    }

    public void openClose(View view) {
//        bottomSheetBehavior();
        if (mDialog == null) {
            mDialog = new BottomSheetDialog(this);
            mDialog.setContentView(R.layout.dialog_bottom);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);
        }
        mDialog.show();
    }

    private void bottomSheetBehavior() {
        if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}
