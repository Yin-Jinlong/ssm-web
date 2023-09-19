package cn.yjl.io

import cn.yjl.log.util.getLogger
import java.io.File
import java.util.*

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

    /**
     * 基础文件夹
     */
    private val baseFile = File(baseDir)

    /**
     * 映射表
     */
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

    /**
     * 添加内存缓存
     *
     * @param file 要缓存的物理文件
     *
     * @return 缓存的文件或null（不存在）
     */
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

    /**
     * 获取内存文件
     *
     * @param name 文件名（相对路径）
     *
     * @return 文件或null（不存在）
     */
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