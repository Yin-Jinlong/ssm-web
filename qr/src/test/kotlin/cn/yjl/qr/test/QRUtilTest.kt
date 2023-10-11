package cn.yjl.qr.test

import cn.yjl.qr.MultiPatternSplitBlockQRImageGenerator
import cn.yjl.qr.MultiPatternSplitBlockQRImageGenerator.Companion.BLOCK_ROUND_CORNER_DRAWER
import cn.yjl.qr.MultiPatternSplitBlockQRImageGenerator.Companion.LOCATION_IN_CIRCLE_DRAWER
import cn.yjl.qr.MultiPatternSplitBlockQRImageGenerator.Companion.LOCATION_OUT_RECT_DRAWER
import cn.yjl.qr.QRUtil
import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO.getImageWritersBySuffix
import javax.imageio.stream.FileImageOutputStream
import kotlin.random.Random

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
    fun testGenQR() {
        QRUtil.genQR(
            MultiPatternSplitBlockQRImageGenerator(
                blockDrawer = { x, y ->
                    primaryPaint.color = randomColor()
                    BLOCK_ROUND_CORNER_DRAWER(this, x, y)
                },
                locationInDrawer = { x, y ->
                    primaryPaint.color = randomColor()
                    LOCATION_IN_CIRCLE_DRAWER(this, x, y)
                },
                locationOutDrawer = { x, y ->
                    primaryPaint.color = randomColor()
                    LOCATION_OUT_RECT_DRAWER(this, x, y)
                }

            ),
            "Hello QR"
        ).save("colorful-round")
    }
}