package ru.fix.completable.reactor.graph.viewer.code;

import ru.fix.completable.reactor.graph.viewer.GraphViewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kamil Asfandiyarov
 */
public class CodeUpdater {
    private static final String COORDINATES = ".coordinates()";
    private static final String BUILD_GRAPH = ".buildGraph()";

    private static final Map<GraphViewer.CoordinateItem.Type, Integer> typeOrder;

    static {
        typeOrder = new HashMap<>();
        typeOrder.put(GraphViewer.CoordinateItem.Type.START_POINT, 0);
        typeOrder.put(GraphViewer.CoordinateItem.Type.PROCESSOR, 1);
        typeOrder.put(GraphViewer.CoordinateItem.Type.MERGE_POINT, 2);
        typeOrder.put(GraphViewer.CoordinateItem.Type.END_POINT, 3);

    }

    /**
     * @param codeBlock block of code that contains .coordinates() and .buildGraph() methods
     * @return
     */
    public String updateCoordinates(String codeBlock, List<GraphViewer.CoordinateItem> coordinates) {

        StringBuilder out = new StringBuilder();

        int start = codeBlock.indexOf(COORDINATES);
        if (start < 0) {
            return codeBlock;
        }

        final StringBuilder padding = new StringBuilder();
        for (int i = 0; i < start; i++) {
            padding.append(' ');
        }

        start += COORDINATES.length();

        int end = codeBlock.indexOf(BUILD_GRAPH);
        if (end < 0) {
            return codeBlock;
        }

        out.append(codeBlock.substring(0, start + 1));

        coordinates.stream()
                .sorted((item1, item2) -> {

                    //compare Type
                    if (item1.getType().equals(item2.getType())) {

                        //type are equals, compare processor
                        int cmpProc;

                        if (item1.getIdentity() != null && item2.getIdentity() != null) {
                            return item1.getIdentity().compareTo(item2.getIdentity());
                        } else {
                            cmpProc = 0;
                        }

                        if (cmpProc == 0) {
                            //processors are equals
                            int cmpx = Integer.compare(item1.getX(), item2.getX());
                            if (cmpx == 0) {
                                return Integer.compare(item1.getY(), item2.getY());
                            } else {
                                return cmpx;
                            }
                        } else {
                            return cmpProc;
                        }

                    } else {
                        return Integer.compare(typeOrder.get(item1.getType()), typeOrder.get(item2.getType()));
                    }
                })
                .forEach(item -> {
                    out.append(padding.toString());
                    out.append(serialize(item));
                    out.append("\n");
                });

        out.append("\n");
        out.append(padding.toString());
        out.append(codeBlock.substring(end, codeBlock.length()));

        return out.toString();
    }



    private String serialize(GraphViewer.CoordinateItem item) {
        switch (item.getType()) {
            case START_POINT:
                return String.format(".start(%d, %d)", item.getX(), item.getY());
            case PROCESSOR:
                return String.format(".proc(%s.class, %d, %d, %d)",
                        item.getIdentity().getClassName(),
                        item.getIdentity().getId(),
                        item.getX(),
                        item.getY());
            case MERGE_POINT:
                if(item.getIdentity().getClassName() == null){
                    return String.format(".merge(%d, %d, %d)",
                            item.getIdentity().getId(),
                            item.getX(),
                            item.getY());
                } else {
                    return String.format(".merge(%s.class, %d, %d, %d)",
                            item.getIdentity().getClassName(),
                            item.getIdentity().getId(),
                            item.getX(),
                            item.getY());
                }
            case END_POINT:
                return String.format(".complete(%s.class, %d, %d, %d)",
                        item.getIdentity().getClassName(),
                        item.getIdentity().getId(),
                        item.getX(),
                        item.getY());
            default:
                throw new IllegalArgumentException(String.format("Unsupported type: %s", item));
        }
    }
}
