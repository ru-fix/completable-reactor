package ru.fix.completable.reactor.graph.viewer.app

import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import mu.KotlinLogging
import ru.fix.completable.reactor.graph.viewer.GraphViewer
import ru.fix.completable.reactor.parser.java.JavaSourceParser

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

private val log = KotlinLogging.logger {}

/**
 * @author Kamil Asfandiyarov
 */

class Main : Application() {

    override fun start(stage: Stage) {
        if (parameters.raw.size <= 0) {
            Platform.exit()
            return
        }

        try {

            //TODO remove support of rg files after upgrade
            val fileContent = String(Files.readAllBytes(
                    Paths.get(parameters.raw[0])),
                    StandardCharsets.UTF_8)

            if (parameters.raw[0].endsWith(".rg")) {

                val viewer = GraphViewer()

                viewer.openGraph(fileContent)
                stage.scene = viewer.scene

                stage.show()
            } else if (parameters.raw[0].endsWith(".java")){

                //TODO add file monitor, reparsing loop, errors in stdout, parsing time

                val parser = JavaSourceParser(object: JavaSourceParser.Listener {
                    override fun error(msg: String) {
                        log.error { msg }
                    }
                })

                val timestamp = System.currentTimeMillis()
                val model = parser.parse(fileContent)

                log.info{ "Parsing took ${System.currentTimeMillis() - timestamp}ms" }

                val viewer2 = ru.fix.completable.reactor.graph.viewer.gl.GraphViewer()
                viewer2.openGraph(model)
                stage.scene = viewer2.scene
            }


        } catch (exc: Exception) {
            log.error(exc.message, exc)
            Platform.exit()
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(*args)
}