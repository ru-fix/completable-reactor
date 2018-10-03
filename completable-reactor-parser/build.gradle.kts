import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")

    id("antlr")
}

dependencies {

    compile(project(":completable-reactor-model"))

    compile(Libs.kotlin_jdk8)
    compile(Libs.kotlin_stdlib)
    compile(Libs.kotlin_reflect)
    compile(Libs.kotlin_logging)

    compile(Libs.antlrRuntime)

    antlr(Libs.antlrAll)


    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
    testCompile(project(":completable-reactor-test-utils"))

    testCompile(Libs.hamcrest)
}

val antlrDestinationDir = "src/main/java/ru/fix/completable/reactor/parser/java/antlr"

tasks.register("generateAntlr"){
    description = """
        Generates java source from antlr
        Antlr source generated and replaced everything within $antlrDestinationDir
        Antlr task generation is disabled by default since antlr g4 files changes rarely
        and it tooks time to generate java code.
    """.trimIndent()
    doLast{
        tasks.withType<AntlrTask>{
            enabled = true
            includes.add("Graph.g4")
            arguments.addAll(listOf(
                    "-no-visitor",
                    "-no-listener",
                    "-package",
                    "ru.fix.completable.reactor.parser.java.antlr"
            ))
            outputDirectory = project.projectDir.resolve(antlrDestinationDir)

            execute()
        }
    }
}

tasks {
    withType<AntlrTask> {
        enabled = false
    }
}


