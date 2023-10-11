package cn.yjl.qr

import com.google.zxing.BarcodeFormat
import com.google.zxing.BarcodeFormat.*
import com.google.zxing.EncodeHintType
import com.google.zxing.aztec.AztecWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.datamatrix.DataMatrixWriter
import com.google.zxing.oned.*
import com.google.zxing.pdf417.PDF417Writer
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.awt.image.BufferedImage

/**
 *
 * @author YJL
 */
fun interface BarcodeImageGenerator {

    companion object {

        const val DEFAULT_SCALE = 15.0f

        private val aztecWriter by lazy { AztecWriter() }

        private val codaBarWriter by lazy { CodaBarWriter() }

        private val code39Writer by lazy { Code39Writer() }

        private val code93Writer by lazy { Code93Writer() }

        private val code128Writer by lazy { Code128Writer() }

        private val dataMatrixWriter by lazy { DataMatrixWriter() }

        private val ean8Writer by lazy { EAN8Writer() }

        private val ean13Writer by lazy { EAN13Writer() }

        private val itfWriter by lazy { ITFWriter() }

        private val pdF417Writer by lazy { PDF417Writer() }

        private val qrcodeWriter by lazy { QRCodeWriter() }

        private val upcaWriter by lazy { UPCAWriter() }

        private val upceWriter by lazy { UPCEWriter() }

        val DEFAULT_QR_HINTS = mutableMapOf<EncodeHintType, Any>().apply {
            put(EncodeHintType.CHARACTER_SET, "UTF-8")
            put(EncodeHintType.MARGIN, 1)
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q)
        }

        fun genBitMatrix(
            content: String,
            format: BarcodeFormat,
            hints: Map<EncodeHintType, *>
        ): BitMatrix = when (format) {
            AZTEC -> aztecWriter
            CODABAR -> codaBarWriter
            CODE_39 -> code39Writer
            CODE_93 -> code93Writer
            CODE_128 -> code128Writer
            DATA_MATRIX -> dataMatrixWriter
            EAN_8 -> ean8Writer
            EAN_13 -> ean13Writer
            ITF -> itfWriter
            PDF_417 -> pdF417Writer
            QR_CODE -> qrcodeWriter
            UPC_A -> upcaWriter
            UPC_E -> upceWriter
            else -> TODO("No writer found for format $format")
        }.encode(content, format, 0, 0, hints)

    }

    fun generate(bitMatrix: BitMatrix, scale: Float): BufferedImage

}