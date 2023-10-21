package cn.yjl.qr

import cn.yjl.qr.drawer.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import org.jetbrains.skia.Color

/**
 * 二维码工具（QR_CODE)
 *
 * @author YJL
 */
object QRUtil {

    val RectBarcodeImageGenerator by lazy { BaseBarcodeImageGenerator(BaseDrawer.RectDrawer) }
    val DiamondBarcodeImageGenerator by lazy { BaseBarcodeImageGenerator(PathDrawer(BasePath.DiamondPath)) }
    val CircleBarcodeImageGenerator by lazy { BaseBarcodeImageGenerator(BaseDrawer.OvalDrawer) }
    val RoundRectBarcodeImageGenerator by lazy { BaseBarcodeImageGenerator(RoundRectDrawer()) }
    val LoveBarcodeImageGenerator by lazy { BaseBarcodeImageGenerator(PathDrawer(BasePath.LovePath)) }

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
    fun genScaleQR(
        content: String,
        generator: AbstractBarcodeImageGenerator<*>,
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

    /**
     * 生成二维码图片
     *
     * @param generator 生成器
     * @param content 内容
     * @param hints 配置
     * @param color 主要色
     * @param bgc 背景色
     * @param size 尺寸
     */
    fun genQR(
        content: String,
        generator: AbstractBarcodeImageGenerator<*> = RectBarcodeImageGenerator,
        hints: Map<EncodeHintType, *> = BarcodeImageGenerator.DEFAULT_QR_HINTS,
        color: Int = Color.BLACK,
        bgc: Int = Color.WHITE,
        size: Int = BarcodeImageGenerator.DEFAULT_SIZE
    ) = generator.let {
        it.setPrimaryColor(color)
        it.setBackGroundColor(bgc)
        it.generate(
            BarcodeImageGenerator.genBitMatrix(
                content,
                BarcodeFormat.QR_CODE,
                hints
            ), size, size
        )
    }

}