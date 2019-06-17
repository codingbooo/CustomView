package top.codingbo.instagramstudy;

import android.app.Application;

import com.bilibili.boxing.BoxingMediaLoader;

/**
 * Created by boliang
 * on 2019-06-16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BoxingMediaLoader.getInstance().init(new BoxingGlideLoader());
    }
}
