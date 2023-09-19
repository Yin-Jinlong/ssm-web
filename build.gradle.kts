import java.io.FileOutputStream
import java.text.DecimalFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

rootProject.apply {
    group = "cn.yjl"
    version = "0.0.1-SNAPSHOT"
}

task("pkg-src") {
    group = "zip"
    doLast {
        val file = buildDir.resolve("src.zip")
        ZipOutputStream(FileOutputStream(file)).use {
            it.setLevel(9)
            it.setComment("yjl-ssm-web-src")
            it.zipSub(File("."))
        }
        println()
        println("file: $file")
        println("size: ${file.size}")
    }
}

fun File.zipEntry(): ZipEntry {
    return ZipEntry(path.substring(2).replace("\\", "/").let {
        if (isDirectory)
            "$it/"
        else
            it
    })
}

fun ZipOutputStream.zipSub(dir: File) {
    dir.listFiles()?.filter {
        !it.name.matches(Regex("\\.gradle|build|dist|node_modules"))
    }?.forEach { file ->
        val entry=file.zipEntry()
        println("zip $entry")
        putNextEntry(entry)
        if (file.isDirectory) {
            closeEntry()
            zipSub(file)
        } else {
            file.inputStream().use {
                it.copyTo(this)
            }
            closeEntry()
        }
    }
}

val File.size: String
    get() {
        val us = arrayOf("B", "KB", "MB", "GB", "TB")
        var i = 0
        var s = length().toDouble()
        while (s > 1024) {
            s /= 1024
            i++
        }
        return "${DecimalFormat("###.00").format(s)} ${us[i]}"
    }