package cn.yjl.io.df

import cn.yjl.io.FileStream
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileNotFoundException

/**
 * @author YJL
 */
@Component
class DataFileManager(
    @Value("\${ssm-web.data-dir}")
    base: String
) {
    val baseDir: File = File(base)

    fun openStream(df: DataFile): FileStream {
        val file = resolve(df.name)
        if (!file.exists())
            throw FileNotFoundException("file '$file' not found")
        return FileStream(df.name, file.inputStream())
    }

    fun saveFromStream(group: DataFileGroup, s: FileStream): DataFile {
        val file = resolve(group.name, s.name)
        file.outputStream().use {
            s.ins.copyTo(it)
        }
        return SimpleDataFile(group, s.name)
    }

    private fun resolve(vararg path: String) = baseDir.resolve(path.joinToString("/"))
}