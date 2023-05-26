plugins {
    java
    kotlin("jvm")
}

dependencies {

    api(project(":completable-reactor-graph"))

    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.kotlin_reflect)
    api(Libs.kotlin_coroutines_core)
    implementation(Libs.kotlin_coroutines_jdk)

    implementation(Libs.kotlin_logging)

    testImplementation(Libs.apacheCommonsLang)

    testImplementation(project(":completable-reactor-runtime"))
    testImplementation(Libs.aggregating_profiler)
    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
    testImplementation(Libs.hamcrest)
    testImplementation(Libs.hamkrest)
}
