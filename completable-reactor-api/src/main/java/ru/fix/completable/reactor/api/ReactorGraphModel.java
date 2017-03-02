package ru.fix.completable.reactor.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * JSON representation of reactor graph.
 * @author Kamil Asfandiyarov
 */
public class ReactorGraphModel {

    @Data
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Coordinates{

        public int x;
        public int y;
    }
    public static class Payload {

        public String payloadName;
        public String payloadClass;
        public String[] payloadDoc;
    }
    public static class StartPoint {

        public Coordinates coordinates;
        public Source coordinatesSource;
        public List<String> processors;
    }
    public static class MergeGroup {

        public List<MergePoint> mergePoints;
    }
    public static class MergePoint {

        public String processor;
        public Coordinates coordinates;
        public Source coordinatesSource;
        public Source mergeSource;
        public List<MergePointTransition> transitions;
    }
    public static class TransitionDocumentation{

        public String mergeStatus;
        public String[] docs;
    }
    public static class MergePointTransition {

        public List<String> mergeStatuses;
        public boolean isOnAny;
        public boolean isComplete;
        public String mergeProcessor;
        public String handleByProcessor;
        public Coordinates completeCoordinates;
        public Source completeCoordinatesSource;
        public List<TransitionDocumentation> transitionsDoc;
    }


    public enum ProcessorType{
        PLAIN,
        SUBGRAPH;
    }
    public static class Source {

        public String className;
        public String fileName;
        public Integer fileNameLine;
    }
    public static class ProcessorInfo{

        public ProcessorType processorType;
        public Coordinates coordinates;
        public Source coordinatesSource;
        public String[] processorDoc;
        public String[] requestDoc;
        public String handlerName;
        public Source handleBySource;
    }
    public Payload payload;

    public Map<String, ProcessorInfo> processors;
    public StartPoint startPoint;
    public List<MergeGroup> mergeGroups;
    public Source serializationPointSource;

    public Source coordinatesSource;
    public Source buildGraphSource;
}
