package ru.fix.completable.reactor.parser.java

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths

class ProjectFileResolver {
    /**
     * @param path unix stile path
     */
    fun resolvePath(path: String) =
            ProjectFileResolver::class.java.getResource("").toURI().toURL().file.let {
                Paths.get(
                        it.substring(0, it.lastIndexOf("completable-reactor-parser")),
                        path.replace("/", FileSystems.getDefault().separator)
                )
            }

    fun read(path: String) =
            Files.newBufferedReader(
                    resolvePath(path)
            ).readText()

}