package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import codingbo.customviewstudy.R;

/**
 * Created by bob
 * on 17.7.17.
 */

public class PaintStudyView extends View {


    public PaintStudyView(Context context) {
        super(context);
    }

    public PaintStudyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintStudyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#ff456789"));

        canvas.drawRect(200, 200, 400, 300, paint);

        paint.setTextSize(50);
        Rect rect = new Rect();
        String content = "Hello World!";
        paint.getTextBounds(content, 0, content.length(), rect);
        canvas.drawText(content, 500, 300, paint);
        int height = rect.height();
        int width = rect.width();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(500, 300 - height, width + 500, 300, paint);


//        Shader shader = new LinearGradient(100, 500, 500, 900, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        Shader shader = new LinearGradient(100, 0, 500, 0, Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(300, 700, 200, paint);

        RadialGradient gradient = new RadialGradient(800, 700, 200, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        canvas.drawCircle(800, 700, 200, paint);

        SweepGradient sweep = new SweepGradient(300, 1100, Color.RED, Color.BLUE);
        paint.setShader(sweep);
        canvas.drawCircle(300, 1100, 200, paint);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wyj);
//        int h = bitmap.getHeight();
//        int w = bitmap.getWidth();
//        Log.d("haha", "height: " + h);
//        Log.d("haha", "width: " + w);
//        bitmap.setWidth(30);
//        bitmap.setHeight(30);

        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT);
        paint.setShader(bitmapShader);
//        canvas.drawCircle(350, 350, 350, paint);


//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_android);
//        BitmapShader bitmapShader2 = new BitmapShader(bitmap1, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//        paint.setShader(bitmapShader2);
//        canvas.drawCircle(350, 350, 350, paint);

        //关闭硬件加速
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        ComposeShader composeShader = new ComposeShader(bitmapShader, bitmapShader2, PorterDuff.Mode.MULTIPLY);
//        paint.setShader(composeShader);


//        LightingColorFilter colorFilter = new LightingColorFilter(0x77aaff, 0x330000);


//        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(0x770000, PorterDuff.Mode.ADD);

//        ColorMatrix matrix = new ColorMatrix();

//        matrix.setRGB2YUV();
//        matrix.setYUV2RGB();
//        matrix.setSaturation(8);
//        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(matrix);

//        paint.setColorFilter(colorFilter);
        canvas.drawCircle(350, 350, 350, paint);
    }
}
