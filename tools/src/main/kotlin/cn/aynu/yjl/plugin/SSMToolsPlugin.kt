package cn.aynu.yjl.plugin

import cn.aynu.yjl.task.ZipSrcTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class SSMToolsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        if (target.parent != null)
            return
        target.tasks.create("pkg-src", ZipSrcTask::class.java) {
            it.comment = "yjl-ssm-web-src"
        }
    }

}