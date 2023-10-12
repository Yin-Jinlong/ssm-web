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

    lateinit var canvas: Canvas

    /**
     * 数据矩阵
     */
    lateinit var data: BitMatrix
        internal set

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

    internal fun saveDraw(draw: () -> Unit) {
        val c = canvas.save()
        draw()
        canvas.restoreToCount(c)
    }

    /**
     * 绘制有效点（矩阵中的true)
     */
    open fun draw() {

        for (y in 0..<data.height)
            for (x in 0..<data.width)
                if (data[x, y])
                    saveDraw {
                        draw(x, y)
                    }
    }

    /**
     * 绘制背景
     */
    open fun drawBackGround(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
    }

    /**
     * 绘制点，宽度为1
     *
     * @param x data x
     * @param y data y
     */
    abstract fun draw(x: Int, y: Int)
    override fun generate(bitMatrix: BitMatrix, scale: Float): BufferedImage {
        val graphicsWidth = (bitMatrix.width * scale).roundToInt()
        val graphicsHeight = (bitMatrix.height * scale).roundToInt()
        return Bitmap().apply {
            if (!allocN32Pixels(graphicsWidth, graphicsHeight, true)) {
                close()
                throw IllegalStateException("Alloc bitmap failed")
            }
        }.let { bm ->
            Canvas(bm).use { canvas ->
                this.data = bitMatrix
                this.canvas = canvas
                canvas.scale(graphicsWidth.toFloat() / data.width, graphicsHeight.toFloat() / data.height)
                drawBackGround(canvas)
                draw()
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
