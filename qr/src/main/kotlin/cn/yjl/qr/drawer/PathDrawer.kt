package cn.yjl.qr.drawer

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Path

/**
 *
 * @author YJL
 */
open class PathDrawer(
    path: Path = Path()
) : Drawer {

    open var path: Path = path
        internal set

    override fun draw(canvas: Canvas, primaryPaint: Paint) {
        canvas.drawPath(path, primaryPaint)
    }
}