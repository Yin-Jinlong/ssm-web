package cn.yjl.qr.test

import cn.yjl.qr.MultiPatternQRImageGenerator
import cn.yjl.qr.MultiPatternQRImageGenerator.Companion.BLOCK_CIRCLE_DRAWER
import cn.yjl.qr.MultiPatternQRImageGenerator.Companion.BLOCK_DIAMOND_DRAWER
import cn.yjl.qr.MultiPatternQRImageGenerator.Companion.BLOCK_RECT_DRAWER
import cn.yjl.qr.MultiPatternQRImageGenerator.Companion.LOCATION_IN_CIRCLE_DRAWER
import cn.yjl.qr.MultiPatternQRImageGenerator.Companion.LOCATION_IN_DIAMOND_DRAWER
import cn.yjl.qr.MultiPatternQRImageGenerator.Companion.LOCATION_OUT_RECT_DRAWER
import cn.yjl.qr.QRUtil
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
    fun testGenQR() {

        val blocks = arrayOf(
            BLOCK_RECT_DRAWER,
            BLOCK_CIRCLE_DRAWER,
            BLOCK_DIAMOND_DRAWER
        )

        val locationIns = arrayOf(
            LOCATION_IN_CIRCLE_DRAWER,
            LOCATION_IN_CIRCLE_DRAWER,
            LOCATION_IN_DIAMOND_DRAWER
        )

        QRUtil.genQR(
            MultiPatternQRImageGenerator(
                blockDrawer = { x, y ->
                    primaryPaint.color = randomColor()
                    blocks[Random.nextInt(blocks.size)](this, x, y)
                },
                locationInDrawer = { x, y ->
                    primaryPaint.color = randomColor()
                    locationIns[Random.nextInt(locationIns.size)](this, x, y)
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