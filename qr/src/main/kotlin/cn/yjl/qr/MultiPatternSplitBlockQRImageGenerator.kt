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

        val BLOCK_ROUND_CORNER_DRAWER: Drawer = { x, y ->

            val LEFT = 0
            val RIGHT = 1
            val TOP = 2
            val BOTTOM = 3

            val has = arrayOf(
                if (x < 0) false else data[x - 1, y],
                if (x >= data.width - 1) false else data[x + 1, y],
                if (y < 0) false else data[x, y - 1],
                if (y >= data.height - 1) false else data[x, y + 1]
            )

            fun getCount(): Int {
                var c = 0
                has.forEach { c += if (it) 1 else 0 }
                return c
            }

            val sx = x * blockWidth
            val sy = y * blockHeight

            fun fillRound(s: Int = 0) = graphics.fillArc(sx, sy, blockWidth, blockHeight, s, 180)
            fun fillHalfRound(offX: Int=0, offY: Int=0, s: Int = 0) {
                graphics.fillArc(
                    sx + offX * blockWidth,
                    sy + offY * blockHeight,
                    blockWidth * 2,
                    blockHeight * 2,
                    s,
                    90
                )
            }

            fun fillHalf(offX: Int, offY: Int, w: Int, h: Int) =
                graphics.fillRect(sx + offX, sy + offY, w, h)

            val bw2 = ((blockWidth + 1) / 2)
            val bh2 = ((blockHeight + 1) / 2)

            when (getCount()) {
                0 -> BLOCK_CIRCLE_DRAWER(this, x, y)
                3, 4 -> BLOCK_RECT_DRAWER(this, x, y)
                2 -> {
                    if ((has[LEFT] && has[RIGHT]) || (has[TOP] && has[BOTTOM]))
                        BLOCK_RECT_DRAWER(this, x, y)
                    else {
                        if (has[LEFT]) {
                            if (has[TOP])
                                fillHalfRound(-1, -1, 270)
                            else
                                fillHalfRound(-1, 0)
                        } else {
                            if (has[TOP])
                                fillHalfRound(0, -1, 180)
                            else
                                fillHalfRound( s=90)
                        }
                    }
                }

                else -> {
                    if (has[LEFT]) {
                        fillRound(-90)
                        fillHalf(0, 0, bw2, blockHeight)
                    } else if (has[RIGHT]) {
                        fillRound(90)
                        fillHalf(bw2 - 1, 0, bw2, blockHeight)
                    } else if (has[TOP]) {
                        fillRound(180)
                        fillHalf(0, 0, blockWidth, bh2)
                    } else {
                        fillRound()
                        fillHalf(0, bh2 - 1, blockWidth, bh2)
                    }
                }
            }

        }

    }
}
