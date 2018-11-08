package ru.fix.completable.reactor.graph.viewer.app

import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import mu.KotlinLogging
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ru.fix.completable.reactor.graph.viewer.gl.GraphViewer
import ru.fix.completable.reactor.graph.viewer.gl.Shortcut
import ru.fix.completable.reactor.graph.viewer.gl.ShortcutType
import ru.fix.completable.reactor.model.CompileTimeGraph
import ru.fix.completable.reactor.model.Source
import ru.fix.completable.reactor.parser.java.JavaSourceParser
import ru.fix.completable.reactor.test.utils.ProjectFileResolver
import java.nio.file.Files
import java.nio.file.Paths


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

            println("goToSource: $source\n>$sourceContext")
        }

        override fun goToSubgraph(subgraphPayloadType: String) {
            println("goToSubgraph: " + subgraphPayloadType)
        }

        override fun coordinatesChanged(graphModel: CompileTimeGraph) {
            println("coordinatesChanged")
        }
    }

    override fun start(stage: Stage?) {
        val filePath = parameters.raw[0]
        val javaSourceContext = Files.newBufferedReader(Paths.get(filePath)).use { it.readText() }

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
    @Disabled
    @Test
    fun `open java graph for purchase payload`() {
        Application.launch(
                ProjectFileResolver().resolvePath(
                        "completable-reactor-example/src/main/java/ru/fix/completable/reactor/example/PurchaseGraph.java"
                ).toString()
        )
    }

    /**
     * Manual UI Test
     * Opens given java source file and display graph
     */
    @Disabled
    @Test
    fun `open kotlin graph for subscribe payload`() {
        Application.launch(
                ProjectFileResolver().resolvePath(
                        "completable-reactor-example/src/main/kotlin/ru/fix/completable/reactor/example/SubscribeGraph.kt"
                ).toString()
        )
    }

    /**
     * Manual UI Test
     * Opens given java source file and display graph
     */
    @Disabled
    @Test
    fun `open source with several graphs`() {
        Application.launch(
                ProjectFileResolver().resolvePath(
                        "completable-reactor-runtime/src/test/java/ru/fix/completable/reactor/runtime/tests/GlCompletableReactorTest.java"
                ).toString()
        )
    }

    /**
     * Manual UI Test
     * Opens given java source file and display graph
     */
    @Disabled
    @Test
    fun `open kotlin source with several graphs`() {
        Application.launch(
                ProjectFileResolver().resolvePath(
                        "completable-reactor-graph-kotlin/src/test/kotlin/ru/fix/completable/reactor/graph/kotlin/KotlinGraphTest.kt"
                ).toString()
        )
    }

    //TODO Fix documentation and titles on all nodes and popup menues

    //TODO add source file dump if there is parsing or visualization error

}