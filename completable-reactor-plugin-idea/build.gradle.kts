plugins {
    java
    kotlin("jvm")
    id("org.jetbrains.intellij") version "1.13.3"
    id("org.openjfx.javafxplugin") version "0.0.14"
}


javafx {
    version = "11"
    modules("javafx.controls", "javafx.swing")
}


dependencies {

    implementation(project(":completable-reactor-graph-kotlin"))
    implementation(project(":completable-reactor-graph-viewer"))
    implementation(project(":completable-reactor-model"))
    implementation(project(":completable-reactor-parser"))
    implementation(project(":completable-reactor-model"))

    implementation(Libs.kotlin_reflect)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.kotlin_jdk8)
}

intellij {
    version.set("2020.2")
    pluginName.set("Completable Reactor")
    updateSinceUntilBuild.set(false)
}



