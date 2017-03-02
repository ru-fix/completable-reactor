package ru.fix.completable.reactor.plugin.idea;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

/**
 * @author Kamil Asfandiyarov
 */
public class ReacrotGraphEditorProvider implements ApplicationComponent, FileEditorProvider {
    private static Logger log = Logger.getInstance(ReactorGraphAction.class);

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "ReacrotGraphEditorProvider";
    }

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return virtualFile.getFileType() == ReactorGraphFileType.INSTANCE;
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        try {
            return new ReactorGraphEditor(project, new String(virtualFile.contentsToByteArray(), StandardCharsets.UTF_8));
        } catch (Exception exc) {
            log.error("Failed to create ReactorGraph editor", exc);
            return new ReactorGraphEditor(project, new String());
        }
    }



    @NotNull
    @Override
    public String getEditorTypeId() {
        return getComponentName();
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.NONE;
    }
}
