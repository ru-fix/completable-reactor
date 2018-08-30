import org.gradle.kotlin.dsl.*

plugins {
    java
    kotlin("jvm")
}

dependencies {
    compile(Libs.springTest)
    compile(Libs.springContext)

    compile(Libs.kotlin_jdk8)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_reflect)

    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
}
