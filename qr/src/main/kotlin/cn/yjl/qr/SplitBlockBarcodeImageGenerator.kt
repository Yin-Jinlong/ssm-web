package cn.yjl.qr

import com.google.zxing.common.BitMatrix
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color

/**
 * 分离块二维码生成器
 *
 * 分离数据块绘制
 *
 * @author YJL
 */
abstract class SplitBlockBarcodeImageGenerator : AbstractBarcodeImageGenerator() {

    /**
     * 单个块宽度，初始为-1f
     */
    var blockWidth: Float = -1f

    /**
     * 单个块高度，初始为-1f
     */
    var blockHeight: Float = -1f

    lateinit var canvas: Canvas

    /**
     * 数据矩阵
     */
    lateinit var data: BitMatrix
        internal set

    /**
     * 绘制为true时的块
     *
     * @param x data x
     * @param y data y
     */
    abstract fun draw(x: Int, y: Int)

    /**
     * 更新信息
     */
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