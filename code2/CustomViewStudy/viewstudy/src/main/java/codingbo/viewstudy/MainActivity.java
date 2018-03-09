package codingbo.viewstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        float density = getResources().getDisplayMetrics().density;
        Log.d(TAG, "density: " + density);

        int dpi = getResources().getDisplayMetrics().densityDpi;
        Log.d(TAG, "dpi: " + dpi);

        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        Log.d(TAG, "scaledDensity: " + scaledDensity);

        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        Log.d(TAG, "heightPixels: " + heightPixels);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        Log.d(TAG, "widthPixels: " + widthPixels);
        float xdpi = getResources().getDisplayMetrics().xdpi;
        Log.d(TAG, "xdpi: " + xdpi);
        float ydpi = getResources().getDisplayMetrics().ydpi;
        Log.d(TAG, "ydpi: " + ydpi);

    }

    public void thumb(View view) {
        startActivity(new Intent(this, ThumbUpActivity.class));
    }
}
