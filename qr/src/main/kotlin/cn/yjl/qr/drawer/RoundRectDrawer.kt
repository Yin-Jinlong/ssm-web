package cn.yjl.qr.drawer

import org.jetbrains.skia.Path

/**
 *
 * @author YJL
 */
class RoundRectDrawer(
    radius: Float=0.3f
) : PathDrawer() {

    override var path: Path = BasePath.roundRect(radius)

    var radius: Float = radius
        set(value) {
            field = value
            path = BasePath.roundRect(value)
        }
}