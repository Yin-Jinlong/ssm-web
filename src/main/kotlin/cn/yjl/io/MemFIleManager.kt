package cn.yjl.io

import cn.yjl.log.util.getLogger
import java.io.File
import java.util.*
import java.util.logging.Logger

/**
 *
 * 内存文件管理器
 *
 * @param baseDir 基础目录
 *
 */
class MemFileManager(baseDir: String) {

    companion object {
        @JvmStatic
        val log = getLogger()
    }

    private val baseFile = File(baseDir)
    private val fileMap = Hashtable<String, MemFile>()

    init {
        if (!baseFile.exists()) {
            log.warning("baseDir $baseFile not exist")
        } else if (!baseFile.isDirectory) {
            log.warning("baseDir $baseFile is not a directory")
        } else if (!baseFile.canRead()) {
            log.warning("baseDir $baseFile can not read")
        }
    }

    // 添加到缓存
    private fun addCache(file: String): MemFile? {
        runCatching {
            val mf = MemFile.simple(baseFile, file)
            fileMap[file] = mf
            return mf
        }.onFailure {
            log.info("addCache $file failed -> ${it.message}")
        }
        return null
    }

    operator fun get(name: String): MemFile? {
        // 先从缓存中取
        val cache = fileMap[name] ?:
        // 缓存中没有，从文件中取
        addCache(name) ?: return null
        return if (cache.exists())// 物理文件存在
            cache
        else {// 物理文件不存在
            log.warning("file $name in disk not exist")
            fileMap -= name
            null
        }
    }

}