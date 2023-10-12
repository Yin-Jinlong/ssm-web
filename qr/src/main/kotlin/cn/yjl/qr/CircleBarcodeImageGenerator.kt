package cn.yjl.qr

import cn.yjl.util.drawOval

/**
 * 圆点二维码图片生成器（替换方块为圆）
 *
 * @author YJL
 */
class CircleBarcodeImageGenerator : AbstractBarcodeImageGenerator() {

    override fun draw(x: Int, y: Int) {
        canvas.drawOval(x.toFloat(), y.toFloat(), 1f, 1f, primaryPaint)
    }

}