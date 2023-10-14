package cn.yjl.qr.test

import cn.yjl.qr.QRImageGenerator
import cn.yjl.qr.QRUtil
import cn.yjl.qr.drawer.BaseDrawer
import cn.yjl.qr.drawer.BasePath
import cn.yjl.qr.drawer.PathDrawer
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint
import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO.getImageWritersBySuffix
import javax.imageio.stream.FileImageOutputStream
import kotlin.random.Random

/**
 * QR工具测试
 *
 * 测试生成图片在`build/test/qr`目录下，执行扫描查看结果
 *
 * @author YJL
 */
class QRUtilTest {

    val filePath = File("build/test/qr")
    val suffix = "png"

    fun BufferedImage.save(fileName: String) {
        val writer = getImageWritersBySuffix(suffix).next()
        val file = filePath.resolve("$fileName.$suffix")
        file.parentFile.mkdirs()
        writer.output = FileImageOutputStream(file)
        writer.write(this)
        writer.dispose()
    }

    @Test
    fun testGenRectQRImage() {
        QRUtil.genRectQRImage(
            "Hi QR"
        ).save("rect")
    }

    @Test
    fun testGenDiamondQRImage() {
        QRUtil.genDiamondQRImage(
            "Hi Diamond QR"
        ).save("diamond")
    }

    @Test
    fun testGenCircleQRImage() {
        QRUtil.genCircleQRImage(
            "Hi Circle QR"
        ).save("circle")
    }

    private fun randomColor(): Int {
        return java.awt.Color.getHSBColor(
            Random.nextFloat() * 360,
            Random.nextFloat() * 0.5f + 0.3f,
            Random.nextFloat() * 0.5f + 0.2f
        ).rgb
    }

    @Test
    fun testGenRoundRectQRImage() {
        QRUtil.genRoundRectQRImage("Hi RoundRect QR")
            .save("round-rect")
    }

    @Test
    fun testGenLoveQRImage() {
        QRUtil.genLoveQRImage("Hi Love QR", scale = 30f)
            .save("love")
    }

    @Test
    fun testGenQR() {

        val bds = arrayOf(
            BaseDrawer.RectDrawer,
            BaseDrawer.OvalDrawer,
            PathDrawer(BasePath.DiamondPath),
            PathDrawer(BasePath.LovePath),
        )

        QRUtil.genQR(
            QRImageGenerator(object : QRImageGenerator.QRDrawer {
                override fun drawBlock(canvas: Canvas, primaryPaint: Paint, x: Int, y: Int) {
                    primaryPaint.color = randomColor()
                    bds[Random.nextInt(bds.size)].draw(canvas, primaryPaint)
                }

                override fun drawOutLocation(canvas: Canvas, primaryPaint: Paint, x: Int, y: Int) {
                    primaryPaint.color = randomColor()
                    super.drawOutLocation(canvas, primaryPaint, x, y)
                }

                override fun drawInLocation(canvas: Canvas, primaryPaint: Paint, x: Int, y: Int) {
                    canvas.scale(3f,3f)
                    drawBlock(canvas, primaryPaint, x, y)
                }
            }),
            "Hello QR"
        ).save("random")
    }
}