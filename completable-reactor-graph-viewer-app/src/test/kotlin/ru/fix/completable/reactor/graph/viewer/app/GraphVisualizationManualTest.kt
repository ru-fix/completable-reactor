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
 * Manual test.
 * Opens java source file and display graph.
 * source parsing, model building is covered by other test.
 * This unit purpose is to ensure that there is no visual artifacts during graph visualization.
 */
class GraphVisualizationManualTest : Application() {

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
            } catch (exc: Exception) {
                exc.printStackTrace()
            }

        }
    }

    override fun start(stage: Stage?) {
        val javaSourceContext = javaClass.getResource(parameters.raw[0]).readText()

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
        stage!!.scene = viewer.scene

        stage.show()
    }

    /**
     * Manual UI Test
     * Opens given java source file and display graph
     */
    @Ignore
    @Test
    fun open_graph_for_purchase_payload() {
        Application.launch("/viewer-test-PurchaseGraphConfig.java")
    }

    //TODO add source file dump if there is parsing or visualization error

}