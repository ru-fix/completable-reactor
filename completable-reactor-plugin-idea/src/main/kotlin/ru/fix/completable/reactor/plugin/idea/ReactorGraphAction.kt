package ru.fix.completable.reactor.plugin.idea

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.LocalFileSystem
import ru.fix.completable.reactor.graph.viewer.code.CodeUpdater
import ru.fix.completable.reactor.graph.viewer.GraphViewer
import ru.fix.completable.reactor.model.CompileTimeGraph
import ru.fix.completable.reactor.model.Source

/**
 * @author Kamil Asfandiyarov
 */
class ReactorGraphAction : AnAction() {
    val notificator = Notificator()

    //TODO detect code type from source
    val codeUpdater = CodeUpdater(CodeUpdater.CodeType.JAVA)

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) ?: return
        val editor = event.getData(CommonDataKeys.EDITOR) ?: return
        val virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        val viewer = Viewer(
                editor.document,
                virtualFile,
                project,
                editor
        )

        viewer.graphViewer.registerListener(object : GraphViewer.ActionListener {
            override fun coordinatesChanged(graphModel: CompileTimeGraph) {

                ApplicationManager.getApplication().invokeLater {
                    ApplicationManager.getApplication().runReadAction {

                        codeUpdater.updateCoordinates(graphModel, object : CodeUpdater.Editor {
                            override fun getCodeBlock(coordinatesStart: Source, coordinatesEnd: Source): String {
                                val textRange = TextRange(coordinatesStart.offset, coordinatesEnd.offset)
                                return viewer.document.getText(textRange)
                            }

                            override fun replaceCodeBlock(start: Source, end: Source, newCodeBlock: String) {
                                WriteCommandAction.runWriteCommandAction(project) {
                                    viewer.document.replaceString(start.offset, end.offset, newCodeBlock)
                                }
                            }

                            override fun insert(position: Source, newCodeBlock: String) {
                                WriteCommandAction.runWriteCommandAction(project) {
                                    viewer.document.insertString(position.offset, newCodeBlock)
                                }
                            }
                        })

                    }
                }
            }

            override fun goToSource(source: Source) {
                ApplicationManager.getApplication().invokeLater {
                    ApplicationManager.getApplication().runReadAction {

                        source.filePath
                                .let { LocalFileSystem.getInstance().findFileByPath(it) }
                                ?.let {
                                    val descriptor = OpenFileDescriptor(
                                            project,
                                            it,
                                            source.line.let { it - 1 },
                                            source.column)

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

        viewer.show()
    }
}
