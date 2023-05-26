plugins {
    java
    kotlin("jvm")
}

dependencies {
    implementation(project(":completable-reactor-model"))
    api(project(":completable-reactor-graph"))

    api(Libs.aggregating_profiler)

    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.kotlin_logging)


    testImplementation(Libs.mockito)
    testImplementation(Libs.mockito_kotiln)

    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
    testImplementation(Libs.hamcrest)
}
