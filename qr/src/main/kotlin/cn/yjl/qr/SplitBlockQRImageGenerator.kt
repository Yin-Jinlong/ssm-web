package cn.yjl.qr

import com.google.zxing.common.BitMatrix
import org.jetbrains.skia.Canvas

/**
 * 分离块二维码生成器
 *
 * 分离数据块和定位块绘制
 *
 * @author YJL
 */
abstract class SplitBlockQRImageGenerator : SplitBlockBarcodeImageGenerator() {

    /**
     * 绘制定位块
     *
     * @param x 定位块左上角x
     * @param y 定位块左上角y
     */
    abstract fun drawLocation(x: Int, y: Int)

    override fun draw(canvas: Canvas, data: BitMatrix) {
        super.update(canvas, data)

        val ls = find(data)

        for (y in 0..<data.height)
            loopX@ for (x in 0..<data.width) {
                for (l in ls) {
                    val (lx, ly) = l
                    if (x in lx..lx + 6 && y in ly..ly + 6) {
                        continue@loopX
                    }
                }
                if (data[x, y])
                    draw(x, y)
            }

        ls.forEach {
            drawLocation(it[0], it[1])
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
}