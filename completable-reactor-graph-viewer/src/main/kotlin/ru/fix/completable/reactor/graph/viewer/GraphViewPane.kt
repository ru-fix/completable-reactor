package ru.fix.completable.reactor.graph.viewer;

import javafx.application.Platform
import javafx.geometry.Point2D
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseEvent
import mu.KotlinLogging
import ru.fix.completable.reactor.model.*
import java.util.concurrent.atomic.AtomicReference


data class Rect(
        val minX: Int,
        val minY: Int,
        val maxX: Int,
        val maxY: Int) {
    val width: Int get() = maxX - minX
    val height: Int get() = maxY - minY

}

private val log = KotlinLogging.logger {}

/**
 * Created by Kamil Asfandiyarov
 */
class GraphViewPane(
        val actionListener: GraphViewer.ActionListener,
        val shortcutProvider: (ShortcutType) -> Shortcut?
) : ScrollPane() {

    val MIN_SCALE = 0.3
    val MAX_SCALE = 2.0

    val ZOOM_CHANGE_FACTOR = 1.15

    private val pane = GraphPane()

    private var oldScrollPosition = Point2D(0.5, 0.5)

    var graphModel: CompileTimeGraph? = null
        private set

    init {

        pane.getStyleClass().add("graphViewer")

        this.setPannable(true)
        this.vbarPolicy = ScrollBarPolicy.ALWAYS
        this.hbarPolicy = ScrollBarPolicy.ALWAYS


        this.setContent(pane)


        pane.setOnScroll { scrollEvent ->

            if (scrollEvent.isControlDown) {
                scrollEvent.consume()

                var zoomChangeFactor =
                        if (scrollEvent.deltaY > 0) {
                            ZOOM_CHANGE_FACTOR
                        } else {
                            1 / ZOOM_CHANGE_FACTOR
                        }

                val newScaleX = pane.scaleX * zoomChangeFactor
                val newScaleY = pane.scaleY * zoomChangeFactor

                if (newScaleX > MIN_SCALE && newScaleX < MAX_SCALE && newScaleY > MIN_SCALE && newScaleY < MAX_SCALE) {
                    pane.scaleX = newScaleX
                    pane.scaleY = newScaleY
                }

            } else {
                /**
                 * To prevent strange behavior on scroll within IDE in MacOS
                 */
                scrollEvent.consume()
            }
        }


        initializePopupMenu()

    }

    fun initializePopupMenu() {
        val contextMenu = ContextMenu()

        val shortcutTitleSuffix = shortcutProvider(ShortcutType.GOTO_GRAPH)?.getTitle()?.let { " ($it)" } ?: ""

        val buildGraphMenuItem = MenuItem("Graph location$shortcutTitleSuffix")
        buildGraphMenuItem.setOnAction { graphModel?.startPoint?.source?.let { actionListener.goToSource(it) } }
        contextMenu.items.add(buildGraphMenuItem)


        pane.setOnContextMenuRequested { contextMenuEvent ->
            contextMenu.show(pane, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY())
            contextMenuEvent.consume()
        }

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, { event ->
            contextMenu.hide()
        })
    }

    fun openGraph(graphModel: CompileTimeGraph, resetViewer: Boolean = true): GraphViewPane {
        this.graphModel = graphModel
        if (!resetViewer) {
            this.oldScrollPosition = Point2D(hvalue, vvalue)
        }

        pane.children.clear()

        val graphNodeTreeForAutoLayout = AtomicReference<GraphNode>()


        /**
         * Handler Nodes
         */
        val handlers = HashMap<Handler, HandlerNode>()

        for (handler in graphModel.handlers.values) {
            val handlerNode = HandlerNode(
                    handler,
                    actionListener)

            handlers[handler] = handlerNode
            pane.children.add(handlerNode)
        }

        /**
         * Subgraph Nodes
         */
        val subgraphs = HashMap<Subgraph, SubgraphNode>()

        for (subgraph in graphModel.subgraphs.values) {
            val subgraphNode = SubgraphNode(
                    subgraph,
                    actionListener
            )
            subgraphs[subgraph] = subgraphNode
            pane.children.add(subgraphNode)
        }

        /**
         * StartPoint Node
         */
        val startPointNode = StartPointNode(
                graphModel.startPoint,
                graphModel,
                actionListener)
        pane.children.add(startPointNode)

        graphNodeTreeForAutoLayout.set(startPointNode)

        /**
         * EndPoint Node
         */
        val endpoints = HashMap<EndPoint, EndPointNode>()
        for (endPoint in graphModel.endpoints.values) {
            val endPointNode = EndPointNode(
                    endPoint,
                    actionListener)
            endpoints[endPoint] = endPointNode
            pane.children.addAll(endPointNode)
        }

        /**
         * Mergers
         */
        val mergers = HashMap<Merger, MergerNode>()
        for (merger in graphModel.mergers.values) {
            val mergerNode = MergerNode(
                    merger,
                    actionListener)
            mergers[merger] = mergerNode
            pane.children.add(mergerNode)
        }

        /**
         * Routers
         */
        val routers = HashMap<Router, RouterNode>()
        for (router in graphModel.routers.values) {
            val routerNode = RouterNode(
                    router,
                    actionListener)
            routers[router] = routerNode
            pane.children.add(routerNode)
        }

        fun transitionable(figure: TransitionableFigure): GraphNode =
                when (figure) {
                    is Merger -> mergers[figure]!!
                    is Router -> routers[figure]!!
                    else -> throw IllegalArgumentException("figure: $figure")
                }

        fun figureNode(figure: Figure): GraphNode? {
            return when (figure) {
                is Subgraph -> subgraphs[figure]
                is Handler -> handlers[figure]
                is Merger -> mergers[figure]
                is Router -> routers[figure]
                is EndPoint -> endpoints[figure]
                is StartPoint -> startPointNode
            }
        }

        /**
         * Transition line: handler -> merger
         */
        graphModel.handlers.values.asSequence()
                .mapNotNull { handler ->
                    graphModel.mergers[handler.name]?.let { merger ->
                        Pair(handlers[handler]!!, mergers[merger]!!)
                    }
                }
                .forEach { (handlerNode, mergerNode) ->

                    val line = TransitionLine(pane, handlerNode, mergerNode, null, actionListener)
                    pane.children.add(line)
                    line.toBack()

                    handlerNode.graphChildren.add(mergerNode)
                }

        /**
         * Transition line: subgraph -> merger
         */
        graphModel.subgraphs.values.asSequence()
                .mapNotNull { subgraph ->
                    graphModel.mergers[subgraph.name]?.let { merger ->
                        Pair(subgraphs[subgraph]!!, mergers[merger]!!)
                    }
                }
                .forEach { (subgraphNode, mergerNode) ->

                    val line = TransitionLine(pane, subgraphNode, mergerNode, null, actionListener)
                    pane.children.add(line)
                    line.toBack()

                    subgraphNode.graphChildren.add(mergerNode)
                }

        /**
         * Transition line: merger, router -> any
         */
        graphModel.transitionable.values.asSequence()
                .filter { it.transitions.isNotEmpty() }
                .map { Pair(it, transitionable(it)) }
                .forEach { (transitionable, transitionableNode) ->
                    for (transition in transitionable.transitions) {

                        val toNode = figureNode(transition.target)
                        if (toNode == null) {
                            log.error {
                                "Failed to build transition line" +
                                        " from ${transitionableNode.figure} to ${transition.target}." +
                                        " Skip line and children."
                            }
                            return@forEach
                        }

                        val line = TransitionLine(
                                pane,
                                transitionableNode,
                                toNode,
                                transition,
                                actionListener)

                        pane.children.add(line)
                        line.toBack()

                        transitionableNode.graphChildren.add(toNode)
                    }
                }


        /**
         * StartPoint outgoing transitions
         */
        graphModel.startPoint.handleBy.forEach { figure ->

            val toNode = figureNode(figure)
            if (toNode == null) {
                log.error {
                    "Failed to build transition line" +
                            " from StartPoint to ${figure}." +
                            " Skip transition and children."
                }
                return@forEach
            }

            val line = TransitionLine(
                    pane,
                    startPointNode,
                    toNode,
                    null,
                    actionListener
            )
            pane.children.add(line)
            line.toBack()

            startPointNode.graphChildren.add(toNode)
        }


        /**
         * Bring mergers and routers to front
         */
        mergers.values.forEach { it.toFront() }
        routers.values.forEach { it.toFront() }


        enableNodeDragging()

        // when scroll pane detects that content size changes
        // scroll pane skin tries to save scroll positions
        // and reset to 0 all scrolls if initially prefWidth and prefHeight not set
        // due to wrong calculations
        // Scroll Pane skin tries to safe hvalue/vvalue during content pane resize.
        // Skin fails calculation and set wrong hvalue/vvalue to scrolls.
        // Fixing hvalue/vvalue changes generated by skin with hack

        hvalueProperty().addListener({ observable, old, new ->
            val positionX = targetViewportPositionX
            if (positionX != null) {
                if (positionX != new) {
                    Platform.runLater {
                        targetViewportPositionX = null
                        hvalue = positionX
                    }
                }
            }
        })

        vvalueProperty().addListener({ observable, old, new ->
            val positionY = targetViewportPositionY
            if (positionY != null) {
                if (positionY != new) {
                    Platform.runLater {
                        targetViewportPositionY = null
                        vvalue = positionY
                    }
                }
            }


        })

        pane.requestResize({
            val position = if (resetViewer) {
                Point2D(0.5, 0.5)

            } else {
                oldScrollPosition
            }

            targetViewportPositionX = position.x
            targetViewportPositionY = position.y

            hvalue = position.x
            vvalue = position.y
        })


        //TODO during first opening scroll pane so payload will be displayed in top middle position
        return this
    }

    fun enableNodeDragging() {
        pane.children.asSequence()
                .mapNotNull { it as? GraphNode }
                .forEach {
                    NodeDragger.attach(
                            it,
                            object : NodeDraggerListener {
                                override fun modelChanging() {
                                    pane.requestLayout()
                                }

                                override fun modelChanged() {
                                    pane.requestResize({})
                                    graphModel?.let { actionListener.coordinatesChanged(it) }
                                }
                            },
                            CoordinateTranslator(pane))
                }
    }

    var targetViewportPositionX: Double? = null
    var targetViewportPositionY: Double? = null

}