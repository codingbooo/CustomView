package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import codingbo.customviewstudy.R;

/**
 * Created by bob
 * on 17.8.8.
 */

public class CanvasStudyView extends View {

    public CanvasStudyView(Context context) {
        super(context);
    }

    public CanvasStudyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasStudyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint mPaint;

    private Bitmap mBitmap;
    private Bitmap mBitmap2;

    private Path mPath1;
    private Path mPath2;

    {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scarlett);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        mPath1 = new Path();
        mPath2 = new Path();

        mPath1.addCircle(200, 200, 100, Path.Direction.CW);

        mPath2.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        mPath2.addCircle(200, 200, 100, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //裁剪画布
//        int save = canvas.save();
//        canvas.clipRect(100, 100, 400, 400);
//        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//        canvas.restore();
//        canvas.drawBitmap(mBitmap, 400, 400, mPaint);

        //裁剪画布
//        canvas.save();
//        canvas.clipPath(mPath1);
//        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//        canvas.restore();

        //裁剪画布 根据路径
//        canvas.save();
//        canvas.clipPath(mPath2);
//        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//        canvas.restore();

        //画布平移
//        canvas.save();
//        canvas.translate(500, 0);
//        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//        canvas.restore();

        //画布旋转
//        canvas.save();
//        canvas.rotate(45, 100, 100);
//        canvas.translate(300, 0);
//        canvas.drawLine(100, 100, 300, 300, mPaint);
//        canvas.drawBitmap(mBitmap2, 300, 300, mPaint);
//        canvas.restore();
//        canvas.drawBitmap(mBitmap2, 300, 300, mPaint);
        //画布缩放
//        canvas.save();
//        canvas.scale(1f, 1.2f, 300f, 300f);
//        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//        canvas.restore();

        //画布错切
//        canvas.save();
//        canvas.skew(0f, 0.3f);
//        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//        canvas.restore();


//        Matrix matrix = new Matrix();
//        matrix.postTranslate(300, 0);
//        matrix.preRotate(30);
//        matrix.preTranslate(0, 300);
//
//        canvas.save();
//        canvas.setMatrix(matrix);
//        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//        canvas.restore();
//        canvas.drawRect(100, 100, 200, 200, mPaint);
//        canvas.drawCircle(0, 0, 100, mPaint);


//        float left = getLeft();
//        float top = getTop();
//        float right = getRight();
//        float bottom = getBottom();

//        float[] src = {left, top, right, top, left, bottom, right, bottom};
//        float[] dis = {left + 300, top + 700, right - 300, top + 700, left + 50, bottom, right + 50, bottom};

//        Matrix matrix = new Matrix();
//        matrix.setPolyToPoly(src, 0, dis, 0, 4);

//        canvas.save();
//        canvas.setMatrix(matrix);
//        canvas.concat(matrix);
//        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//        canvas.restore();


        //camera-------------------------------------------------------------------------------------


        int width = mBitmap2.getWidth();
        int height = mBitmap2.getHeight();

        Camera camera = new Camera();


//        camera.save();
//        camera.rotate(30, 0, 0);
//        canvas.translate(width / 2, height / 2);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-width / 2, -height / 2);
//        camera.restore();

        drawAndroidX(canvas, camera, 45);
        drawAndroidX(canvas, camera, 40);
        drawAndroidX(canvas, camera, 35);
        drawAndroidX(canvas, camera, 15);
        drawAndroidX(canvas, camera, 25);
        drawAndroidX(canvas, camera, 20);
        drawAndroidX(canvas, camera, 30);
        drawAndroidX(canvas, camera, 10);
        drawAndroidX(canvas, camera, 5);
        drawAndroidX(canvas, camera, 0);

    }

    private void drawAndroidZ(Canvas canvas, Camera camera, int z) {
        canvas.save();
        camera.save();
        camera.rotate(0, 0, z);
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(mBitmap2, 0, 0, mPaint);
        camera.restore();
        canvas.restore();
    }

    private void drawAndroidY(Canvas canvas, Camera camera, int y) {
        canvas.save();
        camera.save();
        camera.rotate(0, y, 0);
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(mBitmap2, 0, 0, mPaint);
        camera.restore();
        canvas.restore();
    }
    private void drawAndroidX(Canvas canvas, Camera camera, int x) {
        canvas.save();
        camera.save();
        camera.rotate(x, 0, 0);
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(mBitmap2, 0, 0, mPaint);
        camera.restore();
        canvas.restore();
    }
}
