package ru.fix.completable.reactor.graph.viewer;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.experimental.var;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kamil Asfandiyarov.
 */
class MergeGroupNode extends Pane {

    final CoordinateTranslator coordinateTranslator;
    final ReactorGraphModel.MergeGroup mergeGroup;
    final GraphViewer.ActionListener actionListener;
    final List<Node> groupedNodes;
    final Optional<StartPointNode> startPointNode;

    static final double padding = 5;


    public MergeGroupNode(CoordinateTranslator translator,
                          ReactorGraphModel.MergeGroup mergeGroup,
                          Optional<StartPointNode> startPointNode,
                          List<MergePointNode> mergePointNodes,
                          GraphViewer.ActionListener actionListener) {
        this.coordinateTranslator = translator;
        this.mergeGroup = mergeGroup;
        this.actionListener = actionListener;
        this.startPointNode = startPointNode;

        this.getStyleClass().add("mergeGroup");

        this.groupedNodes = new ArrayList<>(mergePointNodes);
        startPointNode.ifPresent(groupedNodes::add);

        for (var node : groupedNodes) {
            node.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> updateLayout());
            node.layoutXProperty().addListener((observable, oldValue, newValue) -> updateLayout());
            node.layoutYProperty().addListener((observable, oldValue, newValue) -> updateLayout());
        }

        updateLayout();
    }

    void updateLayout(){
        double minx = Double.MAX_VALUE;
        double miny = Double.MAX_VALUE;
        double maxx = 0;
        double maxy = 0;

        for (var node : groupedNodes) {
            minx  = Math.min(minx, node.getLayoutX() - padding);
            miny  = Math.min(miny, node.getLayoutY() - padding);
            maxx  = Math.max(maxx, node.getLayoutX() + node.getLayoutBounds().getWidth() + padding);
            maxy  = Math.max(maxy, node.getLayoutY() + node.getLayoutBounds().getHeight() + padding);
        }

        this.setLayoutX(minx);
        this.setLayoutY(miny);

        double width = maxx - minx;
        this.setPrefWidth(width);
        this.setMaxWidth(width);
        this.setMinWidth(width);

        double height = maxy - miny;
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        this.setMinHeight(height);

        this.resize(width, height);

    }
}