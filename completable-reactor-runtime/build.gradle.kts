import org.gradle.kotlin.dsl.*

plugins {
    java
    kotlin("jvm")
}

dependencies {
    compile(project(":completable-reactor-model"))
    compile(project(":completable-reactor-graph"))



    compile(Libs.dynamic_property)
    compile(Libs.aggregating_profiler)

    compile(Libs.kotlin_jdk8)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_reflect)
    compile(Libs.kotlin_logging)

    testCompile(Libs.mockito)
    testCompile(Libs.mockito_kotiln)


    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
    testCompile(Libs.hamcrest)
}
