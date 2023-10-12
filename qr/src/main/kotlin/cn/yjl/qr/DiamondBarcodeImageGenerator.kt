package cn.yjl.qr

import org.jetbrains.skia.Path

/**
 * 圆点二维码图片生成器（替换方块为圆）
 *
 * @author YJL
 */
class DiamondBarcodeImageGenerator : AbstractBarcodeImageGenerator() {

    companion object {
        internal val diamondPath = Path().apply {
            moveTo(0.5f, 0f)
            rLineTo(0.5f, 0.5f)
            rLineTo(-0.5f, 0.5f)
            rLineTo(-0.5f, -0.5f)
            closePath()
        }
    }

    override fun draw(x: Int, y: Int) {
        canvas.translate(x.toFloat(), y.toFloat())
        canvas.drawPath(diamondPath, primaryPaint)
    }

}