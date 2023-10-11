package cn.yjl.qr

import java.awt.Color

typealias Drawer = MultiPatternSplitBlockQRImageGenerator.(x: Int, y: Int) -> Unit

/**
 *
 * @author YJL
 */
class MultiPatternSplitBlockQRImageGenerator(
    primaryColor: Color = Color.BLACK,
    backgroundColor: Color = Color.WHITE,
    val blockDrawer: Drawer = BLOCK_RECT_DRAWER,
    val locationOutDrawer: Drawer = LOCATION_OUT_RECT_DRAWER,
    val locationInDrawer: Drawer = LOCATION_IN_RECT_DRAWER
) : SplitBlockQRImageGenerator(primaryColor, backgroundColor) {

    override fun draw(x: Int, y: Int) {
        blockDrawer(x, y)
    }

    override fun drawLocation(x: Int, y: Int) {
        locationOutDrawer(x, y)
        locationInDrawer(x + 2, y + 2)
    }

    companion object {

        val BLOCK_RECT_DRAWER: Drawer = { x, y ->
            graphics.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight)
        }

        val LOCATION_OUT_RECT_DRAWER: Drawer = { x, y ->
            val sx = x * blockWidth
            val sy = y * blockHeight
            graphics.fillRect(sx, sy, blockWidth * 7, blockHeight)
            graphics.fillRect(sx, sy + blockHeight, blockWidth, blockHeight * 5)
            graphics.fillRect(sx + 6 * blockWidth, sy + blockHeight, blockWidth, blockHeight * 5)
            graphics.fillRect(sx, sy + 6 * blockHeight, blockWidth * 7, blockHeight)
        }

        val LOCATION_IN_RECT_DRAWER: Drawer = { x, y ->
            graphics.fillRect(x * blockWidth, y * blockHeight, blockWidth * 3, blockHeight * 3)
        }

        val LOCATION_IN_CIRCLE_DRAWER: Drawer = { x, y ->
            graphics.fillOval(x * blockWidth, y * blockHeight, blockWidth * 3, blockHeight * 3)
        }


        val BLOCK_CIRCLE_DRAWER: Drawer = { x, y ->
            graphics.fillOval(x * blockWidth, y * blockHeight, blockWidth, blockHeight)
        }

    }
}
