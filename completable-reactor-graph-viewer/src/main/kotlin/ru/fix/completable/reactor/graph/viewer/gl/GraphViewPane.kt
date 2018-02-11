package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseEvent
import mu.KotlinLogging
import ru.fix.completable.reactor.model.*
import java.util.concurrent.atomic.AtomicReference

interface PositionListener {
    fun positionChanged()
}

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

    private val translator = CoordinateTranslator(pane)

    private val autoLayout = AutoLayout()

    var graphModel: GraphModel? = null
        private set

    init {

        pane.getStyleClass().add("graphViewer")

        this.setPannable(true)

        this.setContent(pane)


        this.setVvalue(0.5)
        this.setHvalue(0.5)


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

        val buildGraphMenuItem = MenuItem("Graph location")
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


    fun openGraph(graphModel: GraphModel): GraphViewPane {
        this.graphModel = graphModel

        pane.children.removeAll()

        val graphNodeTreeForAutoLayout = AtomicReference<GraphNode>()

        val positionListener = object : PositionListener {

            private val nodeTree: GraphNode by lazy(LazyThreadSafetyMode.NONE) { graphNodeTreeForAutoLayout.get() }

            override fun positionChanged() {

                actionListener.coordinatesChanged(graphModel)
            }
        }

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

        fun figureNode(figure: Figure): GraphNode {
            return when (figure) {
                is Subgraph -> subgraphs[figure]!!
                is Handler -> handlers[figure]!!
                is Merger -> mergers[figure]!!
                is Router -> routers[figure]!!
                is EndPoint -> endpoints[figure]!!
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
         * Transition line: merger, router -> any
         */
        graphModel.transitionable.values.asSequence()
                .filter { it.transitions.isNotEmpty() }
                .map { Pair(it, transitionable(it)) }
                .forEach { (transitionable, transitionableNode) ->
                    for (transition in transitionable.transitions) {

                        val line = TransitionLine(
                                pane,
                                transitionableNode,
                                figureNode(transition.target),
                                transition,
                                actionListener)

                        pane.children.add(line)
                        line.toBack()

                        transitionableNode.graphChildren.add(figureNode(transition.target))
                    }
                }


        /**
         * StartPoint outgoing transitions
         */
        graphModel.startPoint.handleBy.forEach { figure ->
            val line = TransitionLine(
                    pane,
                    startPointNode,
                    figureNode(figure),
                    null,
                    actionListener
            )
            pane.children.add(line)
            line.toBack()

            startPointNode.graphChildren.add(figureNode(figure))
        }


        /**
         * Bring mergers and routers to front
         */
        mergers.values.forEach { it.toFront() }
        routers.values.forEach { it.toFront() }


        enableNodeDragging()

        updatePaneSizeIfRequired()

        return this
    }


    fun scrollToStartPoint() {

        val node = pane.children.find { it is StartPointNode } ?: return

//        val model = graphModel ?: return
//
//        val coordinates = model.startPoint.coordinates ?: DEFAULT_COORDINATES
//
//        val minViewportCenterX = this.viewportBounds.width / 2
//        val minViewportCenterY = this.viewportBounds.height / 2
//
//        val maxViewportCenterX = pane.prefWidth - this.viewportBounds.width / 2
//        val maxViewportCenterY = pane.prefHeight - this.viewportBounds.height / 2
//
//        val paneCoordinateToDisplayX = (coordinates.x - pane.graphBordersInModelCoordinates.minX)
//        val paneCoordinateToDisplayY = (coordinates.y - pane.graphBordersInModelCoordinates.minY)
//
//        val normalizedPaneCoordinateToDisplayX =
//                when {
//                    paneCoordinateToDisplayX < minViewportCenterX -> minViewportCenterX
//                    paneCoordinateToDisplayX > maxViewportCenterX -> maxViewportCenterX
//                    else -> paneCoordinateToDisplayX.toDouble()
//                }
//        val normalizedPaneCoordinateToDisplayY =
//                when {
//                    paneCoordinateToDisplayY < minViewportCenterY -> minViewportCenterY
//                    paneCoordinateToDisplayY > maxViewportCenterY -> maxViewportCenterY
//                    else -> paneCoordinateToDisplayY.toDouble()
//                }
//
//        hvalue = normalizedPaneCoordinateToDisplayX / (maxViewportCenterX - minViewportCenterX)
//        vvalue = normalizedPaneCoordinateToDisplayY / (maxViewportCenterY - minViewportCenterY)

        //TODO during first opening scroll pane so payload will be displayed in top middle position
    }

    fun enableNodeDragging() {
        pane.children.asSequence()
                .mapNotNull { it as? GraphNode }
                .forEach {
                    NodeDragger.attach(it, object : NodeDraggerListener {
                        override fun modelChanging() {
                            pane.requestLayout()

                        }

                        override fun modelChanged() {
                            updatePaneSizeIfRequired()
                            pane.requestLayout()
                            graphModel?.let { actionListener.coordinatesChanged(it) }
                        }
                    })
                }
    }

    fun updatePaneSizeIfRequired() {
        val GRAPH_PANE_MIN_BORDER_SIZE = 2048

        val model = graphModel ?: return

        val graphBorders = (
                sequenceOf(model.startPoint)
                        + model.handlers.values.asSequence()
                        + model.mergers.values.asSequence()
                        + model.routers.values.asSequence()
                        + model.subgraphs.values.asSequence()
                        + model.endpoints.values.asSequence())
                .map { it.coordinates ?: DEFAULT_COORDINATES }
                .map {
                    Rect(
                            it.x,
                            it.y,
                            it.x,
                            it.y)
                }
                .reduce { acc, rect ->
                    Rect(
                            Math.min(acc.minX, rect.minX),
                            Math.min(acc.minY, rect.minY),
                            Math.max(acc.maxX, rect.maxX),
                            Math.max(acc.maxY, rect.maxY)
                    )
                }
                .let {

                    val resizeWidth =
                            if (it.width * 2 < GRAPH_PANE_MIN_BORDER_SIZE) {
                                GRAPH_PANE_MIN_BORDER_SIZE - it.width
                            } else {
                                it.width
                            }
                    val resizeHeight =
                            if (it.height * 2 < GRAPH_PANE_MIN_BORDER_SIZE) {
                                GRAPH_PANE_MIN_BORDER_SIZE - it.height
                            } else {
                                it.height
                            }

                    Rect(
                            it.minX - resizeWidth / 2,
                            it.minY - resizeHeight / 2,
                            it.maxX + resizeWidth / 2,
                            it.maxY + resizeHeight / 2
                    )
                }

        val targetWidth = graphBorders.width.toDouble()
        val targetHeight = graphBorders.height.toDouble()

        if (pane.prefWidth != targetWidth || pane.prefHeight != targetHeight) {

            //TODO: fix scroll during resizing of the content

            pane.graphBordersInModelCoordinates = graphBorders
            pane.prefWidth = targetWidth
            pane.prefHeight = targetHeight
        }
    }
}