plugins {
    java
    kotlin("jvm")
}

dependencies {
    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.kotlin_reflect)


    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
}
