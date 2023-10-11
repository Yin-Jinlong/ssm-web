package cn.yjl.qr

import com.google.zxing.common.BitMatrix
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color

/**
 *
 * @author YJL
 */
abstract class SplitBlockBarcodeImageGenerator : AbstractBarcodeImageGenerator() {

    var blockWidth: Float = -1f

    var blockHeight: Float = -1f

    lateinit var canvas: Canvas

    lateinit var data: BitMatrix
        internal set

    /**
     * 绘制为true时的块
     *
     * @param x data x
     * @param y data y
     */
    abstract fun draw(x: Int, y: Int)

    internal fun update(canvas: Canvas, data: BitMatrix) {
        this.data = data
        this.canvas = canvas
        blockWidth = graphicsWidth.toFloat() / data.width
        blockHeight = graphicsHeight.toFloat() / data.height
    }

    override fun draw(canvas: Canvas, data: BitMatrix) {

        update(canvas, data)

        for (y in 0..<data.height)
            for (x in 0..<data.width)
                if (data[x, y])
                    draw(x, y)
    }


}