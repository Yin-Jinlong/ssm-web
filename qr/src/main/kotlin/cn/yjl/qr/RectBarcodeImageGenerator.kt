package cn.yjl.qr

import cn.yjl.util.drawRect

/**
 * 常规二维码图片生成器
 *
 * @author YJL
 */
class RectBarcodeImageGenerator : AbstractBarcodeImageGenerator() {

    override fun draw(x: Int, y: Int) {
        canvas.drawRect(x.toFloat(), y.toFloat(), 1f, 1f, primaryPaint)

    }

}