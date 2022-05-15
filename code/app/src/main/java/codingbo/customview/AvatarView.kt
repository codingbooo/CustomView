package codingbo.customview

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Created by boliang
 * on 2022/5/14.
 */
class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    private var borderSize = 0F
    private var borderColor = Color.WHITE
    private var shadowSize = 0F
    private var shadowColor = Color.RED
    private var shadowDx = 0F
    private var shadowDy = 0F

    init {
        attrs?.let {
            initAttrs(it)
        }
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }


    private val rectF by lazy { RectF() }
    private val xfermode by lazy { PorterDuffXfermode(PorterDuff.Mode.SRC_IN) }

    private val paint by lazy {
        Paint(ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }
    }
    private val borderPaint by lazy {
        Paint(ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
        }
    }
    private val shadowPaint by lazy {
        Paint(ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }
    }


    private fun initAttrs(attrs: AttributeSet) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarView)
        borderSize = typedArray.getDimension(R.styleable.AvatarView_avatar_borderSize, 0F)
        borderColor = typedArray.getColor(R.styleable.AvatarView_avatar_borderColor, Color.WHITE)

        shadowSize = typedArray.getDimension(R.styleable.AvatarView_avatar_shadowSize, 0F)
        shadowColor = typedArray.getColor(R.styleable.AvatarView_avatar_shadowColor, Color.RED)
        shadowDx = typedArray.getDimension(R.styleable.AvatarView_avatar_shadowDx, 0F)
        shadowDy = typedArray.getDimension(R.styleable.AvatarView_avatar_shadowDy, 0F)

        typedArray.recycle()
    }


    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) {
            return
        }

        val cx = measuredWidth / 2F
        val cy = measuredHeight / 2F
        val radius = min(measuredWidth, measuredHeight) / 2F



        rectF.set(
            0F,
            0F,
            measuredWidth.toFloat(),
            measuredHeight.toFloat()
        )


        val ringRadius = radius - shadowSize - borderSize / 2 - max(abs(shadowDx), abs(shadowDy))

        // draw shadow
        if (shadowSize > 0) {
            shadowPaint.setShadowLayer(shadowSize, shadowDx, shadowDy, shadowColor)
            shadowPaint.color = shadowColor
            shadowPaint.strokeWidth = shadowSize
            canvas.drawCircle(
                cx, cy,
                ringRadius,
                shadowPaint
            )
        }


        val offset = radius - ringRadius + borderSize / 2

        // draw avatar
        val saved = canvas.saveLayer(rectF, paint)
        canvas.drawCircle(cx, cy, radius - offset, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(
            getBitmap(
                drawable,
                measuredWidth - offset.toInt() * 2,
                measuredHeight - offset.toInt() * 2
            ), offset, offset, paint
        )
        paint.xfermode = null
        canvas.restoreToCount(saved)


        // draw border
        if (borderSize > 0) {
            borderPaint.color = borderColor
            borderPaint.strokeWidth = borderSize
            canvas.drawCircle(
                cx, cy,
                ringRadius,
                borderPaint
            )
        }


        // debug: draw view border
//        canvas.drawRect(
//            rectF,
//            Paint(ANTI_ALIAS_FLAG).apply {
//                style = Paint.Style.STROKE
//            }
//        )


    }

    private fun getBitmap(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
//        Log.d(TAG, "getBitmap: drawable = ${drawable.intrinsicWidth} , ${drawable.intrinsicHeight}")
//        Log.d(TAG, "getBitmap: bitmap = ${bitmap.width} , ${bitmap.height}")
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, bitmap.width, bitmap.height)
        drawable.draw(canvas)
        return bitmap
    }


    companion object {
        private const val TAG = "AvatarView"
    }

}