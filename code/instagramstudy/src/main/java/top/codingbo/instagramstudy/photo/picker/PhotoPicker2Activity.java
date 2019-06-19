package top.codingbo.instagramstudy.photo.picker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bilibili.boxing.AbsBoxingViewActivity;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.adapter.BoxingMediaAdapter;

import java.util.List;

import top.codingbo.instagramstudy.R;

/**
 * Created by boliang
 * on 2019-06-15.
 * <p>
 * 2, 实现一个界面, 分上下两部分,
 * 上部分是图片, 下面是recycleview列表,
 * 手指在recycleview中可以正常滑动,
 * 手指滑动到图片和recycleview交界处时,
 * 整个界面可以上移动把recycleview展开成全屏样式.
 * 交互示例见视频2.(可以不用实现上半部分的图片放大缩小特效)
 * <p>
 * https://github.com/Skykai521/InstagramPhotoPicker
 */
public class PhotoPicker2Activity extends AbsBoxingViewActivity {
    public static final int REQUEST_PERMISSION = 101;
    RecyclerView mRecyclerView;
    ImageView mIvImage;
    NestedLinearLayout mNestedContainer;

    private BoxingMediaAdapter mediaAdapter;

    @Override
    public void startLoading() {
        loadMedias();
    }

    @Override
    public void showMedia(@Nullable List<BaseMedia> medias, int allCount) {
        if (medias == null) {
            Toast.makeText(this, "相册为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mediaAdapter.addAllData(medias);
        displayPhoto(medias.get(0));
    }

    private void displayPhoto(BaseMedia media) {
        String path = media.getPath();
        BoxingMediaLoader.getInstance().displayRaw(mIvImage, path, mIvImage.getWidth(), mIvImage.getHeight(), null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker2);
        mNestedContainer = findViewById(R.id.container);
        mIvImage = findViewById(R.id.iv_image);
        mRecyclerView = findViewById(R.id.recycler);


        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mediaAdapter = new BoxingMediaAdapter(this);
        mediaAdapter.setOnMediaClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPhoto((BaseMedia) v.getTag());
            }
        });
        mRecyclerView.setAdapter(mediaAdapter);

        mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startLoading();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (REQUEST_PERMISSION == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onRequestPermissionSuc(requestCode, permissions, grantResults);
            } else {
                onRequestPermissionError(permissions,
                        new SecurityException("request android.permission.READ_EXTERNAL_STORAGE error."));
            }
        }
    }

    private void onRequestPermissionSuc(int code, String[] permissions, int[] results) {
        startLoading();
    }

    private void onRequestPermissionError(String[] permissions, SecurityException e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
