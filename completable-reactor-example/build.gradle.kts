import org.gradle.kotlin.dsl.*

plugins {
    java
    kotlin("jvm")
}

dependencies {

    compile(project(":completable-reactor-runtime"))
    compile(project(":completable-reactor-graph-kotlin"))

    compile(Libs.kotlin_logging)

    compile(Libs.kotlin_jdk8)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_reflect)

    compile(Libs.spring_beans)
    compile(Libs.spring_context)

    compile(Libs.lombok)
    annotationProcessor(Libs.lombok)

    compile(Libs.javax_annotations)

    testCompile(Libs.spring_test)


    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
}
