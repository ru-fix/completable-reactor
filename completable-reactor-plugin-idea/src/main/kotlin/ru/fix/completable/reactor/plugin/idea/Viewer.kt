package ru.fix.completable.reactor.plugin.idea

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.util.MinimizeButton
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.ScreenUtil
import com.intellij.util.BooleanFunction
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.input.KeyCode
import ru.fix.completable.reactor.graph.viewer.GraphViewer
import ru.fix.completable.reactor.graph.viewer.Shortcut
import ru.fix.completable.reactor.graph.viewer.ShortcutType
import ru.fix.completable.reactor.model.CompileTimeGraph
import ru.fix.completable.reactor.parser.java.JavaSourceParser
import java.awt.Dimension
import java.awt.event.KeyEvent
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

class Viewer(
        val document: Document,
        val virtualFile: VirtualFile,
        val project: Project,
        val editor: Editor) {

    private val GRAPH_SOURCE_REPARSING_DELAY_MS = 2_000L

    private val isDisposed = AtomicBoolean(false)

    private val notificator = Notificator()

    val swingFxPanel = JFXPanel()
    val graphViewer = GraphViewer()

    val parser = JavaSourceParser(object : JavaSourceParser.Listener {
        override fun error(msg: String) {
            notificator.error("Failed to parse source: $msg")
        }
    })

    @Volatile
    lateinit var popup: JBPopup

    @Volatile
    lateinit var displayedModel: CompileTimeGraph

    fun show() {
        graphViewer.setShortcut(ShortcutType.GOTO_GRAPH, Shortcut(true, KeyCode.B))

        //Because of https://bugs.openjdk.java.net/browse/JDK-8090517, it is important to disable implicit exit.
        Platform.setImplicitExit(false)
        Platform.runLater {
            swingFxPanel.scene = graphViewer.scene
            scheduleCommand { openGraph() }
        }
    }


    private var lastUpdateTimestamp = AtomicLong(0L)
    private val commandScheduled = AtomicBoolean(false)
    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    val documentListener = object : DocumentListener {
        override fun documentChanged(event: DocumentEvent) {
            scheduleCommand { refreshGraph() }
        }
    }

    /**
     * invoke within command processor
     */
    private fun openGraph() {
        try {

            notificator.info("Load graph")
            val models = parser.parse(document.text, virtualFile.path)

            if (models.isEmpty()) {
                notificator.warn("" +
                        "Can not open graph for given source." +
                        " Source does not contain graph classes." +
                        " Source: ${virtualFile.path}")
                return
            }

            val model = if (models.size == 1) {
                models[0]
            } else {
                val carretOffset = editor.caretModel.offset

                models.sortedBy { it.graphDeclarationLocation?.offset }
                        .filter { it.graphDeclarationLocation?.offset ?: 0 < carretOffset }
                        .last()
            }

            Platform.runLater {
                try {
                    displayedModel = model
                    graphViewer.openGraph(listOf(displayedModel), resetViewer = true)

                    ApplicationManager.getApplication().invokeLater {
                        openPopup()
                    }


                } catch (exc: Exception) {
                    notificator.error("Failed to visualize graph model in viewer.", exc)
                }
            }
        } catch (exc: Exception) {
            notificator.error("Failed to parse graph source.", exc)
        }
    }

    private fun openPopup() {
        popup = JBPopupFactory.getInstance()
                .createComponentPopupBuilder(swingFxPanel, swingFxPanel)
                .setProject(project)
                .setResizable(true)
                .setTitle("${displayedModel.graphClass}<${displayedModel.startPoint.payloadType}>")
                .setShowBorder(true)
                .setMinSize(Dimension(400, 300))
                .setCancelButton(MinimizeButton("Close (Esc)"))
                .setCancelCallback {
                    dispose()
                    true
                }
                .setMovable(true)
                .setFocusable(true)
                .setRequestFocus(true)
                .setCancelOnClickOutside(false)
                .setModalContext(false)
                .setKeyEventHandler(object : BooleanFunction<KeyEvent> {

                    var isMaximized = false

                    override fun `fun`(it: KeyEvent): Boolean {

                        //TODO replace with shortcut from shortcut config for reactor action
                        if (it.id == KeyEvent.KEY_PRESSED
                                && it.isControlDown
                                && it.isAltDown
                                && !it.isShiftDown
                                && !it.isMetaDown
                                && it.keyCode == KeyEvent.VK_R) {

                            if (!isMaximized) {
                                maximaizePopup(popup)
                                isMaximized = true
                            } else {
                                setDefaultPopupSize(popup)
                                isMaximized = false
                            }

                            return true

                        } else {
                            return false
                        }
                    }
                })
                .createPopup()

        setDefaultPopupSize(popup)


        //TODO: save user selected size
        popup.showCenteredInCurrentWindow(project)

        //TODO: reload content when file is changed


        editor.document.addDocumentListener(documentListener)
    }

    /**
     * invoke within command processor
     */
    private fun refreshGraph() {
        try {

            notificator.info("Reload graph.")

            val models = parser.parse(document.text, virtualFile.path)

            if (models.isEmpty()) {
                notificator.warn("" +
                        "Can not refresh graph for give source." +
                        " Source does not contain graph classes." +
                        " Source: ${virtualFile.path}")
            }

            val model = if (models.size == 1) {
                models[0]
            } else {
                models.find { it.graphClass == displayedModel.graphClass }
            }

            if (model != null) {

                Platform.runLater {
                    try {
                        graphViewer.openGraph(listOf(model), resetViewer = false)
                    } catch (exc: Exception) {
                        notificator.error("Failed to visualize refreshed graph model in viewer.", exc)
                    }
                }
            } else {
                popup.cancel()
            }

        } catch (exc: Exception) {
            notificator.error("Failed to parse graph source.", exc)
        }
    }


    private fun scheduleCommand(command: () -> Unit) {

        if (commandScheduled.get()) {
            return
        }

        val needToTryToScheduleCommand: Boolean

        //do not update more often than each 5 seconds
        val timestamp = lastUpdateTimestamp.get()
        if (System.currentTimeMillis() - timestamp > GRAPH_SOURCE_REPARSING_DELAY_MS) {

            if (lastUpdateTimestamp.compareAndSet(timestamp, System.currentTimeMillis())) {
                needToTryToScheduleCommand = false
                executeCommand(command)

            } else {
                needToTryToScheduleCommand = true
            }
        } else {
            needToTryToScheduleCommand = true
        }

        if (needToTryToScheduleCommand) {

            if (commandScheduled.compareAndSet(false, true)) {

                scheduler.schedule(
                        {
                            commandScheduled.set(false)
                            executeCommand(command)
                        },
                        GRAPH_SOURCE_REPARSING_DELAY_MS,
                        TimeUnit.MILLISECONDS)
            }
        }
    }

    private fun executeCommand(command: () -> Unit) {
        ApplicationManager.getApplication().invokeLater {
            CommandProcessor.getInstance().executeCommand(
                    project,
                    {
                        command()

                    },
                    "Parsing graph",
                    null)
        }
    }

    private fun setDefaultPopupSize(popup: JBPopup) {
        ScreenUtil.getMainScreenBounds().let {
            popup.size = Dimension((it.width * 0.9).toInt(), (it.height * 0.9).toInt())
        }
    }

    private fun maximaizePopup(popup: JBPopup) {
        ScreenUtil.getMainScreenBounds().let {
            popup.size = Dimension(it.width, it.height)
        }
    }

    private fun dispose() {
        if (isDisposed.compareAndSet(false, true)) {
            editor.document.removeDocumentListener(documentListener)
        }
    }

    fun close() {
        popup.cancel()
        dispose()
    }
}