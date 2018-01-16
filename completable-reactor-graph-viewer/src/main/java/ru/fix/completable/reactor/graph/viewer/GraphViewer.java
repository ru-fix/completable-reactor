package ru.fix.completable.reactor.graph.viewer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Scene;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.graph.viewer.model.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Kamil Asfandiyarov.
 */
public class GraphViewer {
    ObjectMapper marshaller = new ObjectMapper();
    {
        marshaller.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    Scene scene;
    CopyOnWriteArrayList<ActionListener> actionListeners = new CopyOnWriteArrayList<>();
    GraphViewPane graphViewPane;

    public GraphViewer() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void goToSource(ReactorGraphModel.Source source) {
                for (val listener : actionListeners) {
                    listener.goToSource(source);
                }
            }

            @Override
            public void goToSubgraph(String subgraphPayloadClass) {
                for(val listener: actionListeners){
                    listener.goToSubgraph(subgraphPayloadClass);
                }
            }

            @Override
            public void coordinatesChanged(List<CoordinateItem> coordinateItems) {
                for (val listener : actionListeners) {
                    listener.coordinatesChanged(coordinateItems);
                }
            }
        };

        graphViewPane = new GraphViewPane(actionListener, this::getShortcut);

        graphViewPane.setPrefSize(700.0, 600.0);

        scene = new Scene(graphViewPane);

        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        //Shortcuts
        graphViewPane.setOnKeyReleased(keyEvent -> {
            shortcuts.forEach((shortcut, shortcutType) -> {
                if(shortcut.getPredicate().test(keyEvent)){
                    switch (shortcutType){
                        case GOTO_SERIALIZATION_POINT:
                            actionListener.goToSource(graphViewPane.getGraph().serializationPointSource);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }

            });
        });
    }

    public void openGraph(ReactorGraphModel graph) {
        fixCoordinates(graph);
        graphViewPane.setGraph(graph);
    }

    int DEFAULT_POSITION = 100;
    /**
     * исправляет координаты у графа, если она не заполненны
     * @param graph
     */
    public void fixCoordinates(ReactorGraphModel graph) {
        System.out.println("graph " + graph.startPoint.coordinates.x + ", " + graph.startPoint.coordinates.y);
//        graph.startPoint.coordinates.x;
        TreeNode treeNode = new TreeNode(graph.startPoint);
        for (val identity : graph.startPoint.processingItems) {
            recursiveBuldTree(graph, treeNode, identity);
        }
        recursiveFixCoordinates(treeNode,
                graph.startPoint.coordinates.x, graph.startPoint.coordinates.y, 200, 100);
        System.out.println("tree " + treeNode);
    }

    private void recursiveFixCoordinates(TreeNode parentNode, int parentX, int parentY,  int deltaX,  int deltaY) {
        int index = -1*(parentNode.childs().size()/2);
        for (val object : parentNode.childs()) {
            TreeNode node = (TreeNode)object;
            if ((node.getData() instanceof ReactorGraphModel.Processor)
                && (((ReactorGraphModel.Processor) node.getData()).coordinates.x == DEFAULT_POSITION)
                    && (((ReactorGraphModel.Processor) node.getData()).coordinates.y == DEFAULT_POSITION)) {
                ((ReactorGraphModel.Processor) node.getData()).coordinates.setX(deltaX * index++ + parentX);
                ((ReactorGraphModel.Processor) node.getData()).coordinates.setY(deltaY + parentY);
                recursiveFixCoordinates(node, ((ReactorGraphModel.Processor) node.getData()).coordinates.getX(),
                        ((ReactorGraphModel.Processor) node.getData()).coordinates.getY(), deltaX, deltaY);
            }
        }
    }

    /**
     * рекурсивно строим дерево
     * @param graph
     * @param parentNode
     * @param identity
     */
    private void recursiveBuldTree(ReactorGraphModel graph, TreeNode parentNode, ReactorGraphModel.Identity identity) {
        ReactorGraphModel.Processor processor = findProcessor(graph.processors, identity);
        if (processor != null) {
            val node = parentNode.addChild(processor);
            for (val proc : findChildProcessorByMerge(graph, processor.getIdentity())) {
                recursiveBuldTree(graph, node, proc.getIdentity());
            }
        }
    }

    /**
     * find merge points for Processor
     * @param graph
     * @param identity
     * @return
     */
    public List<ReactorGraphModel.Processor> findChildProcessorByMerge(ReactorGraphModel graph, ReactorGraphModel.Identity identity) {
        val result = new ArrayList<ReactorGraphModel.Processor>();
        for (ReactorGraphModel.MergePoint mergePoint : graph.mergePoints) {
            if (mergePoint.identity.equals(identity)) {
                for (ReactorGraphModel.Transition transition : mergePoint.getTransitions()) {
                    if (transition.handleByProcessingItem != null) {
                        ReactorGraphModel.Processor processor = findProcessor(graph.processors, transition.handleByProcessingItem);
                        if (processor != null) {
                            result.add(processor);
                        }
                    }
                }
            }
        }
        return result;
    }

    public ReactorGraphModel.Processor findProcessor(List<ReactorGraphModel.Processor> processors, ReactorGraphModel.Identity identity) {
        if (processors != null) {
            for (val processor : processors) {
                if (processor.getIdentity().equals(identity)) {
                    return processor;
                }
            }
        }
        return null;
    }

    public void openGraph(String graph) throws Exception {
        val graphModel = marshaller.readValue(graph, ReactorGraphModel.class);
        openGraph(graphModel);
    }

    public ReactorGraphModel getGraphModel(){
        return graphViewPane.getGraph();
    }

    public GraphViewer registerListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
        return this;
    }

    Map<Shortcut, ShortcutType> shortcuts = new ConcurrentHashMap<>();

    public void setShortcut(ShortcutType shortcutType, Shortcut shortcut) {
        shortcuts.put(shortcut, shortcutType);
    }

    public Optional<Shortcut> getShortcut(ShortcutType shortcutType){
        return shortcuts.entrySet().stream()
                .filter(entry -> entry.getValue() == shortcutType)
                .map(entry -> entry.getKey())
                .findFirst();
    }

    @Data
    @AllArgsConstructor
    public static class CoordinateItem {
        public enum Type {
            PROCESSOR,
            MERGE_POINT,
            START_POINT,
            END_POINT
        }

        final Type type;
        final ReactorGraphModel.Identity identity;
        volatile int x;
        volatile int y;
    }

    public interface ActionListener {

        /**
         * Viewer asks IDE to navigate to source code
         * @param source source code location
         */
        void goToSource(ReactorGraphModel.Source source);

        /**
         * Viewer asks IDE to navigate to subgraph view
         */
        void goToSubgraph(String subgraphPayloadClass);

        /**
         * Graph nodes coordinates changed
         * @param coordinateItems new coordinates
         */
        void coordinatesChanged(List<CoordinateItem> coordinateItems);
    }

    public Scene getScene() {
        return scene;
    }
}