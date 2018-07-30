package codingbo.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Locale;

/**
 * Created by bob
 * on 17.7.18.
 */

public class DrawTextView extends View {
    public DrawTextView(Context context) {
        super(context);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(100);
        String content = "Hello World";
//        canvas.drawText(content, 200, 100, paint);
//        canvas.drawText(content, 0, 5, 200, 200, paint);
//        canvas.drawText(content.toCharArray(), 1, 4, 200, 300, paint);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            canvas.drawTextRun(content, 0, 11, 0, 11, 200, 400, false, paint);
//        }

        Path path = new Path();

        path.moveTo(0, 500);
//        path.lineTo(100, 600);
//        path.lineTo(400, 500);
//        path.lineTo(600, 800);
//        path.quadTo(0,500,100,600);
//        path.quadTo(100,600,400,500);
//        path.quadTo(400,500,600,800);

//        path.cubicTo(0,500,100,600,400,500);
//        path.cubicTo(100,600,400,500,600,800);

//        path.quadTo(100, 600, 400, 500);
//        path.moveTo(100, 600);
//        path.quadTo(400, 500, 600, 800);

        path.cubicTo(100, 800, 400, 500, 600, 800);


//        paint.setStyle(Paint.Style.STROKE);
//        CornerPathEffect effect = new CornerPathEffect(50);
//        paint.setPathEffect(effect);
//        paint.setTextSize(100);
//        canvas.drawPath(path, paint);
//        paint.setPathEffect(null);
//        canvas.drawTextOnPath(content, path, 20, 0, paint);

        String txt = "Momma always said:\n\"Life is like a box of chocolates,Forrest." +
                "You never know what you're gonna get.\"\nMother:It's my time." +
                "It's just my time.Oh,now,don't you be afraid sweetheart." +
                "Death is just a part of life,something we're all destined to do.I didn't know it." +
                "But I was destined to be your momma." +
                "I did the best I could.Jenny:Are you stupid or something?" +
                "Forrest:Momma says that stupid is as stupid does.";

//        canvas.drawText(txt, 200, 1000, paint);


//        TextPaint textPaint = new TextPaint();
//        textPaint.setTextSize(60);
//        StaticLayout staticLayout = new StaticLayout(txt, textPaint, 900, Layout.Alignment.ALIGN_NORMAL, 0, 1, true);
//        staticLayout.draw(canvas);


//        paint.setTextSize(25);
//        paint.setFontFeatureSettings("smcp");
//        paint.setTypeface(Typeface.DEFAULT);
//        canvas.drawText(content,100,25,paint);
//        paint.setTextSize(50);
//        paint.setTypeface(Typeface.DEFAULT_BOLD);
//        canvas.drawText(content,100,75,paint);
//        paint.setTextSize(100);
//        paint.setTypeface(Typeface.SANS_SERIF);
//        canvas.drawText(content,100,175,paint);
//        paint.setTextSize(200);
//        paint.setTypeface(Typeface.MONOSPACE);
//        canvas.drawText(content,100,375,paint);
//        paint.setTextSize(400);
//        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"consola.ttf"));
//        paint.setFakeBoldText(true);
//        paint.setTextScaleX(1.5f);
//        paint.setLetterSpacing(0.5f);
//        canvas.drawText(content,100,775,paint);
//        paint.setTextSize(800);
//        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"samplefont.ttf"));
//        paint.setFakeBoldText(false);
//        paint.setStrikeThruText(true);
//        paint.setUnderlineText(true);
//        paint.setTextSkewX(-0.5f);
//        canvas.drawText(content,100,1575,paint);

//        paint.setTextSize(100);
//        canvas.drawText(content, 700, 100, paint);
//        paint.setTextAlign(Paint.Align.LEFT);
//        canvas.drawText(content, 700, 300, paint);
//        paint.setTextAlign(Paint.Align.CENTER);
//        canvas.drawText(content, 700, 500, paint);
//        paint.setTextAlign(Paint.Align.RIGHT);
//        canvas.drawText(content, 700, 700, paint);
//        canvas.drawText(content, 700, 900, paint);
//        canvas.drawLine(700, 0, 700, 1000, paint);

//        String china = "雨骨底条今直沿微写值";
//
//        content = china;
//        paint.setTextSize(100);
//
//        canvas.drawText(content, 100, 100, paint);
//        paint.setTextLocale(Locale.CHINA);
//        canvas.drawText(content, 100, 300, paint);
//        paint.setTextLocale(Locale.TAIWAN);
//        canvas.drawText(content, 100, 500, paint);
//        paint.setTextLocale(Locale.SIMPLIFIED_CHINESE);
//        canvas.drawText(content, 100, 700, paint);
//        paint.setTextLocale(Locale.CHINESE);
//        canvas.drawText(content, 100, 900, paint);
//        paint.setTextLocale(Locale.JAPAN);
//        canvas.drawText(content, 100, 1100, paint);


//        paint.setTextSize(100);
//        Rect rect = new Rect();
//        paint.getTextBounds(content, 0, content.length(), rect);
//        float textWid = paint.measureText(content);
//        canvas.drawText(content, 100, 200, paint);
//
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.translate(100, 200);
//        canvas.drawRect(rect, paint);
//        canvas.drawLine(0, 0, textWid, 0, paint);

//        paint.setTextSize(100);
//        paint.setStyle(Paint.Style.STROKE);
//        int width = 60;
//        int height = 1000;
//        canvas.drawRect(0, 0, width, height, paint);
//
//        float[] floats = new float[]{};
//
//        String temp = content;
//        int th = 1;
//        int subLength = 0;
//        int len = content.length();
//        do {
//            temp = temp.substring(subLength, temp.length());
//            subLength = paint.breakText(temp, true, width, floats);
//            if (subLength <= 0) {
//                break;
//            }
//            canvas.drawText(temp.substring(0, subLength), 0, 100 * th, paint);
//            len -= subLength;
//            th++;
//        } while (len > 0);


//        String text = content;
//        int length = text.length();
//        int offsetX = 100;
//        int offsetY = 100;
//        float advance = 0;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            advance = paint.getRunAdvance(text, 0, length, 0, length, false, length);
//        }
//        canvas.drawText(text, offsetX, offsetY, paint);
//        canvas.drawLine(offsetX + advance, offsetY - 50, offsetX + advance, offsetY + 10, paint);

        String text = "Hello HenCoder \uD83C\uDDE8\uD83C\uDDF3"; // "Hello HenCoder 🇨🇳"
        int length = text.length();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            float advance1 = paint.getRunAdvance(text, 0, length, 0, length, false, length);
            float advance2 = paint.getRunAdvance(text, 0, length, 0, length, false, length - 1);
            float advance3 = paint.getRunAdvance(text, 0, length, 0, length, false, length - 2);
            float advance4 = paint.getRunAdvance(text, 0, length, 0, length, false, length - 3);
            float advance5 = paint.getRunAdvance(text, 0, length, 0, length, false, length - 4);
            float advance6 = paint.getRunAdvance(text, 0, length, 0, length, false, length - 5);
            canvas.drawText(text, 100, 100, paint);
            canvas.drawText(text, 100, 200, paint);
            canvas.drawText(text, 100, 300, paint);
            canvas.drawText(text, 100, 400, paint);
            canvas.drawText(text, 100, 500, paint);
            canvas.drawText(text, 100, 600, paint);
            canvas.drawLine(100 + advance1, 0, 100 + advance1, 100, paint);
            canvas.drawLine(100 + advance2, 100, 100 + advance2, 200, paint);
            canvas.drawLine(100 + advance3, 200, 100 + advance3, 300, paint);
            canvas.drawLine(100 + advance4, 300, 100 + advance4, 400, paint);
            canvas.drawLine(100 + advance5, 400, 100 + advance5, 500, paint);
            canvas.drawLine(100 + advance6, 500, 100 + advance6, 600, paint);
        }

    }
}
