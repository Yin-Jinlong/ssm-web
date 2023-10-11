package cn.yjl.qr

import com.google.zxing.common.BitMatrix
import org.jetbrains.skia.*
import org.jetbrains.skiko.toBufferedImage
import java.awt.image.BufferedImage
import java.lang.IllegalStateException
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
        return Bitmap().apply {
            if (!allocN32Pixels(graphicsWidth, graphicsHeight, true)) {
                close()
                throw IllegalStateException("Alloc bitmap failed")
            }
        }.let { bm ->
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
