package cn.yjl.qr

import java.awt.Color

/**
 *
 * @author YJL
 */
class CircleBarcodeImageGenerator(
    primaryColor: Color = Color.BLACK,
    backgroundColor: Color = Color.WHITE
) : SplitBlockBarcodeImageGenerator(primaryColor, backgroundColor) {

    override fun draw(x: Int, y: Int) {
        graphics.fillOval(x * blockWidth, y * blockHeight, blockWidth, blockHeight)
    }

}