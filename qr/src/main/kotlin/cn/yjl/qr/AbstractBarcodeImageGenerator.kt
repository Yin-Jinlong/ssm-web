package cn.yjl.qr

import com.google.zxing.common.BitMatrix
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint
import org.jetbrains.skiko.toBufferedImage
import org.jetbrains.skiko.toImage
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

/**
 *
 * @author YJL
 */
abstract class AbstractBarcodeImageGenerator : BarcodeImageGenerator {

    var graphicsWidth = 0

    var graphicsHeight = 0

    val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    val primaryPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
    }

    abstract fun draw(canvas: Canvas, data: BitMatrix)

    open fun drawBackGround(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
    }

    override fun generate(bitMatrix: BitMatrix, scale: Float): BufferedImage {
        graphicsWidth = (bitMatrix.width * scale).roundToInt()
        graphicsHeight = (bitMatrix.height * scale).roundToInt()
        val image = BufferedImage(graphicsWidth, graphicsHeight, BufferedImage.TYPE_INT_RGB).toImage()
        return Bitmap.makeFromImage(image).let { bm ->
            Canvas(bm).use { canvas ->
                canvas.drawPaint(backgroundPaint)
                draw(canvas, bitMatrix)
                bm.toBufferedImage()
            }
        }
    }

    fun setPrimaryColor(color: Int) {
        primaryPaint.color = color
    }

    fun setBackGroundColor(color: Int) {
        backgroundPaint.color = color
    }
}
