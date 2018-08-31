import org.gradle.kotlin.dsl.*

plugins {
    java
    kotlin("jvm")
}

dependencies {
    compile(project(":completable-reactor-runtime"))
    compile(Libs.aggregatingProfiler)

    compile(Libs.springBeans)
    compile(Libs.springContext)
    testCompile(Libs.springTest)

    compile(Libs.kotlin_jdk8)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_reflect)

    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
}
