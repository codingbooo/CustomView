package codingbo.top.behaviordemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import codingbo.top.behaviordemo.demo1.Demo1Activity;

/**
 * Created by bob
 * on 2018/11/28.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void demo1(View view) {
        jump(Demo1Activity.class);
    }

    public void demo(View view) {
        jump(BasicActivity.class);
    }

    private void jump(Class cls) {
        startActivity(new Intent(this, cls));
    }
}
