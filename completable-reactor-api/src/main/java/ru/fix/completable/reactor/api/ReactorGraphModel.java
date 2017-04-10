package ru.fix.completable.reactor.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON representation of reactor graph.
 * @author Kamil Asfandiyarov
 */
@Data
@Accessors(chain = true)
public class ReactorGraphModel {

    @Data
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Coordinates{
        public int x;
        public int y;
    }

    @Data
    @Accessors(chain = true)
    public static class Payload {
        public String payloadName;
        public String payloadClass;
        public String[] payloadDoc;
    }

    @Data
    @Accessors(chain = true)
    public static class StartPoint {
        public Coordinates coordinates;
        public Source coordinatesSource;

        /**
         * Where {@link ReactorGraphBuilder#payload(Class)} method was invoked during graph construction
         */
        ReactorGraphModel.Source builderPayloadSource;

        public final List<Identity> processingItems = new ArrayList<>();
    }

    @Data
    @Accessors(chain = true)
    public static class MergeGroup {
        public final List<MergePoint> mergePoints = new ArrayList<>();
    }

    @Data
    @Accessors(chain = true)
    public static class MergePoint {
        public Identity processor;
        public Identity subgraph;
        public Identity mergePoint;

        public Coordinates coordinates;
        public Source coordinatesSource;
        public Source mergeSource;
        public String mergerTitle;
        public String[] mergerDocs;
        public final List<Transition> transitions = new ArrayList<>();
    }

    @Data
    @Accessors(chain = true)
    public static class TransitionDocumentation{
        public String mergeStatus;
        public String[] docs;
    }

    @Data
    @Accessors(chain = true)
    public static class Transition {
        public List<String> mergeStatuses;
        public boolean isOnAny;
        public boolean isComplete;
        public Identity mergeProcessingItem;
        public Identity handleByProcessingItem;
        public Coordinates completeCoordinates;
        public Source completeCoordinatesSource;
        public Source completeSource;
        public final List<TransitionDocumentation> transitionsDoc = new ArrayList<>();

        public ReactorGraphModel.Source transitionOnAnySource;
        public Map<String, ReactorGraphModel.Source> transitionOnStatusSource;
        public Map<String, ReactorGraphModel.Source> mergeStatusSources;
    }

    @Data
    @Accessors(chain = true)
    public static class Source {
        public String className;
        public String fileName;
        public Integer fileNameLine;
    }

    @Data
    @Accessors(chain = true)
    public static class Identity implements Comparable<Identity>{
        /**
         * Null for detached MergePoint
         * Not null for Processors and Subgraphs
         */
        String className;
        int id;

        @Override
        public int compareTo(Identity other) {
            val item1 = this;
            val item2 = other;

            if(item1.getClassName() == null && item2.getClassName() == null){
                return Integer.compare(item1.getId(), item2.getId());
            }
            if(item1.getClassName() == null){
                return 1;
            }

            if(item2.getClassName() == null){
                return -1;
            }

            int cmp = item1.getClassName().compareTo(item2.getClassName());
            if(cmp != 0){
                return cmp;
            }

            return Integer.compare(item1.getId(), item2.getId());
        }
    }


    @Data
    @Accessors(chain = true)
    public static class Processor{
        Identity identity;

        public Coordinates coordinates;
        public Source coordinatesSource;

        public String[] processorDoc;
        public String handlerTitle;
        public String[] handlerDoc;

        public Source withHandlerSource;
    }

    @Data
    @Accessors(chain = true)
    public static class Subgraph{
        Identity identity;

        public Coordinates coordinates;
        public Source coordinatesSource;
        public Source buildSource;

        public String subgraphTitle;
        public String[] subgraphDoc;
    }

    public Payload payload;
    public StartPoint startPoint;

    public final List<Processor> processors = new ArrayList<>();
    public final List<Subgraph> subgraphs = new ArrayList<>();

    public final List<MergeGroup> mergeGroups = new ArrayList<>();

    public Source serializationPointSource;
    public Source coordinatesSource;
    public Source buildGraphSource;
}
