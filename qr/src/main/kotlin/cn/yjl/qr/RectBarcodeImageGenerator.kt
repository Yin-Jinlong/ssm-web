package cn.yjl.qr

import cn.yjl.util.drawRect

/**
 * 常规二维码图片生成器
 *
 * @author YJL
 */
class RectBarcodeImageGenerator : SplitBlockBarcodeImageGenerator() {

    override fun draw(x: Int, y: Int) {
        canvas.drawRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight, primaryPaint)
    }

}