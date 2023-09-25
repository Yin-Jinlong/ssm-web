package cn.aynu.yjl.task

import cn.aynu.yjl.tools.size
import cn.aynu.yjl.tools.zipSub
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipOutputStream
import javax.inject.Inject

abstract class ZipSrcTask : DefaultTask() {

    @Inject
    var file = project.buildDir.resolve(project.name + "-src.zip")

    @Inject
    var zipLevel = 9

    @Inject
    var filter: (File) -> Boolean = {
        !it.name.matches(Regex("\\.git|\\.gradle|\\.idea|build|dist|node_modules|bin|out"))
    }

    @Inject
    var comment: String? = null

    init {
        group = "zip"
    }

    @TaskAction
    fun run() {
        ZipOutputStream(FileOutputStream(file)).use { out ->
            out.apply {
                setLevel(zipLevel)
                comment?.let { setComment(it) }
                zipSub(File("."), filter)
            }
        }
        println()
        println("file: ${file.absolutePath}")
        println("size: ${file.size}")
    }
}