package top.codingbo.instagramstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;

import top.codingbo.instagramstudy.photo.list.PhotoListActivity;
import top.codingbo.instagramstudy.photo.list.ScaleHelperActivity;
import top.codingbo.instagramstudy.photo.list.ScaleViewActivity;
import top.codingbo.instagramstudy.photo.picker.PhotoPicker2Activity;


public class MainActivity extends AppCompatActivity {

    private BoxingConfig mConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
        mConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
//                .needCamera(cameraRes)
                .needGif()
                // 支持gif，相机，设置最大选图数
                .withMaxCount(9);
//        // 启动缩略图界面, 依赖boxing-impl.

    }

    public void openPicker(View view) {
//        startActivity(new Intent(this, PhotoPickerActivity.class));
//        Boxing.of(config).withIntent(this, BoxingActivity.class).start(this);

        Boxing.of(mConfig).withIntent(this, PhotoPicker2Activity.class).start(this);
    }

    public void openList(View view) {
        Boxing.of(mConfig).withIntent(this, PhotoListActivity.class).start(this);
    }

    public void scaleView(View view) {
        startActivity(new Intent(this, ScaleViewActivity.class));
    }
    public void scaleHelperView(View view) {
        startActivity(new Intent(this, ScaleHelperActivity.class));
    }
}
