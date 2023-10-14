package cn.yjl.qr

import cn.yjl.qr.DiamondBarcodeImageGenerator.Companion.diamondPath
import cn.yjl.util.drawArc
import cn.yjl.util.drawOval
import cn.yjl.util.drawRect
import org.jetbrains.skia.Path

/**
 * 绘制器
 */
typealias Drawer = MultiPatternQRImageGenerator.(x: Int, y: Int) -> Unit

/**
 * 多样式分离式块二维码生成器
 *
 * @property blockDrawer 块绘制器
 * @property locationOutDrawer 定位块外圈绘制器
 * @property locationInDrawer 定位块内部绘制器
 *
 * @author YJL
 */
class MultiPatternQRImageGenerator(
    val blockDrawer: Drawer = BLOCK_RECT_DRAWER,
    val locationOutDrawer: Drawer = LOCATION_OUT_RECT_DRAWER,
    val locationInDrawer: Drawer = LOCATION_IN_RECT_DRAWER
) : QRImageGenerator() {

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
            canvas.drawRect(x.toFloat(), y.toFloat(), 1f, 1f, primaryPaint)
        }

        /**
         * 矩形外定位块
         */
        val LOCATION_OUT_RECT_DRAWER: Drawer = { x, y ->
            val sx = x.toFloat()
            val sy = y.toFloat()
            canvas.drawRect(sx, sy, 7f, 1f, primaryPaint)
            canvas.drawRect(sx, sy + 1f, 1f, 5f, primaryPaint)
            canvas.drawRect(sx + 6f, sy + 1f, 1f, 5f, primaryPaint)
            canvas.drawRect(sx, sy + 6f, 7f, 1f, primaryPaint)
        }

        /**
         * 矩形内定位块
         */
        val LOCATION_IN_RECT_DRAWER: Drawer = { x, y ->
            canvas.drawRect(x.toFloat(), y.toFloat(), 3f, 3f, primaryPaint)
        }

        /**
         * 圆形内定位块
         */
        val LOCATION_IN_CIRCLE_DRAWER: Drawer = { x, y ->
            canvas.drawOval(x.toFloat(), y.toFloat(), 3f, 3f, primaryPaint)
        }

        /**
         * 圆形块
         */
        val BLOCK_CIRCLE_DRAWER: Drawer = { x, y ->
            canvas.drawOval(x.toFloat(), y.toFloat(), 1f, 1f, primaryPaint)
        }

        val BLOCK_DIAMOND_DRAWER: Drawer = { x, y ->
            canvas.translate(x.toFloat(), y.toFloat())
            canvas.drawPath(diamondPath, primaryPaint)
        }

        val LOCATION_IN_DIAMOND_DRAWER: Drawer = { x, y ->
            canvas.translate(x.toFloat(), y.toFloat())
            canvas.scale(3f, 3f)
            canvas.drawPath(diamondPath, primaryPaint)
        }
    }
}
