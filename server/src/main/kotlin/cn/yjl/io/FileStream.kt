package cn.yjl.io

import java.io.InputStream

/**
 * @property download 下载模式（二进制）
 *
 * @author YJL
 */
open class FileStream(
    val name: String,
    val ins: InputStream,
    val download: Boolean = false
) : AutoCloseable {

    override fun close() = ins.close()

}