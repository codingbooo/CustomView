package codingbo.viewstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import codingbo.viewstudy.myLayout.showMore.ShowMoreLayout;
import codingbo.viewstudy.myLayout.showMore.ShowMoreLayout2;

/**
 * Created by bob
 * on 2018/8/27.
 */
public class ShowMoreActivity extends AppCompatActivity {

    private ShowMoreLayout2 sml;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private TextView mTvEmpty;


    private ArrayList<String> mList;
    private Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        sml = findViewById(R.id.sml);
        mTvEmpty = findViewById(R.id.tv_empty);

//        sml.setShowMoreListener(new ShowMoreLayout.ShowMoreListener() {
//            @Override
//            public void onRefresh(ShowMoreLayout showMoreLayout) {
//                sml.postDelayed(() -> {
//                    showMoreLayout.showRefreshView(false);
//                    refreshData(mList);
//                    mAdapter.notifyDataSetChanged();
//                }, 3000);
//
//
//            }
//
//            @Override
//            public void onMore(ShowMoreLayout showMoreLayout) {
//
//            }
//        });

        mList = new ArrayList<>();

        addData(mList);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView2 = findViewById(R.id.recyclerView2);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView2.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        mRecyclerView2.setAdapter(mAdapter);
    }


    private void refreshData(ArrayList<String> list) {
        list.clear();
        int size = list.size();
        for (int i = 0; i < 20; i++) {
            list.add((size + i) + "");
        }
    }

    private void addData(ArrayList<String> list) {
        int size = list.size();
        for (int i = 0; i < 20; i++) {
            list.add((size + i) + "");
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ShowMoreActivity.this).inflate(R.layout.demo_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String s = mList.get(position);
            holder.tv.setText(s);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
