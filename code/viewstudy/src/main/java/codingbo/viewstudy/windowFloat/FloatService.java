package codingbo.viewstudy.windowFloat;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import codingbo.viewstudy.R;

/**
 * Created by bob
 * on 2018/8/9.
 */
public class FloatService extends Service {
    private static final String TAG = "FloatService";

    @Override
    public void onCreate() {
        super.onCreate();
        createView();
    }

    private void createView() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        View view = LayoutInflater.from(getApplication()).inflate(R.layout.layout_float, null);

        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        lp.x = 200;
        lp.y = 200;

//        view.setLayoutParams(lp);

        WindowManager wm = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);

        wm.addView(view, lp);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
