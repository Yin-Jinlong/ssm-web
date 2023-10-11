package cn.yjl.qr

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import org.jetbrains.skia.Color

/**
 *
 * @author YJL
 */
object QRUtil {

    private val rectBarcodeImageGenerator by lazy { RectBarcodeImageGenerator() }
    private val circleBarcodeImageGenerator by lazy { CircleBarcodeImageGenerator() }

    fun genRectQRImage(
        content: String,
        hints: Map<EncodeHintType, *> = BarcodeImageGenerator.DEFAULT_QR_HINTS,
        color: Int = Color.BLACK,
        bgc: Int = Color.WHITE,
        scale: Float = BarcodeImageGenerator.DEFAULT_SCALE
    ) = genQR(rectBarcodeImageGenerator, content, hints, color, bgc, scale)

    fun genCircleQRImage(
        content: String,
        hints: Map<EncodeHintType, *> = BarcodeImageGenerator.DEFAULT_QR_HINTS,
        color: Int = Color.BLACK,
        bgc: Int = Color.WHITE,
        scale: Float = BarcodeImageGenerator.DEFAULT_SCALE
    ) = genQR(circleBarcodeImageGenerator, content, hints, color, bgc, scale)

    fun genQR(
        generator: AbstractBarcodeImageGenerator,
        content: String,
        hints: Map<EncodeHintType, *> = BarcodeImageGenerator.DEFAULT_QR_HINTS,
        color: Int = Color.BLACK,
        bgc: Int = Color.WHITE,
        scale: Float = BarcodeImageGenerator.DEFAULT_SCALE
    ) = generator.let {
        it.setPrimaryColor(color)
        it.setBackGroundColor(bgc)
        it.generate(
            BarcodeImageGenerator.genBitMatrix(
                content,
                BarcodeFormat.QR_CODE,
                hints
            ), scale
        )
    }

}