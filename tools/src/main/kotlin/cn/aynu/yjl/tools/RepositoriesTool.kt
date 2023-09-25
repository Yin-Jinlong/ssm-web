package cn.aynu.yjl.tools

import org.gradle.api.artifacts.dsl.RepositoryHandler

fun RepositoryHandler.mavenAli() {
    mavenAliPublic()
    mavenAliPublicCentral()
}

fun RepositoryHandler.mavenAliPublic() {
    maven("https://maven.aliyun.com/repository/public")
}

fun RepositoryHandler.mavenAliPublicCentral() {
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/gradle-plugin/")
}

fun RepositoryHandler.mavenAliPublicGradlePlugin() {
    maven("https://maven.aliyun.com/repository/gradle-plugin/")
}

private fun RepositoryHandler.maven(url: String) {
    maven {
        it.setUrl(url)
    }
}