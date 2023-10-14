package cn.yjl.qr

import org.jetbrains.skia.Path

/**
 * 路径二维码图片生成器（绘制矢量图，1x1)
 *
 * @author YJL
 */
open class PathBarcodeImageGenerator(
    path: Path = Path()
) : AbstractBarcodeImageGenerator() {

    companion object {

        val PATH_LOVE = Path().apply {
            moveTo(0f, 0.2912f)
            cubicTo(0f, 0.1314f, 0.1304f, 0f, 0.2912f, 0f)
            rCubicTo(0.0786f, 0f, 0.1539f, 0.0318f, 0.2088f, 0.0882f)
            rCubicTo(0.1121f, -0.1153f, 0.2965f, -0.1179f, 0.4118f, -0.0057f)
            rCubicTo(0.0564f, 0.0548f, 0.0882f, 0.1301f, 0.0882f, 0.2088f)
            rCubicTo(0.0002f, 0.0923f, -0.0435f, 0.1791f, -0.1177f, 0.2339f)
            rLineTo(-0.3077f, 0.2535f)
            rCubicTo(-0.0505f, 0.0416f, -0.1237f, 0.0405f, -0.1728f, -0.0027f)
            lineTo(0.1021f, 0.5127f)
            cubicTo(0.0372f, 0.4574f, 0f, 0.3764f, 0f, 0.2912f)
            closePath()
        }

        val LOVE_PATH_QR_GENERATOR = PathBarcodeImageGenerator(PATH_LOVE)

    }

    var path: Path = path
        internal set

    override fun draw(x: Int, y: Int) {
        canvas.translate(x.toFloat(), y.toFloat())
        canvas.drawPath(path, primaryPaint)
    }

}