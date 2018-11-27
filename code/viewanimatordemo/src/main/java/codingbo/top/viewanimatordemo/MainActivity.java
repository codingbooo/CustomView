package codingbo.top.viewanimatordemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<PicNewsEntry> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewFlipper viewFlipper = findViewById(R.id.viewFlipper);

        viewFlipper.addView(getView("Text 1"));
        viewFlipper.addView(getView("Text 2"));
        viewFlipper.addView(getView("Text 3"));
        viewFlipper.addView(getView("Text 4"));

        String path = "http://127.0.0.1:10369/HNXS_Interface/api/Module/NewsResource?Href=aHR0cDovL3d3dy5oYWlueHMucGV0cm9jaGluYS9oYWlueHMvaGR0cC9EaWdpdGFsQXNzZXRzL-a1t-WNl-mUgOWUrjIwMTjljYrlubTmgLvnu5PliIbmnpDkvJouanBn";
        mList = Arrays.asList(
                new PicNewsEntry(0, "title 0",
                        path
                ),
                new PicNewsEntry(1, "title 1",
                        "http://127.0.0.1:10369/HNXS_Interface/api/Module/NewsResource?Href=aHR0cDovL3d3dy5oYWlueHMucGV0cm9jaGluYS9oYWlueHMvZ3N5dy9EaWdpdGFsQXNzZXRzLzIwMThfMDFfMTJfMTQyNy5KUEc%3d"
                ),
                new PicNewsEntry(2, "title 2",
                        "http://127.0.0.1:10369/HNXS_Interface/api/Module/NewsResource?Href=aHR0cDovL3d3dy5oYWlueHMucGV0cm9jaGluYS9oYWlueHMvZ3N5dy9EaWdpdGFsQXNzZXRzLzA1MkExODMzMjIuSlBH"
                ),
                new PicNewsEntry(3, "title 3",
                        "http://127.0.0.1:10369/HNXS_Interface/api/Module/NewsResource?Href=aHR0cDovL3d3dy5oYWlueHMucGV0cm9jaGluYS9oYWlueHMvZ3N5dy9EaWdpdGFsQXNzZXRzL0FMM1UyODk5XzEuanBn"
                ),
                new PicNewsEntry(4, "title 4",
                        "http://127.0.0.1:10369/HNXS_Interface/api/Module/NewsResource?Href=aHR0cDovL3d3dy5oYWlueHMucGV0cm9jaGluYS9oYWlueHMvZ3N5dy9EaWdpdGFsQXNzZXRzL0FMM1UzNDc5X-WJr-acrC5qcGc%3d"
                )
        );


        AdapterViewFlipper adapterViewFlipper = findViewById(R.id.adapterViewFlipper);
        adapterViewFlipper.setAdapter(new Adapter());
        adapterViewFlipper.setFlipInterval(1000);
        adapterViewFlipper.startFlipping();


        Banner banner = findViewById(R.id.banner);
        BannerAdapter adapter = new BannerAdapter(banner);
        adapter.setData(mList);
        banner.start();

        ImageView view = new ImageView(this);

        Glide.with(this).load(path)
                .skipMemoryCache(false)
                .placeholder(R.drawable.black_background)
                .into(view);

    }

    private TextView getView(String s) {
        TextView view = new TextView(this);
        view.setTextSize(30);
        view.setText(s);
        return view;
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_pic, parent, false);
                holder = new Holder();
                holder.iv = convertView.findViewById(R.id.iv);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            Glide.with(MainActivity.this).load(mList.get(position)).into(holder.iv);
            return convertView;
        }

        class Holder {
            ImageView iv;
        }
    }
}
