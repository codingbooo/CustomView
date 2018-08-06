package com.bob.bigimage;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageView iv;

    int[] images = {
            R.drawable.image,
            R.drawable.image_default,
            R.drawable.image_m,
            R.drawable.image_h,
            R.drawable.image_xh,
            R.drawable.image_xxh,
            R.drawable.image_xxxh};

    Bitmap[] bitmaps = new Bitmap[images.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);

        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        Log.d(TAG, "maxMemory: " + maxMemory + "MB");
        iv.post(new Runnable() {
            @Override
            public void run() {
                setData();
            }
        });
    }


    private void setData() {

        for (int i = 0; i < images.length; i++) {
            Bitmap bitmap = getBitmap(getResources(), images[i], iv.getWidth(), iv.getHeight());
            int imageSize = bitmap.getByteCount() / 1024;
            bitmaps[i] = bitmap;
            Log.d(TAG, "image size: " + imageSize + "KB"
                    + ", bitmap:" + bitmap.getWidth() + " : " + bitmap.getHeight()
            );

        }
        iv.setImageBitmap(bitmaps[6]);

        AssetManager assets = getAssets();
        try {
            InputStream open = assets.open("image.png");
            InputStream in = assets.open("image.png");
            Bitmap bitmap = getBitmap(open, in, iv.getWidth(), iv.getHeight());
            int imageSize = bitmap.getByteCount() / 1024;
            Log.d(TAG, "image size: " + imageSize + "KB"
                    + ", bitmap:" + bitmap.getWidth() + " : " + bitmap.getHeight()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmap(InputStream in1, InputStream in2, int vWidth, int vHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Rect padding = new Rect(0, 0, 0, 0);
        BitmapFactory.decodeStream(in1, padding, options);
        setOption(vWidth, vHeight, options);

        return BitmapFactory.decodeStream(in2, padding, options);
    }

    private Bitmap getBitmap(Resources resources, int resId, int vWidth, int vHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);

        setOption(vWidth, vHeight, options);
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    private void setOption(int vWidth, int vHeight, BitmapFactory.Options options) {
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        int inSimpleSize = 1;
        if (imageWidth > vWidth || imageHeight > vHeight) {
            int widRatio = imageWidth / vWidth;
            int heiRatio = imageHeight / vHeight;
            inSimpleSize = widRatio > heiRatio ? heiRatio : widRatio;
        }
        options.inSampleSize = inSimpleSize;
        options.inJustDecodeBounds = false;

        Log.d(TAG, "outImage: " + imageWidth + " : " + imageHeight
                + ", view: " + vWidth + " : " + vHeight
                + ", outMimeType: " + options.outMimeType
                + ", inPreferredConfig: " + options.inPreferredConfig
                + ", inSampleSize: " + options.inSampleSize
        );
    }
}
