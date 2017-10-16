package codingbo.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import codingbo.viewlibrary.unreadView.CountView;

/**
 * Created by bob
 * on 17.9.25.
 */

public class UnreadViewActivity extends AppCompatActivity {
    private static final String TAG = "UnreadViewActivity";
    CountView cv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unread);

        cv = (CountView) findViewById(R.id.cv);

        cv.setText("13");

        cv.setStatusListener(new CountView.StatusListener() {

            @Override
            public void onHide() {
                Log.d(TAG, "onHide: ");
            }
        });
    }
}
