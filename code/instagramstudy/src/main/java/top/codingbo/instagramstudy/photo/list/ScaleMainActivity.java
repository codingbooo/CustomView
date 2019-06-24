package top.codingbo.instagramstudy.photo.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;

import top.codingbo.instagramstudy.R;
import top.codingbo.instagramstudy.photo.list.scalehelper.PhotoList2Activity;
import top.codingbo.instagramstudy.photo.list.scalehelper.ScaleLayoutActivity;

/**
 * Created by bob
 * on 2019/6/24.
 */
public class ScaleMainActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_main);
    }

    public void scaleView(View view) {
        startActivity(new Intent(this, ScaleViewActivity.class));
    }
    public void scaleHelperView(View view) {
        startActivity(new Intent(this, ScaleHelperActivity.class));
    }

    public void scaleLayout(View view) {
        startActivity(new Intent(this, ScaleLayoutActivity.class));
    }

    public void photoList2(View view) {
        BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
//                .needCamera(cameraRes)
                .needGif()
                // 支持gif，相机，设置最大选图数
                .withMaxCount(9);
        Boxing.of(config).withIntent(this, PhotoList2Activity.class).start(this);
    }
}
