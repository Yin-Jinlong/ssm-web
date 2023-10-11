package cn.yjl.qr

import java.awt.Color

/**
 *
 * @author YJL
 */
class RectBarcodeImageGenerator(
    primaryColor: Color = Color.BLACK,
    backgroundColor: Color = Color.WHITE
) : SplitBlockBarcodeImageGenerator(primaryColor, backgroundColor) {

    override fun draw(x: Int, y: Int) {
        graphics.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight)
    }

}