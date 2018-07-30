package codingbo.viewstudy.touchConflict;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import codingbo.viewstudy.R;

/**
 * Created by bob
 * on 2018/7/30.
 */
public class ImageVpAdapter extends PagerAdapter {

    private final Context mContext;

    private ImageData mData;

    private List<ImageView> mIvPool;

    public ImageVpAdapter(Context context, ImageData data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.path.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

//        container.removeView();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        String path = mData.path.get(position);
        View view = getView(path, container);
        container.addView(view);
        return view;
    }

    @NonNull
    private View getView(String path, ViewGroup container) {
//        ImageView iv = new ImageView(mContext);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        params.width = 200;
//        iv.setLayoutParams(params);


        ImageView iv = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.image, container, false);
//        iv.setOnClickListener(v -> Log.d("OnClick", " on click: "));
        Glide.with(mContext).load(path).into(iv);
        return iv;
    }
}
