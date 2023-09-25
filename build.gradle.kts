import cn.aynu.yjl.tools.mavenAli
import cn.aynu.yjl.tools.mavenAliPublicGradlePlugin

buildscript {
    dependencies {
        classpath(files("lib/tools-1.0.0.jar"))
    }
}

apply(plugin = "yjl-ssm-web-tools")

plugins {
    kotlin("jvm") version "1.9.10" apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenAli()
        mavenAliPublicGradlePlugin()
        mavenCentral()
    }
}

rootProject.apply {
    group = "cn.yjl"
    version = "0.0.1-SNAPSHOT"
}
