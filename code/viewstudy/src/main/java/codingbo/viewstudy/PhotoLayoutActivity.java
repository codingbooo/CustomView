package codingbo.viewstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by bob
 * on 2018/8/1.
 */
public class PhotoLayoutActivity extends AppCompatActivity {


    ImageView iv0;
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;
    ImageView iv5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        iv0 = findViewById(R.id.iv0);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        iv5 = findViewById(R.id.iv5);

        String url0 = "http://c.hiphotos.baidu.com/lvpics/w=600/sign=f45290d4092442a7ae0efea5e143ad95/8718367adab44aed447014b3b51c8701a18bfbaf.jpg";
        String url1 = "http://flipermag.com/wp-content/uploads/2014/04/6-600x400.jpg";
        String url2 = "http://marry.qiniudn.com/o_193p6lrhn191g1t3t6sb1gvb1u0oo.jpg?imageView/2/w/600";
        String url3 = "http://img2.imgtn.bdimg.com/it/u=222779410,3669731896&fm=27&gp=0.jpg";
        String url4 = "http://f.hiphotos.baidu.com/lvpics/w=600/sign=d78877af4b90f60304b09f470913b370/a8ec8a13632762d0aa095f21a1ec08fa513dc67d.jpg";
        String url5 = "http://marry.qiniudn.com/o_192uohr8ulgf1hht15d61pjb30b14.jpg?imageView/2/w/600";

        Glide.with(this).load(url0).into(iv0);
        Glide.with(this).load(url1).into(iv1);
        Glide.with(this).load(url2).into(iv2);
        Glide.with(this).load(url3).into(iv3);
        Glide.with(this).load(url4).into(iv4);
        Glide.with(this).load(url5).into(iv5);
    }


}
