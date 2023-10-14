package cn.yjl.qr

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import org.jetbrains.skia.Color

/**
 * 二维码工具（QR_CODE)
 *
 * @author YJL
 */
object QRUtil {

    private val rectBarcodeImageGenerator by lazy { RectBarcodeImageGenerator() }
    private val diamondBarcodeImageGenerator by lazy { DiamondBarcodeImageGenerator() }
    private val circleBarcodeImageGenerator by lazy { CircleBarcodeImageGenerator() }
    private val roundRectBarcodeImageGenerator by lazy { RoundRectBarcodeImageGenerator() }

    /**
     * 生成矩形二维码图片
     *
     * @param content 内容
     * @param hints 配置
     * @param color 主要色
     * @param bgc 背景色
     * @param scale 缩放
     */
    fun genRectQRImage(
        content: String,
        hints: Map<EncodeHintType, *> = BarcodeImageGenerator.DEFAULT_QR_HINTS,
        color: Int = Color.BLACK,
        bgc: Int = Color.WHITE,
        scale: Float = BarcodeImageGenerator.DEFAULT_SCALE
    ) = genQR(rectBarcodeImageGenerator, content, hints, color, bgc, scale)

    /**
     * 生成矩形二维码图片
     *
     * @param content 内容
     * @param hints 配置
     * @param color 主要色
     * @param bgc 背景色
     * @param scale 缩放
     */
    fun genDiamondQRImage(
        content: String,
        hints: Map<EncodeHintType, *> = BarcodeImageGenerator.DEFAULT_QR_HINTS,
        color: Int = Color.BLACK,
        bgc: Int = Color.WHITE,
        scale: Float = BarcodeImageGenerator.DEFAULT_SCALE
    ) = genQR(diamondBarcodeImageGenerator, content, hints, color, bgc, scale)

    /**
     * 生成圆形二维码图片
     *
     * @param content 内容
     * @param hints 配置
     * @param color 主要色
     * @param bgc 背景色
     * @param scale 缩放
     */
    fun genCircleQRImage(
        content: String,
        hints: Map<EncodeHintType, *> = BarcodeImageGenerator.DEFAULT_QR_HINTS,
        color: Int = Color.BLACK,
        bgc: Int = Color.WHITE,
        scale: Float = BarcodeImageGenerator.DEFAULT_SCALE
    ) = genQR(circleBarcodeImageGenerator, content, hints, color, bgc, scale)

    /**
     * 生成圆角矩形二维码图片
     *
     * @param content 内容
     * @param hints 配置
     * @param color 主要色
     * @param bgc 背景色
     * @param radius 圆角半径（小数百分比）
     * @param scale 缩放
     */
    fun genRoundRectQRImage(
        content: String,
        hints: Map<EncodeHintType, *> = BarcodeImageGenerator.DEFAULT_QR_HINTS,
        color: Int = Color.BLACK,
        bgc: Int = Color.WHITE,
        radius: Float = 0.3f,
        scale: Float = BarcodeImageGenerator.DEFAULT_SCALE
    ) = genQR(roundRectBarcodeImageGenerator.apply {
        this.radius = radius
    }, content, hints, color, bgc, scale)

    /**
     * 生成二维码图片
     *
     * @param generator 生成器
     * @param content 内容
     * @param hints 配置
     * @param color 主要色
     * @param bgc 背景色
     * @param scale 缩放
     */
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