package codingbo.top.behaviordemo.demo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import codingbo.top.behaviordemo.R;

/**
 * todo
 * <p>
 * 1. 上滑时 下滑事件没有被拦截
 * 2. 上滑完成时, 继续上滑到不能上滑,迅速按返回键,content部分会自动上滑(已修复,不理想)
 * <p>
 * Q:
 * 1.怎么能 消费但是不操作?
 * 2.fling 的回调机制
 * 3.stop的回调机制
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
        if (mContentBehavior.getCloseStatus() == BaseBehavior.State.Closed) {
            mContentBehavior.showHeader();
            mHeaderBehavior.showHeader();
        } else {
            super.onBackPressed();
        }
    }
}
