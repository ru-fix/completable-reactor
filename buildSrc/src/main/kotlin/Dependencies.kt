object Vers {
    val kotlin = "1.3.0"
    val sl4j = "1.7.25"
    val dokkav = "0.9.16"
    val gradleReleasePlugin = "1.2.20"
    val junit = "5.2.0"

    val aggregatingProfiler = "1.4.7"
    val dynamicProperty = "1.0.5"

    val spring = "5.1.0.RELEASE"
    val kotlinCoroutines = "1.0.1"
    val asciidoctor = "1.5.9.1"

    val jmh = "1.21"
}

object Libs {
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Vers.kotlin}"
    val kotlin_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Vers.kotlin}"
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Vers.kotlin}"

    val gradleReleasePlugin = "ru.fix:gradle-release-plugin:${Vers.gradleReleasePlugin}"
    val dokkaGradlePlugin = "org.jetbrains.dokka:dokka-gradle-plugin:${Vers.dokkav}"
    val shadowPlugin = "com.github.jengelman.gradle.plugins:shadow:2.0.4"

    val slf4j_api = "org.slf4j:slf4j-api:${Vers.sl4j}"
    val slf4j_simple = "org.slf4j:slf4j-simple:${Vers.sl4j}"

    val mockito_kotiln = "com.nhaarman:mockito-kotlin-kt1.1:1.6.0"
    val kotlin_logging = "io.github.microutils:kotlin-logging:1.5.9"

    val log4j = "org.apache.logging.log4j:log4j-core:2.11.1"

    val junit_api = "org.junit.jupiter:junit-jupiter-api:${Vers.junit}"
    val junit_parametri = "org.junit.jupiter:junit-jupiter-params:${Vers.junit}"
    val junit_engine = "org.junit.jupiter:junit-jupiter-engine:${Vers.junit}"
    val mockito = "org.mockito:mockito-core:2.21.0"


    val aggregatingProfiler = "ru.fix:aggregating-profiler:${Vers.aggregatingProfiler}"
    val dynamicProperty = "ru.fix:dynamic-property-api:${Vers.dynamicProperty}"

    val springTest = "org.springframework:spring-test:${Vers.spring}"
    val springBeans = "org.springframework:spring-beans:${Vers.spring}"
    val springContext = "org.springframework:spring-context:${Vers.spring}"


    val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Vers.kotlinCoroutines}"
    val kotlinCoroutinesJdk = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Vers.kotlinCoroutines}"

    val lombok = "org.projectlombok:lombok:1.18.4"
    val apacheCommonsLang = "org.apache.commons:commons-lang3:3.5"
    val antlrRuntime = "org.antlr:antlr4-runtime:4.7.1"
    val antlrAll = "org.antlr:antlr4:4.7.1"

    val hamcrest = "org.hamcrest:hamcrest-all:1.3"

    val asciidoctor = "org.asciidoctor:asciidoctor-gradle-plugin:${Vers.asciidoctor}"

    val jmhGradlePlugin = "me.champeau.gradle:jmh-gradle-plugin:0.4.7"
    val jmh = "org.openjdk.jmh:jmh-core:${Vers.jmh}"
    val jmhGeneratorAnn = "org.openjdk.jmh:jmh-generator-annprocess:${Vers.jmh}"
    val jmhGeneratorBytecode = "org.openjdk.jmh:jmh-generator-bytecode:${Vers.jmh}"
}