package ru.fix.completable.reactor.graph.viewer.code;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.fix.completable.reactor.api.ReactorGraphModel;
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
        coordinateItems.add(new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.START_POINT,
                null,
                500,
                -200));

        coordinateItems.add(new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.PROCESSOR,
                new ReactorGraphModel.Identity()
                        .setClassName("SmsRequestConstructProcessor")
                        .setId(4),
                910,
                901));

        coordinateItems.add(new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.PROCESSOR,
                new ReactorGraphModel.Identity()
                        .setClassName("SmsRequestConstructProcessor")
                        .setId(5),
                1170,
                1270));

        coordinateItems.add(new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.PROCESSOR,
                new ReactorGraphModel.Identity()
                        .setClassName("ServiceFetchProcessor")
                        .setId(0),
                320,
                1141));

        coordinateItems.add(new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.MERGE_POINT,
                new ReactorGraphModel.Identity()
                        .setClassName("ServiceFetchProcessor")
                        .setId(5),
                1210,
                401));

        coordinateItems.add(new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.END_POINT,
                new ReactorGraphModel.Identity()
                        .setClassName("SmsRequestConstructProcessor")
                        .setId(6),

                1500,
                511));
    }

    @Test
    public void update_code_block() throws Exception {
        String input = IOUtils.toString(getClass().getResource("/code-block-1.txt").toURI(), StandardCharsets.UTF_8);
        String expectedOutput = IOUtils.toString(getClass().getResource("/code-block-1-result.txt").toURI(), StandardCharsets.UTF_8);

        String output = updater.updateCoordinates(input, coordinateItems);
        Assert.assertEquals(normalize(expectedOutput), normalize(output));
    }

    @Test
    public void code_after_code_Block_should_not_be_changed() throws Exception {
        String input = IOUtils.toString(getClass().getResource("/code-block-2.txt").toURI(), StandardCharsets.UTF_8);
        String expectedOutput = IOUtils.toString(getClass().getResource("/code-block-2-result.txt").toURI(), StandardCharsets.UTF_8);

        String output = updater.updateCoordinates(input, coordinateItems);
        Assert.assertEquals(normalize(expectedOutput), normalize(output));
    }

    static String normalize(String value){
        return value
                .replace("\r", "\n")
                .replace("\n\n", "\n")
                .replace("\n\n", "\n");
    }


    @Test
    public void update_existing_code_bloc_what_it_smaller_that_new_one() throws Exception {
        String input = IOUtils.toString(getClass().getResource("/code-block-3.txt").toURI(), StandardCharsets.UTF_8);
        String expectedOutput = IOUtils.toString(getClass().getResource("/code-block-3-result.txt").toURI(), StandardCharsets.UTF_8);

        String output = updater.updateCoordinates(input, coordinateItems);
        Assert.assertEquals(expectedOutput, output);
    }

}