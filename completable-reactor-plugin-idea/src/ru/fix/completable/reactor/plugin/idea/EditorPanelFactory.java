package ru.fix.completable.reactor.plugin.idea;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.ProjectAndLibrariesScope;
import com.intellij.psi.search.PsiShortNamesCache;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.graph.viewer.GraphViewer;
import ru.fix.completable.reactor.graph.viewer.Shortcut;
import ru.fix.completable.reactor.graph.viewer.ShortcutType;
import ru.fix.completable.reactor.graph.viewer.code.CodeUpdater;

import java.util.Arrays;
import java.util.List;

/**
 * @author Kamil Asfandiyarov
 */
public class EditorPanelFactory {
    private static Logger log = Logger.getInstance(ReactorGraphAction.class);

    private static final CodeUpdater codeUpdater = new CodeUpdater();

    public static JFXPanel create(Project project, String graphModel) {
        JFXPanel swingFxPanel = new JFXPanel();
        GraphViewer graphViewer = new GraphViewer();

        graphViewer.setShortcut(ShortcutType.GOTO_SERIALIZATION_POINT, new Shortcut(true, KeyCode.B));

        graphViewer.registerListener(new GraphViewer.ActionListener() {
            @Override
            public void goToSource(@NotNull ReactorGraphModel.Source source) {
                ApplicationManager.getApplication().invokeLater(
                        () -> ApplicationManager.getApplication().runReadAction(() -> {

                            PsiFile foundFile = null;

                            if(source.fileName != null) {
                                foundFile = Arrays.stream(PsiShortNamesCache.getInstance(project).getFilesByName(source.fileName))
                                        .findAny()
                                        .orElse(null);

                            } else if(source.className != null){

                                ProjectAndLibrariesScope searchScope = new ProjectAndLibrariesScope(project);
                                PsiClass payloadPsiClass = JavaPsiFacade.getInstance(project).findClass(source.className, searchScope);
                                if(payloadPsiClass != null) {
                                    foundFile = payloadPsiClass.getContainingFile();
                                }
                            }

                            if(foundFile == null){
                                log.warn("Can not find file for source: " + source);
                                return;
                            }

                            OpenFileDescriptor descriptor = new OpenFileDescriptor(
                                    project,
                                    foundFile.getVirtualFile(),
                                    source.fileNameLine != null ? source.fileNameLine - 1 : 0,
                                    0);
                            descriptor.navigate(true);
                        })
                );
            }

            @Override
            public void goToSubgraph(ReactorGraphModel.Identity subgraphIdentity) {
                if(subgraphIdentity.getType() != ReactorGraphModel.Identity.Type.SUBGRAPH){
                    //ignore non subgraph identity
                    return;
                }
                ApplicationManager.getApplication().invokeLater(
                        () -> ApplicationManager.getApplication().runReadAction(() -> {

                            PsiFile foundFile = null;

                            if(subgraphIdentity.getClassName() != null) {
                                foundFile = Arrays.stream(PsiShortNamesCache.getInstance(project)
                                        .getFilesByName(subgraphIdentity.getClassName() + ".rg"))
                                        .findAny()
                                        .orElse(null);

                            }

                            if(foundFile == null){
                                log.warn("Can not find file for subgraph: " + subgraphIdentity);
                                return;
                            }

                            OpenFileDescriptor descriptor = new OpenFileDescriptor(
                                    project,
                                    foundFile.getVirtualFile(),
                                    source.fileNameLine != null ? source.fileNameLine - 1 : 0,
                                    0);
                            descriptor.navigate(true);
                        })
                );
            }

            @Override
            public void coordinatesChanged(List<GraphViewer.CoordinateItem> coordinateItems) {

                ReactorGraphModel graphModel = graphViewer.getGraphModel();

                final ReactorGraphModel.Source codeBlockFrom = graphModel.coordinatesSource;

                if (codeBlockFrom == null) {
                    log.warn("Coordinates source start location not specified in model.");
                    return;
                }

                ApplicationManager.getApplication().invokeLater(() -> {
                    ApplicationManager.getApplication().runReadAction(() -> {

                        PsiFile[] foundFiles = PsiShortNamesCache.getInstance(project).getFilesByName(codeBlockFrom.fileName);
                        if (foundFiles.length == 0) {
                            log.warn("No file with name " + codeBlockFrom.fileName + " found");
                            return;
                        }
                        if (foundFiles.length > 1) {
                            log.warn("Found more than one file with name " + codeBlockFrom.fileName);
                        }
                        PsiFile foundFile = foundFiles[0];

                        Document document = PsiDocumentManager.getInstance(project).getDocument(foundFile);

                        TextRange textRange = new TextRange(
                                document.getLineStartOffset(codeBlockFrom.fileNameLine - 1),
                                document.getLineEndOffset(document.getLineCount() - 1));

                        String codeBlock = document.getText(textRange);

                        String newCodeBlock = codeUpdater.updateCoordinates(codeBlock, coordinateItems);

                        WriteCommandAction.runWriteCommandAction(project, () -> document.replaceString(textRange.getStartOffset(), textRange.getEndOffset(), newCodeBlock));
                    });

                });
            }
        });
        //Because of https://bugs.openjdk.java.net/browse/JDK-8090517, it is important to disable implicit exit.
        Platform.setImplicitExit(false);
        Platform.runLater(() ->

        {
            try {
                graphViewer.openGraph(graphModel);
                swingFxPanel.setScene(graphViewer.getScene());
            } catch (Exception exc) {
                log.error("Failed to open graph.", exc);
            }
        });

        return swingFxPanel;
    }
}
