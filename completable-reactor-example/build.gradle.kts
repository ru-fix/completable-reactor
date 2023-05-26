plugins {
    java
    kotlin("jvm")
}

dependencies {

    implementation(project(":completable-reactor-runtime"))
    implementation(project(":completable-reactor-graph-kotlin"))

    implementation(Libs.kotlin_logging)

    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.kotlin_reflect)

    implementation(Libs.spring_beans)
    implementation(Libs.spring_context)

    implementation(Libs.aggregating_profiler)

    implementation(Libs.lombok)
    annotationProcessor(Libs.lombok)

    implementation(Libs.javax_annotations)

    testImplementation(Libs.spring_test)


    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
}
