package cn.yjl.io

import java.io.File
import java.io.FileNotFoundException
import java.lang.RuntimeException
import java.nio.file.Path

/**
 * @author YJL
 */
class SimpleMemFile(baseDir: File, watchFile: String) : AbstractMemFile() {

    companion object {
        /**
         * 文件缓存有效期（毫秒）
         */
        var aliveTime = 5 * 60 * 1000L
    }

    private val webDir = baseDir

    private val file = File(webDir, watchFile)

    private var cacheTime = 0L

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
        val now = System.currentTimeMillis()
        if (now - cacheTime < aliveTime)
            return
        if (!file.exists()) {
            throw FileNotFoundException(fileName)
        }
        if (!file.isFile) {
            throw RuntimeException("$fileName is not a file")
        }
        data = file.inputStream().use {
            it.readAllBytes()
        }
        cacheTime = now
    }
}