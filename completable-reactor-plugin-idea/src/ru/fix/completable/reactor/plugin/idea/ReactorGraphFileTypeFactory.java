package ru.fix.completable.reactor.plugin.idea;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kamil Asfandiyarov
 */
public class ReactorGraphFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(ReactorGraphFileType.INSTANCE, ReactorGraphFileType.DEFAULT_EXTENSION);
    }
}
