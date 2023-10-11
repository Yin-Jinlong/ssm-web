package cn.yjl.qr

import cn.yjl.util.drawArc
import cn.yjl.util.drawOval
import cn.yjl.util.drawRect

/**
 * 绘制器
 */
typealias Drawer = MultiPatternSplitBlockQRImageGenerator.(x: Int, y: Int) -> Unit

/**
 * 多样式分离式块二维码生成器
 *
 * @property blockDrawer 块绘制器
 * @property locationOutDrawer 定位块外圈绘制器
 * @property locationInDrawer 定位块内部绘制器
 *
 * @author YJL
 */
class MultiPatternSplitBlockQRImageGenerator(
    val blockDrawer: Drawer = BLOCK_RECT_DRAWER,
    val locationOutDrawer: Drawer = LOCATION_OUT_RECT_DRAWER,
    val locationInDrawer: Drawer = LOCATION_IN_RECT_DRAWER
) : SplitBlockQRImageGenerator() {

    override fun draw(x: Int, y: Int) {
        blockDrawer(x, y)
    }

    override fun drawLocation(x: Int, y: Int) {
        locationOutDrawer(x, y)
        locationInDrawer(x + 2, y + 2)
    }

    companion object {

        /**
         * 矩形块
         */
        val BLOCK_RECT_DRAWER: Drawer = { x, y ->
            canvas.drawRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight, primaryPaint)
        }

        /**
         * 矩形外定位块
         */
        val LOCATION_OUT_RECT_DRAWER: Drawer = { x, y ->
            val sx = x * blockWidth
            val sy = y * blockHeight
            canvas.drawRect(sx, sy, blockWidth * 7, blockHeight, primaryPaint)
            canvas.drawRect(sx, sy + blockHeight, blockWidth, blockHeight * 5, primaryPaint)
            canvas.drawRect(sx + 6 * blockWidth, sy + blockHeight, blockWidth, blockHeight * 5, primaryPaint)
            canvas.drawRect(sx, sy + 6 * blockHeight, blockWidth * 7, blockHeight, primaryPaint)
        }

        /**
         * 矩形内定位块
         */
        val LOCATION_IN_RECT_DRAWER: Drawer = { x, y ->
            canvas.drawRect(x * blockWidth, y * blockHeight, blockWidth * 3, blockHeight * 3, primaryPaint)
        }

        /**
         * 圆形内定位块
         */
        val LOCATION_IN_CIRCLE_DRAWER: Drawer = { x, y ->
            canvas.drawOval(x * blockWidth, y * blockHeight, blockWidth * 3, blockHeight * 3, primaryPaint)
        }

        /**
         * 圆形块
         */
        val BLOCK_CIRCLE_DRAWER: Drawer = { x, y ->
            canvas.drawOval(x * blockWidth, y * blockHeight, blockWidth, blockHeight, primaryPaint)
        }

        /**
         * 圆角块（暂时外圆角）
         */
        val BLOCK_ROUND_CORNER_DRAWER: Drawer = { x, y ->

            // 常量
            val PI = 360f
            val LEFT = 0
            val RIGHT = 1
            val TOP = 2
            val BOTTOM = 3

            // 四周块情况，配合上下左右使用
            val has = arrayOf(
                if (x < 0) false else data[x - 1, y],
                if (x >= data.width - 1) false else data[x + 1, y],
                if (y < 0) false else data[x, y - 1],
                if (y >= data.height - 1) false else data[x, y + 1]
            )

            /**
             * 四周块个数
             */
            fun getCount(): Int {
                var c = 0
                has.forEach { c += if (it) 1 else 0 }
                return c
            }

            val sx = x * blockWidth
            val sy = y * blockHeight

            /**
             * 填充圆边（180deg)
             */
            fun fillRound(s: Float = 0f) = canvas.drawArc(sx, sy, blockWidth, blockHeight, s, PI / 2f, primaryPaint)

            /**
             * 填充半圆边（90deg)
             */
            fun fillHalfRound(offX: Float = 0f, offY: Float = 0f, s: Float = 0f) {
                canvas.drawArc(
                    sx + offX * blockWidth,
                    sy + offY * blockHeight,
                    blockWidth * 2,
                    blockHeight * 2,
                    s,
                    PI / 4,
                    primaryPaint
                )
            }

            fun fillHalf(offX: Float, offY: Float, w: Float, h: Float) =
                canvas.drawRect(sx + offX, sy + offY, w, h, primaryPaint)

            val bw2 = blockWidth / 2f + 1f
            val bh2 = blockHeight / 2f + 1f

            when (getCount()) {
                0 -> BLOCK_CIRCLE_DRAWER(this, x, y)
                3, 4 -> BLOCK_RECT_DRAWER(this, x, y)
                2 -> {
                    if ((has[LEFT] && has[RIGHT]) || (has[TOP] && has[BOTTOM]))
                        BLOCK_RECT_DRAWER(this, x, y)
                    else {
                        if (has[LEFT]) {
                            if (has[TOP])
                                fillHalfRound(-1f, -1f)
                            else
                                fillHalfRound(-1f, 0f, 3f * PI / 4f)
                        } else {
                            if (has[TOP])
                                fillHalfRound(0f, -1f, PI / 4f)
                            else
                                fillHalfRound(s = PI / 2f)
                        }
                    }
                }

                else -> {
                    if (has[LEFT]) {
                        fillRound(-PI / 4f)
                        fillHalf(0f, 0f, bw2 + 1f, blockHeight)
                    } else if (has[RIGHT]) {
                        fillRound(PI / 4f)
                        fillHalf(bw2 - 1.5f, 0f, bw2 + 1f, blockHeight)
                    } else if (has[TOP]) {
                        fillRound()
                        fillHalf(0f, 0f, blockWidth, bh2 + 1f)
                    } else {
                        fillRound(PI / 2f)
                        fillHalf(0f, bh2 - 1.5f, blockWidth, bh2 + 1f)
                    }
                }
            }

        }

    }
}
