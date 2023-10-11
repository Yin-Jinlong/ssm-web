import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

project.version = "0.0.0"

dependencies {
    // https://mvnrepository.com/artifact/com.google.zxing/core
    implementation("com.google.zxing:core:3.5.2")
    // https://mvnrepository.com/artifact/org.jetbrains.skiko/skiko-awt
    implementation("org.jetbrains.skiko:skiko-awt:0.7.81")
    // https://mvnrepository.com/artifact/org.jetbrains.skiko/skiko-awt-runtime-windows-x64
    runtimeOnly("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.7.81")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
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