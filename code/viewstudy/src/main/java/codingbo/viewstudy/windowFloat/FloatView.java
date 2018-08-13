package codingbo.viewstudy.windowFloat;

import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by bob
 * on 2018/8/9.
 * @author bob
 */
public class FloatView implements WindowManager {

    @Override
    public Display getDefaultDisplay() {
        return null;
    }

    @Override
    public void removeViewImmediate(View view) {

    }

    @Override
    public void addView(View view, ViewGroup.LayoutParams params) {

    }

    @Override
    public void updateViewLayout(View view, ViewGroup.LayoutParams params) {

    }

    @Override
    public void removeView(View view) {

    }
}
