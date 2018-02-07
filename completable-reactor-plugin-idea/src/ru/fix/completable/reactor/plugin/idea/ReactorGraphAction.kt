package ru.fix.completable.reactor.plugin.idea

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.vfs.LocalFileSystem
import ru.fix.completable.reactor.graph.viewer.gl.GraphViewer
import ru.fix.completable.reactor.model.GraphModel
import ru.fix.completable.reactor.model.Source

/**
 * @author Kamil Asfandiyarov
 */
class ReactorGraphAction : AnAction() {
    val notificator = Notificator()

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) ?: return
        val editor = event.getData(CommonDataKeys.EDITOR) ?: return
        val virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        //TODO: release links when poopup closes

        val viewer = Viewer(
                editor.document,
                virtualFile,
                project,
                editor
        )

        viewer.graphViewer.registerListener(object : GraphViewer.ActionListener {
            override fun coordinatesChanged(graphModel: GraphModel) {
//                TODO coordinatesChanged
            }

            override fun goToSource(source: Source) {
                ApplicationManager.getApplication().invokeLater {
                    ApplicationManager.getApplication().runReadAction {

                        source.filePath
                                ?.let { LocalFileSystem.getInstance().findFileByPath(it) }
                                ?.let {
                                    val descriptor = OpenFileDescriptor(
                                            project,
                                            it,
                                            source.line?.let { it - 1 } ?: 0,
                                            source.lineOffset ?: 0)

                                    viewer.close()

                                    descriptor.navigate(true)

                                }
                                ?: notificator.error(
                                        "Can not open source location. File not found. " +
                                                "Source: $source, path: ${source.filePath}")
                    }
                }
            }

            override fun goToSubgraph(subgraphPayloadType: String) {
                //TODO goToSubgraph
                notificator.info("" +
                        "Go to subgraph not supported yet." +
                        " Ignore opening subgraph action for $subgraphPayloadType.")
            }
        })
    }
}
