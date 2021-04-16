object Vers {
    //Plugins
    val asciidoctor = "1.5.9.2"
    val kotlin = "1.3.72"
    val kotlin_coroutines = "1.3.5"
    
    val slf4j = "1.7.25"
    val dokka = "0.10.1"
    val gradle_release_plugin = "1.3.17"
    val junit = "5.7.1"

    val aggregating_profiler = "1.6.6"
    val dynamic_property = "2.0.8"

    val spring = "5.1.9.RELEASE"
    

}

object Libs {
    //Plugins
    val gradle_release_plugin = "ru.fix:gradle-release-plugin:${Vers.gradle_release_plugin}"
    val dokka_gradle_plugin = "org.jetbrains.dokka:dokka-gradle-plugin:${Vers.dokka}"
    val nexus_staging_plugin = "io.codearte.nexus-staging"
    val nexus_publish_plugin = "de.marcphilipp.nexus-publish"
    val asciidoctor = "org.asciidoctor:asciidoctor-gradle-plugin:${Vers.asciidoctor}"
    val shadowPlugin = "com.github.jengelman.gradle.plugins:shadow:2.0.4"
    
    //Dependencies
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Vers.kotlin}"
    val kotlin_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Vers.kotlin}"
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Vers.kotlin}"
    val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Vers.kotlin_coroutines}"
    val kotlin_coroutines_jdk = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Vers.kotlin_coroutines}"

    val slf4j_simple = "org.slf4j:slf4j-simple:${Vers.slf4j}"

    val mockito_kotiln = "com.nhaarman:mockito-kotlin-kt1.1:1.6.0"
    val kotlin_logging = "io.github.microutils:kotlin-logging:1.5.9"

    val log4j = "org.apache.logging.log4j:log4j-core:2.11.1"

    val junit_api = "org.junit.jupiter:junit-jupiter-api:${Vers.junit}"
    val junit_parametri = "org.junit.jupiter:junit-jupiter-params:${Vers.junit}"
    val junit_engine = "org.junit.jupiter:junit-jupiter-engine:${Vers.junit}"
    val mockito = "org.mockito:mockito-core:2.21.0"


    val aggregating_profiler = "ru.fix:aggregating-profiler:${Vers.aggregating_profiler}"
    val dynamic_property = "ru.fix:dynamic-property-api:${Vers.dynamic_property}"

    val spring_test = "org.springframework:spring-test:${Vers.spring}"
    val spring_beans = "org.springframework:spring-beans:${Vers.spring}"
    val spring_context = "org.springframework:spring-context:${Vers.spring}"


    val javax_annotations = "javax.annotation:javax.annotation-api:1.3.2"
    val lombok = "org.projectlombok:lombok:1.18.8"
    val apacheCommonsLang = "org.apache.commons:commons-lang3:3.5"
    val antlrRuntime = "org.antlr:antlr4-runtime:4.7.1"
    val antlrAll = "org.antlr:antlr4:4.7.1"

    val hamcrest = "org.hamcrest:hamcrest-all:1.3"
    val hamkrest = "com.natpryce:hamkrest:1.7.0.0"
}