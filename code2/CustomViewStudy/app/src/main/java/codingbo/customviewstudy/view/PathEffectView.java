package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bob
 * on 17.7.18.
 */

public class PathEffectView extends View {
    public PathEffectView(Context context) {
        super(context);
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setAntiAlias(true);

//        DashPathEffect dashEffect = new DashPathEffect(new float[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 70);
//        CornerPathEffect effect = new CornerPathEffect(100);
//        DiscretePathEffect discreteEffect = new DiscretePathEffect(50, 50);

//        Path p = new Path();
//        p.moveTo(10, 0);
//        p.lineTo(20, 20);
//        p.lineTo(0, 20);
//        p.close();

//        PathDashPathEffect effect = new PathDashPathEffect(p, 40, 0, PathDashPathEffect.Style.MORPH);

//        SumPathEffect effect = new SumPathEffect(dashEffect, discreteEffect);

//        ComposePathEffect effect = new ComposePathEffect(discreteEffect, dashEffect);
//        paint.setPathEffect(effect);

//        canvas.drawLine(500, 500, 900, 500, paint);
//        canvas.drawCircle(500, 500, 400, paint);


//        Path path = new Path();

//        path.moveTo(50, 50);
//        path.lineTo(300, 300);
//        path.lineTo(500, 30);
//        path.lineTo(700, 600);
//        path.lineTo(1000, 200);
//        path.lineTo(1500, 1000);
//        canvas.drawPath(path, paint);


//--------------------------------------------------------------------------


        Paint paint = new Paint();
        paint.setTextSize(200);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint.setShadowLayer(30, 0, 0, Color.BLUE);


//        canvas.drawText("hello world!", 200, 300, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        Path path = new Path();
        path.moveTo(50, 50);
        path.lineTo(300, 300);
        path.lineTo(500, 30);
        path.lineTo(700, 600);
        path.lineTo(1000, 200);
        path.lineTo(1500, 1000);
//        canvas.drawPath(path, paint);

        paint.clearShadowLayer();
//        canvas.drawText("hello world!", 200, 1000, paint);

        Path real = new Path();
        paint.getFillPath(path, real);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        canvas.drawPath(real, paint);
    }
}
