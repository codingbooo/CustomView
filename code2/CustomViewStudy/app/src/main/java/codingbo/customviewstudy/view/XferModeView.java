package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import codingbo.customviewstudy.R;

/**
 * Created by bob
 * on 17.7.18.
 */

public class XferModeView extends View {
    public XferModeView(Context context) {
        super(context);
    }

    public XferModeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XferModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint = new Paint();

        setLayerType(LAYER_TYPE_SOFTWARE, null);

//        int saveLayer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        Bitmap rect = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap circle = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

//        canvas.drawBitmap(circle, 100, 100, paint);
        canvas.drawBitmap(rect, 0, 0, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(rect, 50, 50, paint);
        paint.setXfermode(null);


//        canvas.restoreToCount(saveLayer);
    }
}
