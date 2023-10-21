package cn.yjl.qr.drawer

import cn.yjl.util.drawOval
import cn.yjl.util.drawRect

/**
 * @author YJL
 */
object BaseDrawer {

    val RectDrawer: Drawer = Drawer { c, p ->
        c.drawRect(0f, 0f, 1f, 1f, p)
    }

    val OvalDrawer: Drawer = Drawer { c, p ->
        c.drawOval(0f, 0f, 1f, 1f, p)
    }

}