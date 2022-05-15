package codingbo.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas

/**
 * Created by boliang
 * on 2022/5/15.
 */
fun getShapeBitmap(context: Context, resourceId: Int, width: Int, height: Int): Bitmap {

    val drawable = context.getDrawable(resourceId)
        ?: return Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888)

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(bitmap)

//    canvas.scale(
//        drawable.intrinsicWidth / width.toFloat(),
//        drawable.intrinsicHeight / height.toFloat()
//    )
    drawable.setBounds(0, 0, width, height)
    drawable.draw(canvas)

    return bitmap
}