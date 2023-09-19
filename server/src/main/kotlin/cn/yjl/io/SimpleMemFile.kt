package cn.yjl.io

import java.io.File
import java.io.FileNotFoundException
import java.lang.RuntimeException
import java.nio.file.Path

class SimpleMemFile(baseDir: File, watchFile: String) : AbstractMemFile() {

    private val webDir = baseDir

    private val file = File(webDir, watchFile)

    init {
        update()
        memTime = file.lastModified()
    }

    override val lastModified: Long
        get() = file.lastModified()

    override val fileName: String
        get() = file.name

    override val filePath: Path
        get() = file.toPath()

    /**
     * 物理文件是否存在
     */
    override fun exists(): Boolean = file.exists()


    @Synchronized
    override fun update() {
        if (!file.exists()) {
            throw FileNotFoundException(fileName)
        }
        if (!file.isFile) {
            throw RuntimeException("$fileName is not a file")
        }
        data = file.inputStream().use {
            it.readAllBytes()
        }
    }
}