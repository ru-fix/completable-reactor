plugins {
    java
    kotlin("jvm")

    id("antlr")
}

dependencies {

    implementation(project(":completable-reactor-model"))

    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.kotlin_logging)

    implementation(Libs.antlrRuntime)

    antlr(Libs.antlrAll)


    testImplementation(Libs.junit_api)
    testRuntimeOnly(Libs.junit_engine)
    testRuntimeOnly(Libs.slf4j_simple)
    testImplementation(project(":completable-reactor-test-utils"))

    testImplementation(Libs.hamcrest)
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

//            TODO: fix on gradle 5
//            this.execute()
        }
    }
}

tasks {
    withType<AntlrTask> {
        enabled = false
    }
}


