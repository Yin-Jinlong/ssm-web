import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    war
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

group = "cn.yjl"
version = "0.0.1-SNAPSHOT"


java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/central")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/gradle-plugin/")
    }
    mavenCentral()
}

dependencies {
    // spring-boot-jdbc
//    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    // spring-boot-web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // spring webmvc
    implementation("org.springframework:spring-webmvc:6.0.11")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // servlet-api
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
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