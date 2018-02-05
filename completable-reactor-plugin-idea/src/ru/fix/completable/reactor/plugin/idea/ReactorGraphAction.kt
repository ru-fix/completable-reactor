package ru.fix.completable.reactor.plugin.idea

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.IdeFrame
import com.intellij.openapi.wm.WindowManager
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.search.ProjectAndLibrariesScope
import com.intellij.psi.search.PsiShortNamesCache
import com.intellij.psi.search.searches.AnnotatedElementsSearch
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.JBList
import ru.fix.completable.reactor.api.Reactored
import ru.fix.completable.reactor.parser.java.JavaSourceParser

import java.util.Arrays
import java.util.stream.Collectors

/**
 * @author Kamil Asfandiyarov
 */
class ReactorGraphAction : AnAction() {

    private var log = Logger.getInstance(ReactorGraphAction::class.java)
    private val NOTIFICATION_GROUP_ID = "ru.fix.completable.reactor"

    private fun notifyError(title: String, msg: String) {
        val notification = Notification(
                NOTIFICATION_GROUP_ID,
                title,
                msg,
                NotificationType.ERROR)
        Notifications.Bus.notify(notification)
    }


    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) ?: return
        val editor = event.getData(CommonDataKeys.EDITOR) ?: return
        val caretModel = editor.caretModel


        val parser = JavaSourceParser(object : JavaSourceParser.Listener {
            override fun error(msg: String) {
                notifyError("Failed to parse source", msg)
            }
        })

        val models = parser.parse(editor.document.text)

        notifyError("parsing resul", "models size : ${models.size}")
//
//
//
//        val searchScope = ProjectAndLibrariesScope(project)
//
//        val payloadDescriptionPsiClass = JavaPsiFacade.getInstance(project).findClass(Reactored::class.java.name, searchScope)
//
//        if (payloadDescriptionPsiClass == null) {
//            log.warn("Can not found psi class for annotation: " + Reactored::class.java)
//            return
//        }
//
//
//        val payloadClasses = AnnotatedElementsSearch.searchPsiClasses(payloadDescriptionPsiClass, searchScope).findAll()
//
//        val fileCache = PsiShortNamesCache.getInstance(project)
//
//        val fileNames = payloadClasses.stream()
//                .map<String>({ it.getQualifiedName() })
//                .flatMap { className -> Arrays.stream(fileCache.getFilesByName(className + ".rg")) }
//                .map<String>({ it.getName() })
//                .collect(Collectors.toList())
//
//        val classNames = payloadClasses.stream().map<String>({ it.getQualifiedName() }).collect(Collectors.toList())
//
//        //        showBalloonPopup(project, classNames + "<br>" + fileNames  , MessageType.INFO);
//
//        showListPopup(project, classNames)

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
