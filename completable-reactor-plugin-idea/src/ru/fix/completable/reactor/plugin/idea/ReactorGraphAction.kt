package ru.fix.completable.reactor.plugin.idea

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.util.MinimizeButton
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.ScreenUtil
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.JBList
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.input.KeyCode
import ru.fix.completable.reactor.graph.viewer.gl.CoordinateItem
import ru.fix.completable.reactor.graph.viewer.gl.GraphViewer
import ru.fix.completable.reactor.graph.viewer.gl.Shortcut
import ru.fix.completable.reactor.graph.viewer.gl.ShortcutType
import ru.fix.completable.reactor.model.GraphModel
import ru.fix.completable.reactor.model.Source
import ru.fix.completable.reactor.parser.java.JavaSourceParser
import java.awt.Dimension
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.charset.StandardCharsets

/**
 * @author Kamil Asfandiyarov
 */
class ReactorGraphAction : AnAction() {

    private val NOTIFICATION_GROUP_ID = "ru.fix.completable.reactor"


    private fun notify(msg: String, notificationType: NotificationType) {
        val notification = Notification(
                NOTIFICATION_GROUP_ID,
                "Reactor graph",
                msg,
                notificationType)
        Notifications.Bus.notify(notification)
    }


    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) ?: return
        val editor = event.getData(CommonDataKeys.EDITOR) ?: return
        val virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return


        val parser = JavaSourceParser(object : JavaSourceParser.Listener {
            override fun error(msg: String) {
                notify("Failed to parse source: $msg", NotificationType.ERROR)
            }
        })

        val models = parser.parse(editor.document.text, virtualFile.path)

        if (models.isEmpty()) {
            notify("Can not open graph for give source. Source does not contain graph classes.",
                    NotificationType.WARNING)
        }

        val model = if (models.size == 1) {
            models[0]
        } else {
            val carretOffset = editor.caretModel.offset

            models.sortedBy { it.graphDeclarationLocation?.offset }
                    .filter { it.graphDeclarationLocation?.offset ?: 0 < carretOffset }
                    .last()
        }

        val viewer = createViewer(model, project)


        val popup = JBPopupFactory.getInstance()
                .createComponentPopupBuilder(viewer.jfxPanel, viewer.jfxPanel)
                .setProject(project)
                .setResizable(true)
                .setTitle("${model.graphClass}<${model.startPoint.payloadType}>")
                .setShowBorder(true)
                .setMinSize(Dimension(400, 300))
                .setCancelButton(MinimizeButton("Close (Esc)"))
                .setMovable(true)
                .setFocusable(true)
                .setRequestFocus(true)
                .setCancelOnClickOutside(false)
                .setModalContext(false)
//                .setKeyEventHandler {
//                    //TODO replace with shortcut from shortcut config for reactor action
//                    if(it.isControlDown && it.isAltDown && it.keyCode == KeyEvent.VK_R){
//
//                        notify("KEY PRESSED!", NotificationType.WARNING)
//
//                        true
//                    } else {
//                        false
//                    }
//                }
                .createPopup()

        ScreenUtil.getMainScreenBounds().let {
            popup.size = Dimension((it.width * 0.9).toInt(), (it.height * 0.9).toInt())
        }

        val connection = ApplicationManager.getApplication().messageBus.connect()
        connection.subscribe(VirtualFileManager.VFS_CHANGES, object : BulkFileListener {
            override fun after(events: MutableList<out VFileEvent>) {

                val graphClass = model.graphClass

                if(events.any { it.file == virtualFile }){

                    val models = parser.parse(
                            virtualFile.contentsToByteArray().toString(StandardCharsets.UTF_8),
                            virtualFile.path)

                    val model = models.find { it.graphClass == graphClass }
                    if(model != null){
                        viewer.graphViewer.openGraph(listOf(model))
                    } else{
                        popup.cancel()
                    }

                }
                super.after(events)
            }
        })


        viewer.graphViewer.registerListener(object : GraphViewer.ActionListener {
            override fun coordinatesChanged(coordinateItems: List<CoordinateItem>) {
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

                                    popup.cancel()

                                    descriptor.navigate(true)

                                }
                                ?: notify(
                                        "Can not open source location. File not found. " +
                                                "Source: $source, path: ${source.filePath}",
                                        NotificationType.ERROR)
                    }
                }
            }

            override fun goToSubgraph(subgraphPayloadType: String) {
                //TODO goToSubgraph
                notify("Go to subgraph not supported. Can not open subgraph $subgraphPayloadType, not supported yet",
                        NotificationType.INFORMATION)
            }
        })

        //TODO: save user selected size
        popup.showCenteredInCurrentWindow(project)

        //TODO: reload content when file is changed
        //TODO: react on go to source action
    }

    class Viewer(val graphViewer: GraphViewer, val jfxPanel: JFXPanel)

    private fun createViewer(graphModel: GraphModel, project: Project): Viewer {
        val swingFxPanel = JFXPanel()
        val graphViewer = GraphViewer()
        graphViewer.setShortcut(ShortcutType.GOTO_GRAPH, Shortcut(true, KeyCode.B))

        //Because of https://bugs.openjdk.java.net/browse/JDK-8090517, it is important to disable implicit exit.
        Platform.setImplicitExit(false)
        Platform.runLater {
            try {
                swingFxPanel.scene = graphViewer.scene
                graphViewer.openGraph(listOf(graphModel))
            } catch (exc: Exception) {
                notify(
                        exc.run {
                            StringWriter().let {
                                printStackTrace(PrintWriter(it))
                                "Failed to open model in graph viewer.\n $it"
                            }
                        },
                        NotificationType.ERROR)
            }
        }

        return Viewer(graphViewer, swingFxPanel)
    }


    private fun showListPopup(project: Project, data: List<String>) {
        val ideFrame = WindowManager.getInstance().getIdeFrame(project)

        val jbList = JBList(data)

        JBPopupFactory.getInstance()
                .createListPopupBuilder(jbList)
                .createPopup()
                .showInFocusCenter()
    }

    private fun showBalloonPopup(project: Project, htmlText: String, messageType: MessageType) {
        val ideFrame = WindowManager.getInstance().getIdeFrame(project)

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(htmlText, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getNorthEastOf(ideFrame.component), Balloon.Position.atRight)
    }
}
