package cn.yjl.qr

import cn.yjl.util.drawOval

/**
 * 圆点二维码图片生成器（替换方块为圆）
 *
 * @author YJL
 */
class CircleBarcodeImageGenerator : SplitBlockBarcodeImageGenerator() {

    override fun draw(x: Int, y: Int) {
        canvas.drawOval(x * blockWidth, y * blockHeight, blockWidth, blockHeight, primaryPaint)
    }

}