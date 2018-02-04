package ru.fix.completable.reactor.graph.viewer.gl

import javafx.scene.Scene
import javafx.scene.control.TabPane
import javafx.scene.layout.Pane
import ru.fix.completable.reactor.model.GraphModel
import ru.fix.completable.reactor.model.Source
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Kamil Asfandiyarov.
 */
class GraphViewer {
    var scene: Scene
        private set

    private var actionListeners = CopyOnWriteArrayList<ActionListener>()

    private val viewPaneActionListener = object : ActionListener {
        override fun goToSource(source: Source) {
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

    private val shortcuts: MutableMap<Shortcut, ShortcutType> = ConcurrentHashMap()

    init {
        scene = Scene(Pane())
        scene.stylesheets.add(javaClass.getResource("/css/styles.css").toExternalForm())
    }

    fun openGraph(graphs: List<GraphModel>) {
        if (graphs.size == 1) {

            var graphViewPane = GraphViewPane(viewPaneActionListener, { this.getShortcut(it) })
            graphViewPane.setPrefSize(700.0, 600.0)

            scene.root = graphViewPane

            graphViewPane.openGraph(graphs[0])

            //Shortcuts
            addShortcutListener(graphViewPane)

        } else {
            val tabPane = TabPane()
            scene.root = tabPane

            for (graph in graphs) {
                var graphViewPane = GraphViewPane(viewPaneActionListener, { this.getShortcut(it) })
                graphViewPane.setPrefSize(700.0, 600.0)
                graphViewPane.openGraph(graph)

                addShortcutListener(graphViewPane)
            }
        }
    }

    private fun addShortcutListener(pane: GraphViewPane) {
        pane.setOnKeyReleased { keyEvent ->
            shortcuts.forEach { shortcut, shortcutType ->
                if (shortcut.getPredicate().test(keyEvent)) {
                    when (shortcutType) {
                        ShortcutType.GOTO_GRAPH -> pane.graphModel?.startPoint?.source?.let {
                            viewPaneActionListener.goToSource(it)
                        }
                    }
                }

            }
        }
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