package ru.fix.completable.reactor.plugin.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.ProjectAndLibrariesScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.search.searches.AnnotatedElementsSearch;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.components.JBList;
import ru.fix.completable.reactor.api.PayloadDescription;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kamil Asfandiyarov
 */
public class ReactorGraphAction extends AnAction {

    Logger log = Logger.getInstance(ReactorGraphAction.class);

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);

        ProjectAndLibrariesScope searchScope = new ProjectAndLibrariesScope(project);

        PsiClass payloadDescriptionPsiClass = JavaPsiFacade.getInstance(project).findClass(PayloadDescription.class.getName(), searchScope);

        if(payloadDescriptionPsiClass == null){
            log.warn("Can not found psi class for annotation: " + PayloadDescription.class);
            return;
        }


        Collection<PsiClass> payloadClasses = AnnotatedElementsSearch.searchPsiClasses(payloadDescriptionPsiClass, searchScope).findAll();

        PsiShortNamesCache fileCache = PsiShortNamesCache.getInstance(project);

        List<String> fileNames = payloadClasses.stream()
                .map(PsiClass::getQualifiedName)
                .flatMap(className -> Arrays.stream(fileCache.getFilesByName(className + ".rg")))
                .map(PsiFile::getName)
                .collect(Collectors.toList());

        List<String> classNames = payloadClasses.stream().map(PsiClass::getQualifiedName).collect(Collectors.toList());

//        showBalloonPopup(project, classNames + "<br>" + fileNames  , MessageType.INFO);

        showListPopup(project, classNames);

    }

    private void showListPopup(Project project, List<String> data) {
        IdeFrame ideFrame = WindowManager.getInstance().getIdeFrame(project);

        JBList<String> jbList = new JBList<>(data);

        JBPopupFactory.getInstance()
                .createListPopupBuilder(jbList)
                .createPopup()
                .showInFocusCenter();
    }

    private void showBalloonPopup(Project project, String htmlText, MessageType messageType) {
        IdeFrame ideFrame = WindowManager.getInstance().getIdeFrame(project);

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(htmlText, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getNorthEastOf(ideFrame.getComponent()), Balloon.Position.atRight);
    }
}
