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
    compile(Libs.kotlinCoroutinesCore)
    compile(Libs.kotlinCoroutinesJdk)

    testCompile(Libs.apacheCommonsLang)

    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
}
