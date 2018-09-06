package codingbo.viewstudy.utils;

import android.content.res.Resources;

/**
 * Created by bob
 * on 2018/8/29.
 */
public class ScreenInfo {

    private static final double LDPI = 0.75;
    private static final int MDPI = 1;
    private static final double HDPI = 1.5;
    private static final int XHDPI = 2;
    private static final int XXHDPI = 3;
    private static final int XXXHDPI = 4;

    public static String printScreenInfo(Resources resources) {
        StringBuilder screenInfo = new StringBuilder();
        float density = resources.getDisplayMetrics().density;
        screenInfo.append("\nfile type:")
                .append(getFileType(density));
        screenInfo.append("\ndensity:")
                .append(density);

        int dpi = resources.getDisplayMetrics().densityDpi;

        screenInfo.append("\ndpi:")
                .append(dpi);

        float scaledDensity = resources.getDisplayMetrics().scaledDensity;
        screenInfo.append("\nscaledDensity:")
                .append(scaledDensity);

        int heightPixels = resources.getDisplayMetrics().heightPixels;
        int widthPixels = resources.getDisplayMetrics().widthPixels;
        screenInfo.append("\npixels:")
                .append(widthPixels)
                .append(":")
                .append(heightPixels);

        float xdpi = resources.getDisplayMetrics().xdpi;
        float ydpi = resources.getDisplayMetrics().ydpi;

        screenInfo.append("\ndpi:")
                .append(xdpi)
                .append(":")
                .append(ydpi);

        return screenInfo.toString();
    }

    private static String getFileType(float density) {
        String result;
        if (density <= LDPI) {
            result = "ldpi";
        } else if (density >= MDPI) {
            result = "mdpi";
        } else if (density >= HDPI) {
            result = "hdpi";
        } else if (density >= XHDPI) {
            result = "xhdpi";
        } else if (density >= XXHDPI) {
            result = "xxhdpi";
        } else if (density >= XXXHDPI) {
            result = "xxxhdpi";
        } else {
            result = "default";
        }
        return result;
    }
}
