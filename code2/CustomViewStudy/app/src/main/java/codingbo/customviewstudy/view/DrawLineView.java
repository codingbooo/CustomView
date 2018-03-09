package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bob
 * on 17.7.18.
 */

public class DrawLineView extends View {


    public DrawLineView(Context context) {
        super(context);
        init(null, 0);
    }

    public DrawLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DrawLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int attr) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();

        paint.setStrokeWidth(30);

        canvas.drawLine(50, 50, 300, 50, paint);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(50, 400, 300, 400, paint);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(50, 1000, 300, 1000, paint);

        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(550, 50);
        path.lineTo(800, 50);
        path.lineTo(700, 200);
        canvas.drawPath(path, paint);


        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.BEVEL);
        Path p1 = new Path();
        p1.moveTo(550, 400);
        p1.lineTo(800, 400);
        p1.lineTo(700, 550);
        canvas.drawPath(p1, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        Path p2 = new Path();
        p2.moveTo(550, 1000);
        p2.lineTo(800, 1000);
        p2.lineTo(700, 1150);
        canvas.drawPath(p2, paint);


        paint.setStrokeWidth(50);
        paint.setStrokeMiter(10);
        paint.setStrokeJoin(Paint.Join.MITER);
        Path p3 = new Path();
        p3.moveTo(50, 1200);
        p3.lineTo(550, 1200);
//        p3.moveTo(550, 1200);
        p3.lineTo(300, 1300);
        canvas.drawPath(p3, paint);
//        canvas.drawLine(50, 1200, 550, 1200, paint);
//        canvas.drawLine(570, 1230, 300, 1500, paint);

    }
}
