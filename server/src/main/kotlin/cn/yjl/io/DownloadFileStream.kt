package cn.yjl.io

import cn.yjl.io.FileStream
import java.io.InputStream

/**
 *
 * @author YJL
 */
class DownloadFileStream(
    name: String,
    ins: InputStream,
) : FileStream(name, ins, true)