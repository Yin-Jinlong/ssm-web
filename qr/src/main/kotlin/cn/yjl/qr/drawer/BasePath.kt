package cn.yjl.qr.drawer

import org.jetbrains.skia.Matrix33
import org.jetbrains.skia.Path
import org.jetbrains.skia.Rect

/**
 *
 * @author YJL
 */
object BasePath {

    val DiamondPath = Path().apply {
        moveTo(0.5f, 0f)
        rLineTo(0.5f, 0.5f)
        rLineTo(-0.5f, 0.5f)
        rLineTo(-0.5f, -0.5f)
        closePath()
    }

    val LovePath = Path().apply {
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
        transform(Matrix33.makeTranslate(0f,0.1f))
    }

    private fun Path.arcTo(l: Float, t: Float, r: Float, b: Float, start: Float = 0f, sweep: Float = 90f) {
        arcTo(Rect(l, t, r, b), start, sweep, false)
    }

    fun roundRect(radius: Float) = Path().apply {
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