package codingbo.top.behaviordemo.demo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import codingbo.top.behaviordemo.R;

/**
 * Created by bob
 * on 2018/11/28.
 */
public class Demo1Activity extends AppCompatActivity {

    View mHeader;
    View mContent;
    private Demo1HeaderBehavior mHeaderBehavior;
    private Demo1Behavior mContentBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);

        mHeader = findViewById(R.id.header);
        mContent = findViewById(R.id.content);

        mHeaderBehavior = (Demo1HeaderBehavior) getBehavior(mHeader);
        mContentBehavior = (Demo1Behavior) getBehavior(mContent);
    }

    private CoordinatorLayout.Behavior getBehavior(View view) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        return lp.getBehavior();
    }

    @Override
    public void onBackPressed() {
        if (!mContentBehavior.isOpen()) {
            mContentBehavior.showHeader();
            mHeaderBehavior.showHeader();
        } else {
            super.onBackPressed();
        }
    }
}
