package ru.fix.completable.reactor.graph.viewer;

import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import ru.fix.completable.reactor.model.Subgraph

/**
 * Created by swarmshine on 29.01.2017.
 */
class SubgraphNode(
        val subgraph: Subgraph,
        val actionListener: GraphViewer.ActionListener
) : GraphNode(subgraph) {

    init {
        this.styleClass.add("subgraph")


        val nameLabel = Label("${subgraph.name}<${subgraph.payloadClass}>")
        nameLabel.font = Font(16.0)

        this.children.add(nameLabel)
        subgraph.title?.let { this.children.add(Text(it)) }

        this.setOnMouseClicked { event ->
            if (event.clickCount == 2) {
                subgraph.source?.let(actionListener::goToSource)
            }
        }

        initializePopupMenu()
    }

    fun initializePopupMenu() {
        val contextMenu = buildTooltipContent()

        this.setOnContextMenuRequested { contextMenuEvent ->
            contextMenu.show(this, contextMenuEvent.screenX, contextMenuEvent.screenY);
            contextMenuEvent.consume()
        }
    }

    fun buildTooltipContent(): ContextMenu {

        val contextMenu = ContextMenu()

        val processorContent = VBox()

        contextMenu.items.add(MenuItem().apply {
            graphic = processorContent
            setOnAction { event -> subgraph.source?.let { actionListener.goToSource(it) } }
        })

        processorContent.children.apply {
            add(Text(subgraph.name))
            add(Text(subgraph.payloadClass))
            subgraph.title?.let { add(Text(it)) }
            subgraph.doc?.let { add(Text(it)) }
        }

        return contextMenu
    }

}