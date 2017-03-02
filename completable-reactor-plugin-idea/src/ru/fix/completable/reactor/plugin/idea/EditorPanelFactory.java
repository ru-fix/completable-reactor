package ru.fix.completable.reactor.plugin.idea;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.PsiShortNamesCache;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;
import ru.fix.cpapsm.completable.reactor.api.ReactorGraphModel;
import ru.fix.cpapsm.completable.reactor.graph.viewer.GraphViewer;
import ru.fix.cpapsm.completable.reactor.graph.viewer.Shortcut;
import ru.fix.cpapsm.completable.reactor.graph.viewer.ShortcutType;
import ru.fix.cpapsm.completable.reactor.graph.viewer.code.CodeUpdater;

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
                            PsiFile[] foundFiles = PsiShortNamesCache.getInstance(project).getFilesByName(source.fileName);
                            if (foundFiles.length == 0) {
                                log.warn("No file with name " + source.fileName + " found");
                                return;
                            }
                            if (foundFiles.length > 1) {
                                log.warn("Found more than one file with name " + source.fileName);
                            }
                            PsiFile foundFile = foundFiles[0];
                            OpenFileDescriptor descriptor = new OpenFileDescriptor(project, foundFile.getVirtualFile(), source.fileNameLine - 1, 0);
                            descriptor.navigate(true);
                        })
                );
            }

            @Override
            public void coordinatesChanged(List<GraphViewer.CoordinateItem> coordinateItems) {

                ReactorGraphModel graphModel = graphViewer.getGraphModel();

                final ReactorGraphModel.Source codeBlockFrom = graphModel.coordinatesSource;
                final ReactorGraphModel.Source codeBlockTo = graphModel.buildGraphSource;

                if (codeBlockFrom == null) {
                    log.warn("Coordinates source start location not specified in model.");
                    return;
                }
                if (codeBlockTo == null) {
                    log.warn("Coordinates source end location not specified in model.");
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
                                document.getLineEndOffset(codeBlockTo.fileNameLine - 1));

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
