package cn.yjl.io.df

import java.io.InputStream

/**
 *
 * @author YJL
 */
class DownloadFileStream(
    name: String,
    ins: InputStream,
) : FileStream(name, ins, true)