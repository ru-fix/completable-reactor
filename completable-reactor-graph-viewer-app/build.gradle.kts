import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")

}

dependencies {
    compile(project(":completable-reactor-graph-viewer"))
    compile(project(":completable-reactor-parser"))

    compile(Libs.kotlin_logging)

    compile(Libs.kotlin_jdk8)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_reflect)


    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testCompile(project(":completable-reactor-test-utils"))

    compile(Libs.slf4j_simple)
}

//TODO: deploy to maven central uberjar with included dependencies

tasks {
    withType<ShadowJar> {
        manifest{
            attributes["Main-Class"] = "ru.fix.completable.reactor.graph.viewer.app.Main"
        }
    }
}