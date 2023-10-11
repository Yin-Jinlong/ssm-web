package cn.yjl.qr

import cn.yjl.util.drawOval

/**
 *
 * @author YJL
 */
class CircleBarcodeImageGenerator: SplitBlockBarcodeImageGenerator() {

    override fun draw(x: Int, y: Int) {
        canvas.drawOval(x * blockWidth, y * blockHeight, blockWidth, blockHeight,primaryPaint)
    }

}