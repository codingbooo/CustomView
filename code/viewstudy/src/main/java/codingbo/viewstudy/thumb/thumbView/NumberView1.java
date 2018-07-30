package codingbo.viewstudy.thumb.thumbView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bob
 * on 18.3.12.
 */

public class NumberView1 extends View {

    public final static int ANIM_UP = 1;//向上滚动
    public final static int ANIM_DOWN = 2;//向下滚动


    public NumberView1(Context context) {
        this(context, null);
    }

    public NumberView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void showAnim(int belowNum, int aboveNum, int animType) {


    }
}
