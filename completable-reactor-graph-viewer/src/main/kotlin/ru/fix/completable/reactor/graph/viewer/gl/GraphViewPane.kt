package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.scene.Node
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import ru.fix.completable.reactor.api.gl.model.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by Kamil Asfandiyarov
 */
class GraphViewPane(
        val actionListener: GraphViewer.ActionListener,
        val shortcutProvider: (ShortcutType) -> Shortcut?
) : ScrollPane() {

    val MIN_SCALE = 0.3
    val MAX_SCALE = 2.0

    val WORLD_SIZE = 10000.0
    val ZOOM_CHANGE_FACTOR = 1.15

    val worldSize = WORLD_SIZE

    private val pane = Pane()

    private val translator = CoordinateTranslator(WORLD_SIZE)

    var graphModel: ReactorGraphCompilationModel? = null
        private set

    init {

        pane.getStyleClass().add("graphViewer")

        this.setPannable(true)

        this.setContent(pane)

        pane.setPrefSize(this.worldSize, this.worldSize)
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

        subscribeScrollingPayloadListenerOnResizeEvent();
    }

    fun initializePopupMenu() {
        val contextMenu = ContextMenu()

        val buildGraphMenuItem = MenuItem("Graph build location")
        buildGraphMenuItem.setOnAction { graphModel?.buildGraphSource?.let { actionListener.goToSource(it) } }
        contextMenu.items.add(buildGraphMenuItem)

        val coordinatesMenuItem = MenuItem("Coordinates location")
        coordinatesMenuItem.setOnAction { graphModel?.coordinatesSource?.let { actionListener.goToSource(it) } }
        contextMenu.items.add(coordinatesMenuItem);


        pane.setOnContextMenuRequested { contextMenuEvent ->
            contextMenu.show(pane, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY())
            contextMenuEvent.consume()
        }

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, { event ->
            contextMenu.hide()
        })
    }


    fun openGraph(graphModel: ReactorGraphCompilationModel): GraphViewPane {
        this.graphModel = graphModel

        pane.children.removeAll()

        val positionListener = object : PositionListener {
            override fun positionChanged() {
                actionListener.coordinatesChanged(coordinateItemsFromModel())
            }
        }

        /**
         * Handler Nodes
         */
        val handlers = HashMap<Handler, HandlerNode>()

        for (handler in graphModel.handlers.values) {
            val handlerNode = HandlerNode(
                    translator,
                    handler,
                    actionListener,
                    positionListener)

            handlers[handler] = handlerNode
            pane.children.add(handlerNode)
        }

        /**
         * Subgraph Nodes
         */
        val subgraphs = HashMap<Subgraph, SubgraphNode>()

        for (subgraph in graphModel.subgraphs.values) {
            val subgraphNode = SubgraphNode(
                    translator,
                    subgraph,
                    actionListener,
                    positionListener
            )
            subgraphs[subgraph] = subgraphNode
            pane.children.add(subgraphNode)
        }

        /**
         * StartPoint Node
         */
        val startPointNode = StartPointNode(
                translator,
                graphModel.startPoint,
                actionListener,
                positionListener)
        pane.children.add(startPointNode)


        /**
         * EndPoint Node
         */
        val endpoints = HashMap<EndPoint, EndPointNode>()
        for (endPoint in graphModel.endpoints.values) {
            val endPointNode = EndPointNode(
                    translator,
                    endPoint,
                    actionListener,
                    positionListener)
            endpoints[endPoint] = endPointNode
            pane.children.addAll(endPointNode)
        }

        /**
         * Mergers
         */
        val mergers = HashMap<Merger, MergerNode>()
        for (merger in graphModel.mergers.values) {
            val mergerNode = MergerNode(
                    translator,
                    merger,
                    actionListener,
                    positionListener)
            mergers[merger] = mergerNode
            pane.children.add(mergerNode)
        }

        /**
         * Routers
         */
        val routers = HashMap<Router, RouterNode>()
        for (router in graphModel.routers.values) {
            val routerNode = RouterNode(
                    translator,
                    router,
                    actionListener,
                    positionListener)
            routers[router] = routerNode
            pane.children.add(routerNode)
        }

        fun figureNode(figure: Figure): Node {
            return when (figure) {
                is Subgraph -> subgraphs[figure]!!
                is Handler -> handlers[figure]!!
                is Router -> routers[figure]!!
                is EndPoint -> endpoints[figure]!!
                else -> throw IllegalArgumentException("Invalid type: ${figure.javaClass}")
            }
        }

        /**
         * Transition line: handler -> merger
         */
        graphModel.handlers.values.asIterable()
                .mapNotNull { handler ->
                    graphModel.mergers[handler.name]?.let { merger ->
                        Pair(handlers[handler]!!, mergers[merger]!!)
                    }
                }
                .forEach { (handlerNode, mergerNode) ->

                    val line = TransitionLine(pane, handlerNode, mergerNode, null, actionListener)
                    pane.children.add(line)
                    line.toBack()
                }

        /**
         * Transition line: subgraph -> merger
         */
        graphModel.handlers.values.asIterable()
                .mapNotNull { handler ->
                    graphModel.mergers[handler.name]?.let { merger ->
                        Pair(handlers[handler]!!, mergers[merger]!!)
                    }
                }
                .forEach { (handlerNode, mergerNode) ->

                    val line = TransitionLine(pane, handlerNode, mergerNode, null, actionListener)
                    pane.children.add(line)
                    line.toBack()
                }

        /**
         * Transition line: merger, router -> any
         */
        graphModel.transitionable.values.asIterable()
                .filter { it.transitions.isNotEmpty() }
                .map { Pair(it, mergers[it]!!) }
                .forEach { (merger, mergerNode) ->
                    for (transition in merger.transitions) {

                        val line = TransitionLine(
                                pane,
                                mergerNode,
                                figureNode(transition.target),
                                transition,
                                actionListener)

                        pane.children.add(line)
                        line.toBack()
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
        }


        /**
         * Bring mergers and routers to front
         */
        mergers.values.forEach { it.toFront() }
        routers.values.forEach { it.toFront() }

        /**
         * Scroll pane so Payload Node would be in center position
         */
        this.hvalue = (WORLD_SIZE / 2 + graphModel.startPoint.coordinates.x) / WORLD_SIZE
        this.vvalue = (WORLD_SIZE / 2 + graphModel.startPoint.coordinates.y) / WORLD_SIZE

        enableSingleScrollingPayloadToTopCenterOnFirstResize()

        return this
    }

    private val payloadIsWidthCentralized = AtomicBoolean()
    private val payloadIsHeightCentralized = AtomicBoolean()

    private fun enableSingleScrollingPayloadToTopCenterOnFirstResize() {
        payloadIsWidthCentralized.set(false)
        payloadIsHeightCentralized.set(false)
    }

    private fun subscribeScrollingPayloadListenerOnResizeEvent() {
        this.heightProperty().addListener { observable, oldValue, newValue ->

            val model = graphModel ?: return@addListener

            if (payloadIsHeightCentralized.compareAndSet(false, true)) {
                val startPointY = model.startPoint.coordinates.y
                val approximateMargin = 50.0
                this.vvalue = ((WORLD_SIZE / 2 + startPointY + newValue.toDouble() / 2 - approximateMargin) /
                        WORLD_SIZE)
            }
        }

        this.widthProperty().addListener { observable, oldValue, newValue ->

            val model = graphModel ?: return@addListener

            if (payloadIsWidthCentralized.compareAndSet(false, true)) {
                val startPointX = model.startPoint.coordinates.x
                val approximatePayloadNodeWidth = 100.0
                this.hvalue = (WORLD_SIZE / 2 + startPointX + approximatePayloadNodeWidth / 2) / WORLD_SIZE
            }
        }
    }


    fun coordinateItemsFromModel(): List<CoordinateItem> {
        val result = ArrayList<CoordinateItem>()

        with(graphModel!!) {

            result.add(CoordinateItem(
                    CoordinateItem.Type.START_POINT, startPoint.coordinates.x, startPoint.coordinates.y))

            handlers.values.forEach {
                result.add(CoordinateItem(CoordinateItem.Type.HANDLER, it.coordinates.x, it.coordinates.y))
            }
            subgraphs.values.forEach {
                result.add(CoordinateItem(CoordinateItem.Type.SUBGRAPH, it.coordinates.x, it.coordinates.y))
            }
            routers.values.forEach {
                result.add(CoordinateItem(CoordinateItem.Type.ROUTER, it.coordinates.x, it.coordinates.y))
            }
            mergers.values.forEach {
                result.add(CoordinateItem(CoordinateItem.Type.MERGER, it.coordinates.x, it.coordinates.y))
            }
            endpoints.values.forEach {
                result.add(CoordinateItem(CoordinateItem.Type.END_POINT, it.coordinates.x, it.coordinates.y))
            }
        }
        return result
    }
}