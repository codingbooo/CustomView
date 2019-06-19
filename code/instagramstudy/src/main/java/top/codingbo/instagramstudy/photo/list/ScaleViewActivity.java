package top.codingbo.instagramstudy.photo.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import top.codingbo.instagramstudy.R;

/**
 * Created by bob
 * on 2019/6/19.
 */
public class ScaleViewActivity extends AppCompatActivity {
    ScaleImageView mIvImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);

        mIvImage = findViewById(R.id.iv_image);

        mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScaleViewActivity.this, "onClick", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
