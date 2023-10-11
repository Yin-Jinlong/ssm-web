package cn.yjl.qr

import com.google.zxing.common.BitMatrix
import java.awt.Color
import java.awt.Graphics2D

/**
 *
 * @author YJL
 */
abstract class SplitBlockBarcodeImageGenerator(
    primaryColor: Color = Color.BLACK,
    backgroundColor: Color = Color.WHITE
) : AbstractBarcodeImageGenerator(primaryColor, backgroundColor) {

    var blockWidth: Int = -1

    var blockHeight: Int = -1

    lateinit var graphics: Graphics2D

    lateinit var data: BitMatrix
        internal set

    /**
     * 绘制为true时的块
     *
     * @param x data x
     * @param y data y
     */
    abstract fun draw(x: Int, y: Int)

    internal fun update(graphics: Graphics2D, data: BitMatrix) {

        this.data = data
        this.graphics = graphics
    }

    override fun draw(graphics: Graphics2D, data: BitMatrix) {

        update(graphics, data)

        blockWidth = graphicsWidth / data.width
        blockHeight = graphicsHeight / data.height

        for (y in 0..<data.height)
            for (x in 0..<data.width)
                if (data[x, y])
                    draw(x, y)
    }


}