import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")
    id("org.jetbrains.intellij") version "0.6.3"
    id("org.openjfx.javafxplugin") version "0.0.9"
}


javafx {
    version = "11"
    modules("javafx.controls")
    modules("javafx.swing")
}


dependencies {

    compile(project(":completable-reactor-graph-kotlin"))
    compile(project(":completable-reactor-graph-viewer"))
    compile(project(":completable-reactor-model"))
    compile(project(":completable-reactor-parser"))
    compile(project(":completable-reactor-model"))

    compile(Libs.kotlin_reflect)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_jdk8)
}

intellij {
    version = "2020.2"
    pluginName = "Completable Reactor"
    updateSinceUntilBuild = false
}



