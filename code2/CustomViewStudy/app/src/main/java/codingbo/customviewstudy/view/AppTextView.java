package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by bob
 * on 17.8.21.
 */

public class AppTextView extends TextView {

    public AppTextView(Context context) {
        super(context);
    }

    public AppTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Paint mPaint;

    {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        TextPaint paint = getPaint();
//        float size = paint.getTextSize();
//
//        float count = getWidth() / size;
//
//        for (int i = 0; i < count; i++) {
//            if (i % 2 == 0) {
//                float l = 0;
//                float t = i * count;
//                float r = getWidth();
//                float b = ++i * count;
//                canvas.drawRect(l, t, r, b, mPaint);
//            }
//        }

//        CharSequence text = getText();

//        TextPaint paint = getPaint();

        super.onDraw(canvas);

    }
}
