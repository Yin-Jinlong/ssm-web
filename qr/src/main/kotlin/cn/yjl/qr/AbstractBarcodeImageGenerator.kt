package cn.yjl.qr

import com.google.zxing.common.BitMatrix
import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

/**
 *
 * @author YJL
 */
abstract class AbstractBarcodeImageGenerator(
    var primaryColor: Color = Color.BLACK,
    var backgroundColor: Color = Color.WHITE
) : BarcodeImageGenerator {

    var graphicsWidth = 0

    var graphicsHeight = 0

    abstract fun draw(graphics: Graphics2D, data: BitMatrix)

    override fun generate(bitMatrix: BitMatrix, scale: Float): BufferedImage {
        graphicsWidth = (bitMatrix.width * scale).roundToInt()
        graphicsHeight = (bitMatrix.height * scale).roundToInt()
        return BufferedImage(graphicsWidth, graphicsHeight, BufferedImage.TYPE_INT_RGB).apply {
            val g = createGraphics()
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

            g.setClip(0, 0, graphicsWidth, graphicsHeight)

            g.color = backgroundColor
            g.fillRect(0, 0, graphicsWidth, graphicsHeight)

            g.color = primaryColor
            draw(g, bitMatrix)
        }
    }

}
