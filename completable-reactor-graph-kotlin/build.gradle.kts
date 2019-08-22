import org.gradle.kotlin.dsl.*

plugins {
    java
    kotlin("jvm")
}

dependencies {

    compile(project(":completable-reactor-graph"))
    compile(project(":completable-reactor-runtime"))

    compile(Libs.kotlin_jdk8)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_reflect)
    compile(Libs.kotlin_coroutines_core)
    compile(Libs.kotlin_coroutines_jdk)

    testCompile(Libs.apacheCommonsLang)

    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
    testImplementation(Libs.hamcrest)
    testImplementation(Libs.hamkrest)
}
