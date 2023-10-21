package cn.yjl.qr

import cn.yjl.qr.drawer.Drawer
import com.google.zxing.common.BitMatrix
import org.jetbrains.skia.*
import org.jetbrains.skiko.toBufferedImage
import java.lang.IllegalStateException

/**
 * 抽象二维码生成器
 *
 * 提供绘制接口
 *
 * @author YJL
 */
abstract class AbstractBarcodeImageGenerator<D : Drawer>(
    val drawer: D
) : BarcodeImageGenerator {


    /**
     * 背景Paint，白色，默认开启抗锯齿
     */
    internal val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    /**
     * 主要Paint，黑色，默认开启抗锯齿
     */
    internal val primaryPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
    }

    internal fun Canvas.saveDrawXY(x: Float, y: Float, draw: Canvas.(Paint) -> Unit) {
        val c = save()
        translate(x, y)
        draw(primaryPaint.makeClone())
        restoreToCount(c)
    }

    /**
     * 绘制有效点（矩阵中的true)
     */
    open fun draw(canvas: Canvas, data: BitMatrix) {
        for (y in 0..<data.height)
            for (x in 0..<data.width)
                if (data[x, y])
                    canvas.saveDrawXY(x.toFloat(), y.toFloat()) {
                        drawer.draw(this, it)
                    }
    }

    /**
     * 绘制背景
     */
    open fun drawBackGround(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
    }

    override fun generate(bitMatrix: BitMatrix, width: Int, height: Int) = Bitmap().apply {
        if (!allocN32Pixels(width, height, true)) {
            close()
            throw IllegalStateException("Alloc bitmap failed")
        }
    }.let { bm ->
        Canvas(bm).use { canvas ->
            canvas.scale(width.toFloat() / bitMatrix.width, height.toFloat() / bitMatrix.height)
            drawBackGround(canvas)
            draw(canvas, bitMatrix)
            bm.toBufferedImage()
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
