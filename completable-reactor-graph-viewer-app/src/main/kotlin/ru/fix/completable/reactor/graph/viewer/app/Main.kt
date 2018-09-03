package ru.fix.completable.reactor.graph.viewer.app

import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import mu.KotlinLogging
import ru.fix.completable.reactor.parser.java.JavaSourceParser
import ru.fix.crudility.engine.FileWatcher

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
            val filePath = parameters.raw[0]

            if (filePath.endsWith(".java")) {

                log.info { "Open CR2 graph: $filePath" }

                val parser = JavaSourceParser(object : JavaSourceParser.Listener {
                    override fun error(msg: String) {
                        log.error { msg }
                    }
                })

                val viewer2 = ru.fix.completable.reactor.graph.viewer.gl.GraphViewer()
                stage.scene = viewer2.scene

                //TODO: add drag&drop
                FileWatcher(Paths.get(filePath)) {

                    val fileContent = String(Files.readAllBytes(
                            Paths.get(filePath)),
                            StandardCharsets.UTF_8)

                    val timestamp = System.currentTimeMillis()
                    val models = parser.parse(fileContent, filePath)
                    log.info { "Parsing took ${System.currentTimeMillis() - timestamp}ms" }

                    Platform.runLater {
                        viewer2.openGraph(models)
                    }
                }.start()

                stage.show()
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