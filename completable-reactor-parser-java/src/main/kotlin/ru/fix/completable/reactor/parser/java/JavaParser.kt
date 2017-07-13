package ru.fix.completable.reactor.parser.java

import ru.fix.completable.reactor.api.ReactorGraphModel
import java.util.regex.Pattern

/**
 * @author Kamil Asfandiyarov
 */
class JavaParser(val symbolResolver: SymbolResolver) {

    val importPattern = Pattern.compile(
            "import\\s+([\\w\\.]*)\\s*;", Pattern.DOTALL)

    val graphPattern = "\\w+".toRegex(RegexOption.DOT_MATCHES_ALL)


    val payloadPattern = Pattern.compile(
            "\\w+\\s*\\.\\s*payload\\s*\\(\\s*(\\w*)\\.class\\s*\\).+?(?=buildGraph)", Pattern.DOTALL)

    val descriptionPattern = Pattern.compile(
            "\\w+\\s*=\\s*\\w+\\s*\\.\\s*processor\\s*\\(\\s*\\)\\s*\\.\\s*forPayload\\s*\\(\\s*[\\w\\.]+\\s*\\)(\\s*\\.\\s*passArg\\s*\\()"
    )

    val imports = ArrayList<String>()

//    val context = Context(null)


    fun parse(javaCode: String): List<ReactorGraphModel> {

        val result = ArrayList<ReactorGraphModel>()


        val scanner = JavaScanner(javaCode)

        while(scanner.findPayload()){
            scanner.parse()
        }




        // parse imports
        var matcher = importPattern.matcher(javaCode)
        while (matcher.find()) {
            imports.add(matcher.group(1))
        }

        // parse payload graphs
        matcher = payloadPattern.matcher(javaCode)
        while (matcher.find()) {
            val paylaod = matcher.group(1)
            val model = ReactorGraphModel()
            model.payload = ReactorGraphModel.Payload()
            model.payload.payloadClass = resolveClassByShortName(paylaod)

            result.add(model)
        }

        // parse
//        Context c;


        return result
    }

    fun resolveClassByShortName(className: String): String {
        val fullClassName = imports.find { it.endsWith("." + className) }
        if (fullClassName != null) {
            return fullClassName
        } else {
            return symbolResolver.resolveFullClassNameByShortName(className)
        }
    }
}
