package ru.fix.completable.reactor.graph.viewer.gl

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import javafx.scene.Scene
import ru.fix.completable.reactor.api.gl.model.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Kamil Asfandiyarov.
 */
class GraphViewer {
    private val scene: Scene

    private var actionListeners = CopyOnWriteArrayList<ActionListener>()
    private var graphViewPane: GraphViewPane

    private val shortcuts: MutableMap<Shortcut, ShortcutType> = ConcurrentHashMap()


    val graphModel: ReactorGraphCompilationModel
        get() = graphViewPane.graph


    init {
        val actionListener = object : ActionListener {
            fun goToSource(source: ReactorGraphModel.Source) {
                for (listener in actionListeners) {
                    listener.goToSource(source)
                }
            }

            override fun goToSubgraph(subgraphPayloadClass: String) {
                for (listener in actionListeners) {
                    listener.goToSubgraph(subgraphPayloadClass)
                }
            }

            override fun coordinatesChanged(coordinateItems: List<CoordinateItem>) {
                for (listener in actionListeners) {
                    listener.coordinatesChanged(coordinateItems)
                }
            }
        }

        graphViewPane = GraphViewPane(actionListener, Function<ShortcutType, Optional<Shortcut>> { this.getShortcut(it) })

        graphViewPane.setPrefSize(700.0, 600.0)

        scene = Scene(graphViewPane)

        scene.stylesheets.add(javaClass.getResource("/css/styles.css").toExternalForm())

        //Shortcuts
        graphViewPane.setOnKeyReleased { keyEvent ->
            shortcuts.forEach { shortcut, shortcutType ->
                if (shortcut.getPredicate().test(keyEvent)) {
                    when (shortcutType) {
                        ShortcutType.GOTO_SERIALIZATION_POINT -> actionListener.goToSource(graphViewPane.graph.serializationPointSource)
                        else -> throw IllegalStateException()
                    }
                }

            }
        }
    }

    fun openGraph(graph: ReactorGraphCompilationModel) {
        graphViewPane.graph = graph
    }


    fun registerListener(actionListener: ActionListener): GraphViewer {
        actionListeners.add(actionListener)
        return this
    }

    fun setShortcut(shortcutType: ShortcutType, shortcut: Shortcut) {
        shortcuts[shortcut] = shortcutType
    }

    fun getShortcut(shortcutType: ShortcutType): Shortcut? {
        for ((shortcut, type) in shortcuts) {
            if (type == shortcutType) {
                return shortcut
            }
        }
        return null
    }


    interface ActionListener {
        /**
         * Viewer asks IDE to navigate to source code
         *
         * @param source source code location
         */
        fun goToSource(source: Source)

        /**
         * Viewer asks IDE to navigate to subgraph view
         * @param subgraphPayloadType payload class name
         */
        fun goToSubgraph(subgraphPayloadType: String)

        /**
         * Graph nodes coordinates changed
         *
         * @param coordinateItems new coordinates
         */
        fun coordinatesChanged(coordinateItems: List<CoordinateItem>)
    }
}