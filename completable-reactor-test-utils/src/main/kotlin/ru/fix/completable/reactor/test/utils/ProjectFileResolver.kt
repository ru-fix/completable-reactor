package ru.fix.completable.reactor.test.utils

import mu.KotlinLogging
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

private val log = KotlinLogging.logger {}

class ProjectFileResolver {

    companion object {
        val RESOLVER_PROJECT_FOLDER_SUB_PATH = FileSystems.getDefault().separator + "completable-reactor-"

    }

    /**
     * @param path unix style path
     */
    fun resolvePath(path: String): Path {


        val resolvedPath = ProjectFileResolver::class.java.getResource("/").toURI()
                .let { File(it).absolutePath }
                .let {
                    Paths.get(
                            it.substring(0, it.lastIndexOf(RESOLVER_PROJECT_FOLDER_SUB_PATH)),
                            path.replace("/", FileSystems.getDefault().separator)
                    )
                }

        log.info { "Path $path\n resolved to: $resolvedPath" }

        return resolvedPath
    }

    fun read(path: String) =
            Files.newBufferedReader(
                    resolvePath(path)
            ).readText()

}