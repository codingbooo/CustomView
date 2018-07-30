package codingbo.viewstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import codingbo.viewstudy.thumb.thumbView.NumberView;
import codingbo.viewstudy.thumb.thumbView.ThumbView;

/**
 * Created by bob
 * on 18.2.11.
 */

public class ThumbUpActivity extends AppCompatActivity {

    ThumbView tv3;
    NumberView numberView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb);

        tv3 = findViewById(R.id.tv3);
        numberView = findViewById(R.id.numberView);

        numberView.setCount(12345688);
    }


    public void thumbSwitch(View view) {
        tv3.setLiked(!tv3.isLiked());
        if (tv3.isLiked()) {
            numberView.animUp();
        } else {
            numberView.animDown();
        }

    }
}
