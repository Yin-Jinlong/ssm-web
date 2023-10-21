package cn.yjl.qr

import cn.yjl.qr.drawer.BaseDrawer
import cn.yjl.qr.drawer.Drawer
import cn.yjl.util.drawRect
import com.google.zxing.common.BitMatrix
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint

/**
 * 分离块二维码生成器
 *
 * 分离数据块和定位块绘制
 *
 * @author YJL
 */
open class QRImageGenerator(
    drawer: QRDrawer = DefaultQRDrawer
) : BaseBarcodeImageGenerator<QRImageGenerator.QRDrawer>(drawer) {

    override fun draw(canvas: Canvas, data: BitMatrix) {

        val ls = find(data)

        for (y in 0..<data.height)
            loopX@ for (x in 0..<data.width) {
                for (l in ls) {
                    val (lx, ly) = l
                    if (x in lx..lx + 6 && y in ly..ly + 6) {
                        continue@loopX
                    }
                }
                if (data[x, y]) {
                    canvas.saveDrawXY(x.toFloat(), y.toFloat()) {
                        drawer.drawBlock(this, it, x, y)
                    }
                }
            }

        ls.forEach { ps ->
            val (x, y) = ps
            canvas.saveDrawXY(x.toFloat(), y.toFloat()) {
                drawer.drawOutLocation(this, it, x, y)
            }
            canvas.saveDrawXY(x + 2f, y + 2f) {
                drawer.drawInLocation(this, it, x + 2, y + 2)
            }
        }
    }


    companion object {

        private val O = false

        private val X = true

        private val PATTERN = arrayOf(
            arrayOf(X, X, X, X, X, X, X),
            arrayOf(X, O, O, O, O, O, X),
            arrayOf(X, O, X, X, X, O, X),
            arrayOf(X, O, X, X, X, O, X),
            arrayOf(X, O, X, X, X, O, X),
            arrayOf(X, O, O, O, O, O, X),
            arrayOf(X, X, X, X, X, X, X)
        )

        val DefaultQRDrawer = object : QRDrawer {

            override fun drawBlock(canvas: Canvas, primaryPaint: Paint, x: Int, y: Int) {
                BaseDrawer.RectDrawer.draw(canvas, primaryPaint)
            }

            override fun drawOutLocation(canvas: Canvas, primaryPaint: Paint, x: Int, y: Int) {
                canvas.drawRect(0f, 0f, 7f, 1f, primaryPaint)
                canvas.drawRect(0f, 1f, 1f, 5f, primaryPaint)
                canvas.drawRect(6f, 1f, 1f, 5f, primaryPaint)
                canvas.drawRect(0f, 6f, 7f, 1f, primaryPaint)
            }

            override fun drawInLocation(canvas: Canvas, primaryPaint: Paint, x: Int, y: Int) {
                canvas.drawRect(0f, 0f, 3f, 3f, primaryPaint)
            }
        }
    }


    /**
     * 寻找定位块
     */
    private fun find(data: BitMatrix): Array<IntArray> {
        var y = 0
        val r = mutableListOf<IntArray>()
        while (y < data.height - 7) {
            var x = 0
            while (x < data.width - 7) {
                if (find(data, x, y))
                    r += intArrayOf(x, y)
                x++
            }
            y++
        }
        return r.toTypedArray()
    }

    /**
     * 找到定位块?
     */
    private fun find(data: BitMatrix, x: Int, y: Int): Boolean {
        for (py in 0..6) {
            for (px in 0..6) {
                if (PATTERN[py][px] != data[x + px, y + py])
                    return false
            }
        }
        return true
    }

    interface QRDrawer : Drawer {
        fun drawBlock(canvas: Canvas, primaryPaint: Paint, x: Int, y: Int) {
            DefaultQRDrawer.drawBlock(canvas, primaryPaint, x, y)
        }

        fun drawOutLocation(canvas: Canvas, primaryPaint: Paint, x: Int, y: Int) {
            DefaultQRDrawer.drawOutLocation(canvas, primaryPaint, x, y)
        }

        fun drawInLocation(canvas: Canvas, primaryPaint: Paint, x: Int, y: Int) {
            DefaultQRDrawer.drawInLocation(canvas, primaryPaint, x, y)
        }

        override fun draw(canvas: Canvas, primaryPaint: Paint) = TODO()

    }

}