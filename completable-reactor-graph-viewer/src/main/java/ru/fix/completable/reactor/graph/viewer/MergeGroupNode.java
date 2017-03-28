package ru.fix.completable.reactor.graph.viewer;

import javafx.scene.layout.Pane;
import lombok.experimental.var;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.List;

/**
 * Created by swarmshine on 29.01.2017.
 */
class MergeGroupNode extends Pane {

    final CoordinateTranslator coordinateTranslator;
    final ReactorGraphModel.MergeGroup mergeGroup;
    final GraphViewer.ActionListener actionListener;
    final List<MergePointNode> mergePointNodes;

    static final double padding = 5;


    public MergeGroupNode(CoordinateTranslator translator,
                          ReactorGraphModel.MergeGroup mergeGroup,
                          List<MergePointNode> mergePointNodes,
                          GraphViewer.ActionListener actionListener) {
        this.coordinateTranslator = translator;
        this.mergeGroup = mergeGroup;
        this.actionListener = actionListener;
        this.mergePointNodes = mergePointNodes;

        this.getStyleClass().add("mergeGroup");

        for (var mergePointNode : mergePointNodes) {
            mergePointNode.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> updateLayout());
            mergePointNode.layoutXProperty().addListener((observable, oldValue, newValue) -> updateLayout());
            mergePointNode.layoutYProperty().addListener((observable, oldValue, newValue) -> updateLayout());
        }

        updateLayout();
    }

    void updateLayout(){
        double minx = Double.MAX_VALUE;
        double miny = Double.MAX_VALUE;
        double maxx = 0;
        double maxy = 0;

        for (var mergePointNode : mergePointNodes) {
            minx  = Math.min(minx, mergePointNode.getLayoutX() - padding);
            miny  = Math.min(miny, mergePointNode.getLayoutY() - padding);
            maxx  = Math.max(maxx, mergePointNode.getLayoutX() + mergePointNode.getLayoutBounds().getWidth() + padding);
            maxy  = Math.max(maxy, mergePointNode.getLayoutY() + mergePointNode.getLayoutBounds().getHeight() + padding);
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