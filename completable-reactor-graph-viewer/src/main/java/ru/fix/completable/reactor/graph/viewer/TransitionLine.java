package ru.fix.completable.reactor.graph.viewer;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import lombok.experimental.var;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kamil Asfandiyarov
 */
public class TransitionLine extends Group {

    final Node world;
    final Node fromNode;
    final Node toNode;
    final Optional<BorderableNode> toNodeBorderable;

    final Optional<ReactorGraphModel.MergePointTransition> transition;
    private final Polygon pointer;

    private final NodeCenter fromCenter;
    private final NodeCenter toCenter;
    private final Line line;
    private final VBox labelsPane = new VBox();
    private final Polygon menuArea;
    private final GraphViewer.ActionListener actionListener;


    public TransitionLine(Node world,
                          Node fromNode,
                          Node toNode,
                          Optional<ReactorGraphModel.MergePointTransition> transition,
                          GraphViewer.ActionListener actionListener) {
        this.world = world;
        this.fromNode = fromNode;
        this.toNode = toNode;
        toNodeBorderable = toNode instanceof BorderableNode ? Optional.of((BorderableNode) toNode) : Optional.empty();

        this.transition = transition;
        this.actionListener = actionListener;

        this.getStyleClass().add("transitionLine");

        line = new Line();
        line.getStyleClass().add("pointerLine");

        fromCenter = new NodeCenter(world, fromNode);
        toCenter = new NodeCenter(world, toNode);

        fromCenter.getCenterX().addListener((observable, oldValue, newValue) -> updateLine());
        fromCenter.getCenterY().addListener((observable, oldValue, newValue) -> updateLine());
        toCenter.getCenterX().addListener((observable, oldValue, newValue) -> updateLine());
        toCenter.getCenterY().addListener((observable, oldValue, newValue) -> updateLine());

        pointer = new Polygon();
        pointer.getPoints().addAll(
                0.0, 0.0,
                5.0, -15.0,
                -5.0, -15.0
        );
        pointer.getStyleClass().add("pointer");
        this.getChildren().add(pointer);

        this.getChildren().add(line);

        menuArea = new Polygon();
        menuArea.setOpacity(0);
        this.getChildren().add(menuArea);


        transition.ifPresent(transitionItem -> {

            if (transitionItem.isOnAny) {
                Label anyStatusLabel = new Label("<Any>");
                labelsPane.getChildren().add(anyStatusLabel);

                if (transitionItem.transitionOnAnySource != null) {
                    anyStatusLabel.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) {
                            actionListener.goToSource(transitionItem.transitionOnAnySource);
                        }
                    });
                    menuArea.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) {
                            actionListener.goToSource(transitionItem.transitionOnAnySource);
                        }
                    });
                }

            } else if (transitionItem.mergeStatuses != null) {

                for (String status : transitionItem.mergeStatuses) {
                    Label statusLabel = new Label(status);
                    labelsPane.getChildren().add(statusLabel);

                    if (transitionItem.transitionOnStatusSource != null && transitionItem.transitionOnStatusSource.get(status) != null) {
                        statusLabel.setOnMouseClicked(mouseEvent -> {
                            if (mouseEvent.getClickCount() == 2) {
                                ReactorGraphModel.Source source = transitionItem.transitionOnStatusSource.get(status);
                                if (source != null) {
                                    actionListener.goToSource(source);
                                }
                            }
                        });
                    }
                }

                Optional.ofNullable(transitionItem.transitionOnStatusSource)
                        .flatMap(map -> map.values().stream().filter(Objects::nonNull).findAny())
                        .ifPresent(source -> menuArea.setOnMouseClicked(mouseEvent -> {
                            if (mouseEvent.getClickCount() == 2) {
                                actionListener.goToSource(source);
                            }
                        }));
            }


            line.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
                val x = (newValue.getMinX() + newValue.getMaxX()) / 2;
                val y = (newValue.getMinY() + newValue.getMaxY()) / 2;

                labelsPane.setLayoutX(x);
                labelsPane.setLayoutY(y);
            });

            this.getChildren().add(labelsPane);
        });

        initializePopupMenu();
    }

    void updateLine() {
        //Arrow
        val from = new Point2D(
                fromCenter.getCenterX().doubleValue(),
                fromCenter.getCenterY().doubleValue());
        val to = new Point2D(
                toCenter.getCenterX().doubleValue(),
                toCenter.getCenterY().doubleValue());

        double minx;
        double miny;
        double maxx;
        double maxy;

        if(toNodeBorderable.isPresent()){
            val node = toNodeBorderable.get();
            minx = node.getBorderableX();
            miny = node.getBorderableY();
            maxx = node.getBorderableX() + node.getBorderableWidth();
            maxy = node.getBorderableY() + node.getBorderableHeight();

        } else {
            minx = toNode.getLayoutX();
            miny = toNode.getLayoutY();
            maxx = toNode.getLayoutX() + toNode.getLayoutBounds().getWidth();
            maxy = toNode.getLayoutY() + toNode.getLayoutBounds().getHeight();
        }

        val toNodeBorders = new Point2D[][]{
                {new Point2D(minx, miny), new Point2D(maxx, miny)},
                {new Point2D(minx, miny), new Point2D(minx, maxy)},
                {new Point2D(maxx, maxy), new Point2D(maxx, miny)},
                {new Point2D(maxx, maxy), new Point2D(minx, maxy)}
        };

        Point2D toNodeBorderIntersection = new Point2D(toCenter.getCenterX().get(), toCenter.getCenterY().get());

        for (int i = 0; i < 4; i++) {
            val intersectionPoint = intersection2(
                    from,
                    to,
                    toNodeBorders[i][0],
                    toNodeBorders[i][1]);
            if (intersectionPoint.isPresent()) {
                toNodeBorderIntersection = intersectionPoint.get();
                break;
            }
        }

        val x0 = toNodeBorderIntersection.getX();
        val y0 = toNodeBorderIntersection.getY();


        val tan = (to.getX() - from.getX()) / (to.getY() - from.getY());
        val angleRad = Math.atan(tan);
        val angleDeg = Math.toDegrees(angleRad);

        var rotationDeg = (360 - angleDeg) % 360;

        if (to.getY() - from.getY() < 0) {
            rotationDeg = (rotationDeg + 180) % 360;
        }

        pointer.getTransforms().clear();
        pointer.getTransforms().add(new Rotate(rotationDeg, 0, 0));

        pointer.setLayoutX(x0);
        pointer.setLayoutY(y0);


        //Line
        line.setStartX(fromCenter.getCenterX().get());
        line.setStartY(fromCenter.getCenterY().get());
        line.setEndX(toCenter.getCenterX().get());
        line.setEndY(toCenter.getCenterY().get());

        //menu area
        menuArea.getPoints().clear();
        val menuAreaWidth = 30.0;

        val menu_dx = menuAreaWidth / 2 * Math.cos(angleRad);
        val menu_dy = menuAreaWidth / 2 * Math.sin(angleRad);

        menuArea.getPoints().addAll(
                line.getStartX() + menu_dx, line.getStartY() - menu_dy,
                line.getStartX() - menu_dx, line.getStartY() + menu_dy,
                line.getEndX() - menu_dx, line.getEndY() + menu_dy,
                line.getEndX() + menu_dx, line.getEndY() - menu_dy
        );
    }


    Optional<Point2D> intersection2(Point2D firstStart, Point2D firstEnd, Point2D secondStart, Point2D secondEnd) {
        double p0_x = firstStart.getX();
        double p0_y = firstStart.getY();
        double p1_x = firstEnd.getX();
        double p1_y = firstEnd.getY();
        double p2_x = secondStart.getX();
        double p2_y = secondStart.getY();
        double p3_x = secondEnd.getX();
        double p3_y = secondEnd.getY();

        double s02_x, s02_y, s10_x, s10_y, s32_x, s32_y, s_numer, t_numer, denom, t;
        s10_x = p1_x - p0_x;
        s10_y = p1_y - p0_y;
        s32_x = p3_x - p2_x;
        s32_y = p3_y - p2_y;

        denom = s10_x * s32_y - s32_x * s10_y;

        if (Double.doubleToRawLongBits(denom) == 0) {
            return Optional.empty(); // Collinear
        }

        boolean denomPositive = denom > 0;

        s02_x = p0_x - p2_x;
        s02_y = p0_y - p2_y;
        s_numer = s10_x * s02_y - s10_y * s02_x;
        if ((s_numer < 0) == denomPositive) {
            return Optional.empty(); // No collision
        }

        t_numer = s32_x * s02_y - s32_y * s02_x;
        if ((t_numer < 0) == denomPositive) {
            return Optional.empty(); // No collision
        }

        if (((s_numer > denom) == denomPositive) || ((t_numer > denom) == denomPositive)) {
            return Optional.empty(); // No collision
        }

        // Collision detected
        Double i_x;
        Double i_y;

        t = t_numer / denom;
        i_x = p0_x + (t * s10_x);

        i_y = p0_y + (t * s10_y);

        return Optional.of(new Point2D(i_x, i_y));
    }

    void initializePopupMenu() {

        val contextMenu = buildTooltipContent();

        EventHandler<ContextMenuEvent> contextMenuHandler = contextMenuEvent -> {
            contextMenu.show(line, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            contextMenuEvent.consume();
        };

        labelsPane.setOnContextMenuRequested(contextMenuHandler);
        pointer.setOnContextMenuRequested(contextMenuHandler);
        line.setOnContextMenuRequested(contextMenuHandler);
        menuArea.setOnContextMenuRequested(contextMenuHandler);
    }

    ContextMenu buildTooltipContent() {
        //documentation
        Map<String, String[]> mergeStatusDocumentation = new HashMap<>();

        this.transition.map(transitionItem -> transitionItem.mergeStatuses)
                .ifPresent(statuses -> statuses.forEach(status -> mergeStatusDocumentation.put(status, new String[]{})));

        this.transition.map(transitionItem -> transitionItem.transitionsDoc)
                .ifPresent(docs -> docs.forEach(doc -> {
                            mergeStatusDocumentation.put(doc.mergeStatus, doc.docs);
                        }
                ));


        //menu controls

        ContextMenu contextMenu = new ContextMenu();

        for (String status : mergeStatusDocumentation.keySet()) {

            VBox content = new VBox();

            MenuItem documentationMenuItem = new MenuItem();
            documentationMenuItem.setGraphic(content);
            contextMenu.getItems().add(documentationMenuItem);

            this.transition
                    .map(transitionItem -> transitionItem.transitionOnStatusSource)
                    .map(onStatusSources -> onStatusSources.get(status))
                    .ifPresent(source -> documentationMenuItem.setOnAction(event -> actionListener.goToSource(source)));

            //menu item content
            content.getChildren().add(new Text(status));
            Optional.ofNullable(mergeStatusDocumentation.get(status))
                    .map(statusDocs -> new Text(Arrays.stream(statusDocs).collect(Collectors.joining("\n"))))
                    .ifPresent(content.getChildren()::add);
        }

        if (this.transition.isPresent()) {
            ReactorGraphModel.MergePointTransition transitionItem = this.transition.get();
            if (transitionItem.isOnAny) {
                MenuItem onAnyMenuItem = new MenuItem();

                VBox content = new VBox();
                onAnyMenuItem.setGraphic(content);
                content.getChildren().add(new Text("<Any>"));
                content.getChildren().add(new Text("Transition activated by any status."));
                contextMenu.getItems().add(onAnyMenuItem);

                ReactorGraphModel.Source source = transitionItem.transitionOnAnySource;
                if (source != null) {
                    onAnyMenuItem.setOnAction(event -> {
                        actionListener.goToSource(source);
                    });
                }
            }
        }

        return contextMenu;
    }

}
