import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    maven("https://repo.gradle.org/gradle/libs-releases-local/")
}

project.version = "1.0.0"
project.group = "yjl-ssm-web"

gradlePlugin {
    plugins {
        create("tools") {
            id = "yjl-ssm-web-tools"
            implementationClass = "cn.aynu.yjl.plugin.SSMToolsPlugin"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

task("installToolsForRoot", Copy::class) {
    group = "install"
    dependsOn("jar")
    from(buildDir.resolve("libs"))
    into(rootProject.rootDir.resolve("lib"))
    doLast {
        println("\u001b[32mInstall success!")
        println("\u001b[33mPlease\u001b[31m reload\u001B[33m project!\u001b[0m")
    }

}