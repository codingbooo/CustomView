package codingbo.viewstudy.touchConflict;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import codingbo.viewstudy.R;


/**
 * Created by bob
 * on 2018/7/30.
 */
public class LayoutActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter(this);
        mRecyclerView.setAdapter(adapter);
        ArrayList<ImageData> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ImageData data = new ImageData();
            data.path = new ArrayList<>();
//            data.path.add("https://ww1.sinaimg.cn/large/0065oQSqgy1ftrrvwjqikj30go0rtn2i.jpg");
//            data.path.add("https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg");
//            data.path.add("http://ww1.sinaimg.cn/large/0073sXn7ly1ft82s05kpaj30j50pjq9v.jpg");
//            data.path.add("https://ww1.sinaimg.cn/large/0065oQSqly1ft5q7ys128j30sg10gnk5.jpg");
//            data.path.add("https://ww1.sinaimg.cn/large/0065oQSqgy1ft4kqrmb9bj30sg10fdzq.jpg");

            data.path.add("http://flipermag.com/wp-content/uploads/2014/04/6-600x400.jpg");
            data.path.add("http://marry.qiniudn.com/o_193p6lrhn191g1t3t6sb1gvb1u0oo.jpg?imageView/2/w/600");
            data.path.add("http://c.hiphotos.baidu.com/lvpics/w=600/sign=f45290d4092442a7ae0efea5e143ad95/8718367adab44aed447014b3b51c8701a18bfbaf.jpg");
            data.path.add("http://img2.imgtn.bdimg.com/it/u=222779410,3669731896&fm=27&gp=0.jpg");
            data.path.add("http://f.hiphotos.baidu.com/lvpics/w=600/sign=d78877af4b90f60304b09f470913b370/a8ec8a13632762d0aa095f21a1ec08fa513dc67d.jpg");
            data.path.add("http://marry.qiniudn.com/o_192uohr8ulgf1hht15d61pjb30b14.jpg?imageView/2/w/600");
            data.path.add("http://img2.imgtn.bdimg.com/it/u=4205732644,3582877646&fm=27&gp=0.jpg");
            list.add(data);
        }
        adapter.setDAta(list);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("haha", "onTouchEvent: " + event);

        return super.onTouchEvent(event);

    }
}
