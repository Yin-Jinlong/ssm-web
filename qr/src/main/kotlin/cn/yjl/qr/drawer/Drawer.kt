package cn.yjl.qr.drawer

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint

/**
 *
 * @author YJL
 */
fun interface Drawer {

    fun draw(canvas: Canvas, primaryPaint: Paint)

}