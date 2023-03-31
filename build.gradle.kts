val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    //ktor-server-content-negotiation and ktor-serialization convert Kotlin objects into a serialized form like JSON and vice versa
    //use to format APIs output and to consume user input that is structured in JSON
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    //ktor-server-netty engine allow us to use server functionality without having to rely on an external application container
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    //logback-classic format logs inconsole (implementation of SLF4J)
    implementation("ch.qos.logback:logback-classic:$logback_version")
    //ktor-server-tests and kotlin-test-junit test parts of our Ktor application without having to use the whole HTTP stack in the process
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}