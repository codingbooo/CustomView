package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bob
 * on 17.7.28.
 */

public class DrawWaterMarkView extends View {

    public DrawWaterMarkView(Context context) {
        super(context);
    }

    public DrawWaterMarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawWaterMarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        TextPaint paint = new TextPaint();

        String content = "中国内蒙古自治区乌兰察布市卓资县运煤专线";

        int width = getWidth();
        int height = getHeight();
        int margin = 10;
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        Rect rect = new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
//        canvas.drawText(content, margin, height - margin, paint);

        StaticLayout layout = new StaticLayout(content, paint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);

        layout.draw(canvas);
    }
}
