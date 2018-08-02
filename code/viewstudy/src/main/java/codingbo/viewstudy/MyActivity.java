package codingbo.viewstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import codingbo.viewstudy.myLayout.MyHorizontalScrollView;

/**
 * Created by bob
 * on 2018/8/2.
 */
public class MyActivity extends AppCompatActivity {

    MyHorizontalScrollView mScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mScrollView = findViewById(R.id.hor_scroll);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("item" + i);
        }
        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_list, null);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            tvTitle.setText("title" + i);

            ListView listView = view.findViewById(R.id.ll);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
            mScrollView.addView(view);
        }
    }
}


