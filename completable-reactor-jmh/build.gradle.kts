import org.gradle.kotlin.dsl.*

plugins {
    java
    kotlin("jvm")
    id("me.champeau.gradle.jmh")
}

jmh{
    warmupIterations = 1
    fork = 1
    threads = 4
    duplicateClassesStrategy  = DuplicatesStrategy.WARN
}


dependencies {
    /**
     * Runtime
     */
    compile(Libs.kotlin_jdk8)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_reflect)
    compile(Libs.kotlinCoroutinesCore)
    compile(Libs.kotlinCoroutinesJdk)

    compile(Libs.jmh)
    compile(Libs.jmhGeneratorAnn)
    compile(Libs.jmhGeneratorBytecode)

    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
}
