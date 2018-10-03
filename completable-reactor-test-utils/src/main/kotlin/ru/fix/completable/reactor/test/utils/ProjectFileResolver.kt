package ru.fix.completable.reactor.test.utils

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class ProjectFileResolver {
    /**
     * @param path unix style path
     */
    fun resolvePath(path: String): Path? {

        val ROOT_PROJECT_FOLDER_SUB_PATH = FileSystems.getDefault().separator + "completable-reactor" + FileSystems.getDefault().separator

        return ProjectFileResolver::class.java.getResource("").toURI().toURL().file.let {
            Paths.get(
                    it.substring(0, it.lastIndexOf(ROOT_PROJECT_FOLDER_SUB_PATH) + ROOT_PROJECT_FOLDER_SUB_PATH.length),
                    path.replace("/", FileSystems.getDefault().separator)
            )
        }
    }

    fun read(path: String) =
            Files.newBufferedReader(
                    resolvePath(path)
            ).readText()

}