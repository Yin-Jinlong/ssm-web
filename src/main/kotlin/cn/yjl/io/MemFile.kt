package cn.yjl.io

import jakarta.servlet.ServletResponse
import java.io.File
import java.io.OutputStream
import java.nio.file.Path

/**
 * 内存文件（只读）
 *
 * @property size 文件大小
 * @property lastModified 最后修改时间
 * @property fileName 文件名
 * @property filePath 文件路径
 */
interface MemFile {

    val size: UInt

    val lastModified: Long

    val fileName: String

    val filePath: Path

    /**
     * @param index 索引
     *
     * @return 字节
     */
    operator fun get(index: UInt): Byte

    /**
     *
     * @param start 开始索引
     *
     * @param end 结束索引
     *
     * @return ByteBuffer
     */
    operator fun get(start: UInt, end: UInt): ByteArray

    /**
     *
     */
    operator fun rangeTo(end: UInt): ByteArray

    infix fun writeTo(out: OutputStream)

    infix fun writeTo(out: ServletResponse)

    fun buffer(): ByteArray

    /**
     * 是否存在
     */
    fun exists(): Boolean

    companion object {
        fun simple(baseFile: File, file: String): MemFile = SimpleMemFile(baseFile, file)
    }
}