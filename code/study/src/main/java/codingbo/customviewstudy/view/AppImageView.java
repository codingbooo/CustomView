package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import codingbo.customviewstudy.BuildConfig;

/**
 * Created by bob
 * on 17.8.21.
 */

public class AppImageView extends ImageView {

    public AppImageView(Context context) {
        super(context);
    }

    public AppImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint mPaint;

    {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (BuildConfig.DEBUG) {
            Drawable drawable = getDrawable();
            Rect bounds = drawable.getBounds();

            String content = bounds.width() + "X" + bounds.height();
            canvas.drawText(content, 10, 50, mPaint);

        }
    }
}
