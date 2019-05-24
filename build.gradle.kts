import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "1.3.31"
    java
}

group = "com.ruman"
version = "1.0-SNAPSHOT"
val mainClassNameStr = "$group.hnews.HNewsKt"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jsoup:jsoup:1.12.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
   manifest {
       attributes["Main-Class"] = mainClassNameStr
   }
    baseName = "hnews"
    from(configurations.compile.map { if (it.isDirectory) it else zipTree(it) })
}
