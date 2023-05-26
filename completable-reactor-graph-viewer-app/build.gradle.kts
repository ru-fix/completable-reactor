import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    id("org.openjfx.javafxplugin") version "0.0.9"
}

javafx {
    version = "11"
    modules("javafx.controls")
}

dependencies {
    implementation(project(":completable-reactor-graph-viewer"))
    implementation(project(":completable-reactor-parser"))

    implementation(Libs.kotlin_logging)

    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.kotlin_reflect)


    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testImplementation(project(":completable-reactor-test-utils"))
    testImplementation(project(":completable-reactor-model"))

    implementation(Libs.slf4j_simple)
}

//TODO: deploy to maven central uberjar with included dependencies

tasks {
    withType<ShadowJar> {
        manifest {
            attributes["Main-Class"] = "ru.fix.completable.reactor.graph.viewer.app.Main"
        }
    }
}