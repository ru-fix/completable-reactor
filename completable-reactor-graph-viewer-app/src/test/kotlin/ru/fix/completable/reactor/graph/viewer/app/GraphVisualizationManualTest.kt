package ru.fix.completable.reactor.graph.viewer.app

import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import mu.KotlinLogging
import org.junit.Ignore
import org.junit.Test
import ru.fix.completable.reactor.graph.viewer.gl.GraphViewer
import ru.fix.completable.reactor.graph.viewer.gl.Shortcut
import ru.fix.completable.reactor.graph.viewer.gl.ShortcutType
import ru.fix.completable.reactor.graph.viewer.gl.code.CodeUpdater
import ru.fix.completable.reactor.model.GraphModel
import ru.fix.completable.reactor.model.Source
import ru.fix.completable.reactor.parser.java.JavaSourceParser


private val log = KotlinLogging.logger {}

/**
 * Manual test.
 * Opens java source file and display graph.
 * source parsing, model building is covered by other test.
 * This unit purpose is to ensure that there is no visual artifacts during graph visualization.
 */
class GraphVisualizationManualTest : Application() {

    class LogActionListener(val javaSource: String) : GraphViewer.ActionListener {
        val sourceLines = javaSource.split('\n')

        override fun goToSource(source: Source) {
            val sourceContext = source.line?.let {
                (it - 5..it + 5)
                        .map {
                            if (it >= 0 && it < sourceLines.size) {
                                if (it == source.line!! - 1) ">>${sourceLines[it]}" else sourceLines[it]
                            } else {
                                ""
                            }
                        }
                        .joinToString("\n>", ">")
            }

            println("goToSource: $source\n>${sourceContext ?: "unknown context"}")
        }

        override fun goToSubgraph(subgraphPayloadType: String) {
            println("goToSubgraph: " + subgraphPayloadType)
        }

        override fun coordinatesChanged(graphModel: GraphModel) {
            println("coordinatesChanged")
        }
    }

    override fun start(stage: Stage?) {
        val filePath = parameters.raw[0]
        val javaSourceContext = javaClass.getResource(filePath).readText()

        val parser = JavaSourceParser(object : JavaSourceParser.Listener {
            override fun error(msg: String) {
                log.error { msg }
            }
        })

        val models = parser.parse(javaSourceContext, filePath)

        val viewer = GraphViewer()
        viewer.registerListener(LogActionListener(javaSourceContext))

        viewer.setShortcut(ShortcutType.GOTO_GRAPH, Shortcut(true, KeyCode.B))

        viewer.openGraph(models)
        stage!!.scene = viewer.scene

        stage.show()
    }

    /**
     * Manual UI Test
     * Opens given java source file and display graph
     */
    @Ignore
    @Test
    fun `open graph for purchase payload`() {
        Application.launch("/viewer-test-PurchaseGraphConfig.java.txt")
    }

    /**
     * Manual UI Test
     * Opens given java source file and display graph
     */
    @Ignore
    @Test
    fun `open source with two graphs`() {
        Application.launch("/viewer-test-two-test-graphs-in-one-source.java.txt")
    }

    //TODO Fix documentation and titles on all nodes and popup menues

    //TODO add source file dump if there is parsing or visualization error

}