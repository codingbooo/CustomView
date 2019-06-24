package top.codingbo.instagramstudy.photo.list.scalehelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bob
 * on 2019/6/24.
 */
public class ScaleLayout extends FrameLayout implements ScaleParent {
    private static final String TAG = "ScaleLayout";
    private View mTargetView;
    private float mX;
    private float mY;
    private int mWidth;
    private int mHeight;

    public ScaleLayout(@NonNull Context context) {
        super(context);
    }

    public ScaleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setTargetView(getChildAt(0));
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        setTargetView(child);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        layoutTarget();
    }

    private void setTargetView(View view) {
        if (view == null) {
            return;
        }
        mTargetView = view;

        mX = mTargetView.getX();
        mY = mTargetView.getY();

        mWidth = mTargetView.getWidth();
        mHeight = mTargetView.getHeight();
    }

    private void layoutTarget() {
        if (mTargetView != null) {
            mTargetView.layout(
                    (int) mX,
                    (int) mY,
                    (int) mX + mWidth,
                    (int) mY + mHeight);
        }
    }

    @Override
    public void moveTo(int x, int y) {
        mX = x;
        mY = y;
        layoutTarget();
    }

    @Override
    public void scaleTo(int width, int height) {
        mWidth = width;
        mHeight = height;
        layoutTarget();
    }

    public void scaleFromCenter(int width, int height) {
        mX -= (width - mWidth) / 2F;
        mY -= (height - mHeight) / 2F;
        mWidth = width;
        mHeight = height;
        layoutTarget();
    }

    @Override
    public void scaleByPoint(int x, int y, int width, int height) {
        //计算 以(x,y)为中心时 缩放的偏移量
        mX -= (width - mWidth) / (mWidth / (x - mX));
        mY -= (height - mHeight) / (mHeight / (y - mY));

        mWidth = width;
        mHeight = height;
        layoutTarget();
    }


    public String store(List<HashMap<String, String>> a) {
        if (a == null || a.size() <= 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (HashMap<String, String> map : a) {
            if (map == null || map.size() <= 0) {
                continue;
            }
            for (String s : map.keySet()) {
                builder.append(s).append("=").append(map.get(s)).append(";");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public List<HashMap<String, String>> load(String text) {
        if (TextUtils.isEmpty(text)
                //不存在键值对
                || !text.contains("=")) {
            return null;
        }
        String[] list = text.split("\n");
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        for (String item : list) {
            if (!item.contains("=")) {
                continue;
            }
            String[] mapString = item.split(";");
            HashMap<String, String> map = new HashMap<>(mapString.length);
            for (String s : mapString) {
                //判断
                if (!item.contains("=")) {
                    continue;
                }
                String[] kv = s.split("=");
                map.put(kv[0], kv[1]);
            }
            result.add(map);
        }
        return result;
    }

}
