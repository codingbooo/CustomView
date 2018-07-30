package codingbo.viewstudy.touchConflict;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import codingbo.viewstudy.R;

/**
 * Created by bob
 * on 2018/7/30.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private List<ImageData> mData;


    public RecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setDAta(List<ImageData> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageData data = mData.get(position);
        PagerAdapter adapter = holder.mViewPager.getAdapter();
        if (adapter == null) {
            adapter = new ImageVpAdapter(mContext, data);
        }
        holder.mViewPager.setAdapter(adapter);
//        holder.mViewPager.setCurrentItem(2);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewPager mViewPager;

        public ViewHolder(View itemView) {
            super(itemView);
            mViewPager = itemView.findViewById(R.id.viewPager);
        }
    }
}
