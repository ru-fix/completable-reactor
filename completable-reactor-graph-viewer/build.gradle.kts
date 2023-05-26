plugins {
    java
    kotlin("jvm")
    id ("org.openjfx.javafxplugin") version "0.0.8"
}

javafx {
    version = "11"
    modules("javafx.controls")
}

dependencies {

    implementation(project(":completable-reactor-model"))

    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.kotlin_logging)


    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
}
