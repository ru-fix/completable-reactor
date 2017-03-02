package ru.fix.completable.reactor.ui;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Kamil Asfandiyarov
 */
public class UIBuilder {
    private static final Logger log = LoggerFactory.getLogger(UIBuilder.class);

    static String htmlPage;


    static String load(String resource){
        try (InputStream stream = UIBuilder.class.getResourceAsStream(resource)) {
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (Exception exc) {
            log.error(String.format("Failed to load resource: %s", resource), exc);
            return "";
        }
    }


    static {
        String index_html = load("index.html");
        String index_js = load("js/index.js");
        String index_css = load("css/index.css");

        index_html = index_html.replace("<link rel=\"stylesheet\" type=\"text/css\" href=\"./css/index.css\">", "<style>"+index_css+"</style>");
        index_html = index_html.replace("<script src='./js/index.js'></script>", "<script>" + index_js + "</script>");

        htmlPage = index_html;
    }

    public static String buildHtmlPage(String modelJson) {
        return htmlPage.replace("<script src='./data/index.js'></script>", "<script>var _data = " + modelJson + "</script>");
    }
}
