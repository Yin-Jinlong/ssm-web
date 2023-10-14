package cn.yjl.qr

import org.jetbrains.skia.Path
import org.jetbrains.skia.Rect

/**
 * 圆点二维码图片生成器（替换方块为圆）
 *
 * @author YJL
 */
class RoundRectBarcodeImageGenerator(
    radius: Float = 0.2f
) : AbstractBarcodeImageGenerator() {

    companion object {
        private fun Path.arcTo(l: Float, t: Float, r: Float, b: Float, start: Float = 0f, sweep: Float = 90f) {
            arcTo(Rect(l, t, r, b), start, sweep, false)
        }
    }

    private lateinit var path: Path

    var radius: Float = radius
        set(value) {
            field = value
            path = Path().apply {
                val R = radius * 2
                val R1 = 1f - R
                moveTo(0f, 0f)
                // 左上
                arcTo(0f, 0f, R, R, 180f)
                // 上
                lineTo(1f, 0f)
                // 右上
                arcTo(R1, 0f, 1f, R, 270f)
                // 右
                lineTo(1f, 1f)
                // 右下
                arcTo(R1, R1, 1f, 1f)
                // 下
                lineTo(0f, 1f)
                // 左下
                arcTo(0f, R1, R, 1f, 90f)
                closePath()
            }
        }


    override fun draw(x: Int, y: Int) {
        canvas.translate(x.toFloat(), y.toFloat())
        canvas.drawPath(path, primaryPaint)
    }

}