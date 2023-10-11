package cn.yjl.qr

import com.google.zxing.common.BitMatrix
import org.jetbrains.skia.*
import org.jetbrains.skiko.toBufferedImage
import java.awt.image.BufferedImage
import java.lang.IllegalStateException
import kotlin.math.roundToInt

/**
 * 抽象二维码生成器
 *
 * 提供绘制接口
 *
 * @author YJL
 */
abstract class AbstractBarcodeImageGenerator : BarcodeImageGenerator {

    /**
     * 当前图片宽度，初始时为0
     */
    var graphicsWidth = 0

    /**
     * 当前图片高度，初始时为0
     */
    var graphicsHeight = 0

    /**
     * 背景Paint，白色，默认开启抗锯齿
     */
    val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    /**
     * 主要Paint，黑色，默认开启抗锯齿
     */
    val primaryPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
    }

    /**
     * 绘制有效点（矩阵中的true)
     *
     * @param canvas 画布
     * @param data 数据矩阵
     */
    abstract fun draw(canvas: Canvas, data: BitMatrix)

    /**
     * 绘制背景
     */
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

    /**
     * 设置主要画笔颜色
     *
     * @param color 颜色
     */
    fun setPrimaryColor(color: Int) {
        primaryPaint.color = color
    }

    /**
     *
     * 设置背景画笔颜色
     *
     * @param color 颜色
     */
    fun setBackGroundColor(color: Int) {
        backgroundPaint.color = color
    }
}
