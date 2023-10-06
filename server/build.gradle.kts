import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    war
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("jvm")
}

rootProject.apply {
    group = "cn.yjl"
    version = "0.0.1-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations.all {
    exclude("com.zaxxer", "HikariCP")
}

dependencies {
    // spring-boot-web
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0") {
        exclude("org.springframework.boot", "spring-boot-starter-json")
    }
    // druid
    // https://mvnrepository.com/artifact/com.alibaba/druid-spring-boot-starter
    implementation("com.alibaba:druid-spring-boot-starter:1.2.19")

    // spring-validation
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")

    // mybatis
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")

    // servlet-api
    api("jakarta.servlet:jakarta.servlet-api:6.0.0")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j:8.0.32")

    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat:3.1.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
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

tasks.build {
    doLast {
        copy {
            from("src/main/webapp")
            into("build/resources/main")
        }
    }
}