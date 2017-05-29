package ru.fix.completable.reactor.graph.viewer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Scene;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;

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
                        case SHOW_HIDE_MERGE_GROUPS:
                            showMergeGroups(!isMergeGroupsShown());
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }

            });
        });
    }

    public void openGraph(ReactorGraphModel graph) {
        graphViewPane.setGraph(graph);
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

    public void showMergeGroups(boolean showMergeGroups){
        graphViewPane.showMergeGroups(showMergeGroups);
    }

    public boolean isMergeGroupsShown(){
        return graphViewPane.isMergeGroupShown();
    }
}