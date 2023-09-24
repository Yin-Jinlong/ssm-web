import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    war
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
}

rootProject.apply {
    group = "cn.yjl"
    version = "0.0.1-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/gradle-plugin/")
    mavenCentral()
}

configurations.all {
    exclude("com.zaxxer", "HikariCP")
}

dependencies {
    // spring-boot-jdbc
    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.0.4")
    // spring-boot-web
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    // druid
    // https://mvnrepository.com/artifact/com.alibaba/druid-spring-boot-starter
    implementation("com.alibaba:druid-spring-boot-starter:1.2.19")

    // spring-validation
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")

    // servlet-api
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")

    // mybatis
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

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