package cn.aynu.yjl.tools

import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

fun File.zipEntry(): ZipEntry {
    return ZipEntry(path.substring(2).replace("\\", "/").let {
        if (isDirectory)
            "$it/"
        else
            it
    })
}

fun ZipOutputStream.zipSub(
    dir: File,
    filter: (File) -> Boolean = { true }
) {
    dir.listFiles()?.filter(filter)?.forEach { file ->
        val entry = file.zipEntry()
        println("zip $entry")
        putNextEntry(entry)
        if (file.isDirectory) {
            closeEntry()
            zipSub(file, filter)
        } else {
            file.inputStream().use {
                it.copyTo(this)
            }
            closeEntry()
        }
    }
}

