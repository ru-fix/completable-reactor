package ru.fix.cpapsm.completable.reactor.plugin.idea;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * @author Kamil Asfandiyarov
 */
public class ReactorGraphFileType implements FileType {

    public static final String DEFAULT_EXTENSION = "rg";

    public static final ReactorGraphFileType INSTANCE = new ReactorGraphFileType();

    @NotNull
    @Override
    public String getName() {
        return "ReactorGraph";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "ReactorGraph diagram";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Diagram;
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return StandardCharsets.UTF_8.name();
    }
}
