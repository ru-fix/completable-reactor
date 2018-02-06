package ru.fix.completable.reactor.plugin.idea

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.util.MinimizeButton
import com.intellij.openapi.vfs.LocalFileSystem
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

/**
 * @author Kamil Asfandiyarov
 */
class ReactorGraphAction : AnAction() {

    private var log = Logger.getInstance(ReactorGraphAction::class.java)
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

        val model = models[0]

        val viewer = createViewer(model, project)


        val popup = JBPopupFactory.getInstance()
                .createComponentPopupBuilder(viewer.jfxPanel, viewer.jfxPanel)
                .setProject(project)
                .setResizable(true)
                .setTitle("${model.graphClass}<${model.startPoint.payloadType}>")
                .setShowBorder(true)
                .setMinSize(Dimension(400, 300))
                .setCancelButton(MinimizeButton("Close"))
                .setMovable(true)
                .setFocusable(true)
                .setRequestFocus(true)
                .setCancelOnClickOutside(true)
                .setModalContext(false)
                .createPopup()

        ScreenUtil.getMainScreenBounds().let {
            popup.size = Dimension((it.width * 0.7).toInt(), (it.height * 0.7).toInt())
        }

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


        //TODO: show in center of the window
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
