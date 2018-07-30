package codingbo.customviewstudy;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import codingbo.customviewstudy.view.TypeEvaluatorView;

public class MainActivity extends AppCompatActivity {

    TypeEvaluatorView tev;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tev = (TypeEvaluatorView) findViewById(R.id.tev);
        bt = (Button) findViewById(R.id.bt);

    }


    public void start(View view) {
        ObjectAnimator animator = ObjectAnimator.ofInt(tev, "color", 0xffff0000, 0xff0000ff).setDuration(2000);
        animator.setEvaluator(new HsvEvaluator());
        animator.start();

//        ObjectAnimator animator = ObjectAnimator.ofArgb(tev, "color", 0xffff0000, 0xff00ff00).setDuration(2000);
//        animator.start();

        ObjectAnimator animator2 = ObjectAnimator.ofObject(tev, "position", new PointFEvaluator(),
                new PointF(0, 0), new PointF(500, 500)).setDuration(2000);
        animator2.start();

//        bt.animate()
//                .scaleX(1)
//                .scaleY(1)
//                .alpha(1)
//                .translationX(-800);

//        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.3f, 0.3f);
//        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.3f, 0.3f);
//        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.3f, 0.3f);

//        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(bt, scaleX, scaleY, alpha);
//        animator1.start();

        ObjectAnimator x = ObjectAnimator.ofFloat(bt, "scaleX", 1f, 0f);
        ObjectAnimator y = ObjectAnimator.ofFloat(bt, "scaleY", 1f, 0f);
        ObjectAnimator a = ObjectAnimator.ofFloat(bt, "alpha", 1f, 0);
        ObjectAnimator tx = ObjectAnimator.ofFloat(bt, "translationX", -800);

        AnimatorSet set = new AnimatorSet();
//        set.playTogether(x, y, a);
//        set.playSequentially(x, y, a);
//        set.play(x).with(y);
//        set.play(x).after(a);
//        set.play(y).after(x);
        set.play(tx);
//        set.start();
    }


    class HsvEvaluator implements TypeEvaluator<Integer> {

        float[] startHsv = new float[3];
        float[] endHsv = new float[3];
        float[] outHsv = new float[3];

        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int result = 0;
           /*
            int a = getData(fraction, getAlpha(startValue), getAlpha(endValue));
            int r = getData(fraction, getRed(startValue), getRed(endValue));
            int g = getData(fraction, getGreen(startValue), getGreen(endValue));
            int b = getData(fraction, getBlue(startValue), getBlue(endValue));
            result = a << 24 | r << 16 | g << 8 | b;
            */

            Color.colorToHSV(startValue, startHsv);
            Color.colorToHSV(endValue, endHsv);

            outHsv[0] = (endHsv[0] - startHsv[0]) * fraction + startHsv[0];
            outHsv[1] = (endHsv[1] - startHsv[1]) * fraction + startHsv[1];
            outHsv[2] = (endHsv[2] - startHsv[2]) * fraction + startHsv[2];

            result = Color.HSVToColor(outHsv);
            return result;
        }
    }

    private int getData(float fraction, Integer startValue, Integer endValue) {
        if (Objects.equals(startValue, endValue)) {
            return startValue;
        }
        return (int) ((endValue - startValue) * fraction + startValue);
    }

    private int getAlpha(int color) {
        return color >> 24 & 0xFF;
    }

    private int getRed(int color) {
        return color >> 16 & 0xFF;
    }

    private int getGreen(int color) {
        return color >> 8 & 0xFF;
    }

    private int getBlue(int color) {
        return color & 0xFF;
    }


}
