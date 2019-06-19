package top.codingbo.instagramstudy.photo.list;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bilibili.boxing.AbsBoxingViewActivity;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.model.entity.BaseMedia;

import java.util.List;

import top.codingbo.instagramstudy.R;

/**
 * Created by bob
 * on 2019/6/19.
 */
public class PhotoListActivity extends AbsBoxingViewActivity {

    public static final int REQUEST_PERMISSION = 101;
    RecyclerView mRecyclerView;
    private Adapter mediaAdapter;


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
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        mRecyclerView = findViewById(R.id.recycler);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mediaAdapter = new Adapter();

        mRecyclerView.setAdapter(mediaAdapter);

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


    class Adapter extends RecyclerView.Adapter<Holder> {

        private List<BaseMedia> mMedias;

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup group, int i) {
            return new Holder(LayoutInflater.from(group.getContext()).inflate(R.layout.photo_item, group, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int i) {
            BaseMedia media = mMedias.get(i);
            final ScaleImageView image = holder.image;
            BoxingMediaLoader.getInstance().displayRaw(image, media.getPath(), image.getWidth(), image.getHeight(), null);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    image.showBig();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mMedias == null ? 0 : mMedias.size();
        }

        public void addAllData(List<BaseMedia> medias) {
            mMedias = medias;
            notifyDataSetChanged();
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        ScaleImageView image;

        public Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image);
        }
    }
}
