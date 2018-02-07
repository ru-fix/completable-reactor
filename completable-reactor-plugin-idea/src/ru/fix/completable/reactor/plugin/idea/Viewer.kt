package ru.fix.completable.reactor.plugin.idea

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
import ru.fix.completable.reactor.graph.viewer.gl.GraphViewer
import ru.fix.completable.reactor.graph.viewer.gl.Shortcut
import ru.fix.completable.reactor.graph.viewer.gl.ShortcutType
import ru.fix.completable.reactor.model.GraphModel
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
    lateinit var displayedModel: GraphModel

    init {
        graphViewer.setShortcut(ShortcutType.GOTO_GRAPH, Shortcut(true, KeyCode.B))

        //Because of https://bugs.openjdk.java.net/browse/JDK-8090517, it is important to disable implicit exit.
        Platform.setImplicitExit(false)
        Platform.runLater {
            swingFxPanel.scene = graphViewer.scene
            scheduleCommand { openGraph() }
        }
    }


    private var lastUpdateTimestamp = AtomicLong()
    private val updateRequired = AtomicBoolean()
    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    /**
     * invoke within command processor
     */
    private fun openGraph() {
        try {
            val models = parser.parse(document.text, virtualFile.path)

            if (models.isEmpty()) {
                notificator.warn("" +
                        "Can not open graph for give source." +
                        " Source does not contain graph classes." +
                        " Source: ${virtualFile.path}")
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
                    graphViewer.openGraph(listOf(displayedModel))

                    openPopup()

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


        editor.document.addDocumentListener(object : DocumentListener {

            override fun documentChanged(event: DocumentEvent?) {
                scheduleCommand { refreshGraph() }
            }
        })
    }

    /**
     * invoke within command processor
     */
    private fun refreshGraph() {
        try {
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
                        graphViewer.openGraph(listOf(model))
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

        if (updateRequired.get()) {
            return
        }

        //do not update more often than each 5 seconds
        val timestamp = lastUpdateTimestamp.get()
        if (System.currentTimeMillis() - timestamp > 5_000) {

            if (lastUpdateTimestamp.compareAndSet(timestamp, System.currentTimeMillis())) {

                executeUpdate(command)
            } else {
                updateRequired.set(true)
            }
        } else {
            updateRequired.set(true)
        }
    }

    private fun executeUpdate(command: () -> Unit) {
        CommandProcessor.getInstance().executeCommand(
                project,
                {
                    updateRequired.set(false)
                    command()
                    if (updateRequired.get()) {

                        scheduler.schedule({
                            executeUpdate(command)
                        }, 5_000, TimeUnit.MILLISECONDS)
                    }
                },
                "Parsing graph",
                null)
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

    fun close() {
        popup.cancel()
    }
}