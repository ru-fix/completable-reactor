package ru.fix.completable.reactor.graph.viewer.code;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ru.fix.completable.reactor.graph.viewer.GraphViewer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Asfandiyarov
 */
public class CodeUpdaterTest {

    final CodeUpdater updater = new CodeUpdater();

    static final List<GraphViewer.CoordinateItem> coordinateItems = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        coordinateItems.add(new GraphViewer.CoordinateItem(GraphViewer.CoordinateItem.Type.START_POINT, null, 500, -200));
        coordinateItems.add(new GraphViewer.CoordinateItem(GraphViewer.CoordinateItem.Type.PROCESSOR, "SmsRequestConstructProcessor@4", 910, 901));
        coordinateItems.add(new GraphViewer.CoordinateItem(GraphViewer.CoordinateItem.Type.PROCESSOR, "SmsRequestConstructProcessor@5", 1170, 1270));
        coordinateItems.add(new GraphViewer.CoordinateItem(GraphViewer.CoordinateItem.Type.PROCESSOR, "ServiceFetchProcessor@0", 320, 1141));
        coordinateItems.add(new GraphViewer.CoordinateItem(GraphViewer.CoordinateItem.Type.MERGE_POINT, "ServiceFetchProcessor@5", 1210, 401));
        coordinateItems.add(new GraphViewer.CoordinateItem(GraphViewer.CoordinateItem.Type.END_POINT, "SmsRequestConstructProcessor@6", 1500, 511));
    }

    public static void main(String[] args) throws Exception {
        new CodeUpdaterTest().updateCoordinates();
    }

    @Test
    @Ignore
    public void updateCoordinates() throws Exception {
        String input = IOUtils.toString(getClass().getResource("/code-block-1.txt").toURI(), StandardCharsets.UTF_8);
        String expectedOutput = IOUtils.toString(getClass().getResource("/code-block-1-result.txt").toURI(), StandardCharsets.UTF_8);

        String output = updater.updateCoordinates(input, coordinateItems);
        Assert.assertEquals(expectedOutput, output);
    }

}