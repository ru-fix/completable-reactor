package ru.fix.completable.reactor.graph.viewer.app

import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import mu.KotlinLogging
import org.junit.Ignore
import org.junit.Test
import ru.fix.completable.reactor.api.gl.model.Source
import ru.fix.completable.reactor.graph.viewer.gl.CoordinateItem
import ru.fix.completable.reactor.graph.viewer.gl.GraphViewer
import ru.fix.completable.reactor.graph.viewer.gl.Shortcut
import ru.fix.completable.reactor.graph.viewer.gl.ShortcutType
import ru.fix.completable.reactor.parser.java.JavaSourceParser


private val log = KotlinLogging.logger {}

/**
 * Created by swarmshine on 29.01.2017.
 */

class GraphViewerTest : Application() {

    companion object {
        private var payload: String? = null
    }


    class LogActionListener : GraphViewer.ActionListener {
        override fun goToSource(source: Source) {
            println("goToSource: " + source)
        }

        override fun goToSubgraph(subgraphPayloadType: String) {
            println("goToSubgraph: " + subgraphPayloadType)
        }

        override fun coordinatesChanged(coordinateItems: List<CoordinateItem>) {
            try {
                println("changeCoordinates:\n" + coordinateItems)

                //TODO check code updater
//                val codeUpdater = CodeUpdater()
//                javaClass.getResourceAsStream("/viewer-code-block.txt").use { resource -> println("Code:\n" + codeUpdater.updateCoordinates(IOUtils.toString(resource, StandardCharsets.UTF_8), coordinateItems)) }
            } catch (exc: Exception) {
                exc.printStackTrace()
            }

        }
    }

    override fun start(stage: Stage?) {
        if (stage == null) {
            throw IllegalArgumentException("stage is null")
        }


        val javaSourceContext = javaClass.getResource("/" + payload!!).readText()

        val parser = JavaSourceParser(object: JavaSourceParser.Listener {
            override fun error(msg: String) {
                log.error { msg }
            }
        })

        val model = parser.parse(javaSourceContext)

        val viewer = GraphViewer()
        viewer.registerListener(LogActionListener())

        viewer.setShortcut(ShortcutType.GOTO_BUILD_GRAPH, Shortcut(true, KeyCode.B))

        viewer.openGraph(model)
        stage.scene = viewer.scene

        stage.show()
    }

    /**
     * Manual UI Test
     */
    @Ignore
    @Test
    fun open_graph_for_purchase_payload() {
        payload = "viewer-test-PurchaseGraphConfig.java"
        Application.launch()
    }



}