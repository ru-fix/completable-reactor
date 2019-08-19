import org.gradle.kotlin.dsl.*

plugins {
    java
    kotlin("jvm")
}

dependencies {
    compile(project(":completable-reactor-runtime"))
    compile(Libs.aggregatingProfiler)

    compile(Libs.spring_beans)
    compile(Libs.spring_context)
    testCompile(Libs.spring_test)

    compile(Libs.kotlin_jdk8)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_reflect)

    compile(Libs.javax_annotations)

    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
}
