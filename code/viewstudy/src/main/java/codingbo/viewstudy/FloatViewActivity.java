package codingbo.viewstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

/**
 * Created by bob
 * on 2018/8/8.
 */
public class FloatViewActivity extends AppCompatActivity {
    private static final String TAG = "FloatViewActivity";
    Button mBtBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_view);

        mBtBack = findViewById(R.id.bt_back);

        mBtBack.setOnClickListener(v -> Log.d(TAG, "onClick: "));
    }
}
