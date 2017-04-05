package ru.fix.completable.reactor.runtime.internal;

import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
@Slf4j
public class ReactorReflector {

    private static final String[] methodInvocationClassNamePrefixSkipList = new String[]{
            CompletableReactor.class.getPackage().getName()
    };

    /**
     * @return location where method was called in format class_name:line_number
     */
    public static Optional<ReactorGraphModel.Source> getMethodInvocationPoint() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();


        stackTrace:
        for (int i = 3; i < trace.length; i++) {
            //skip package
            for (String skipPackage : methodInvocationClassNamePrefixSkipList) {
                if (trace[i].getClassName().startsWith(skipPackage)) {
                    continue stackTrace;
                }
            }

            StackTraceElement traceElement = trace[i];

            ReactorGraphModel.Source source = new ReactorGraphModel.Source();
            source.fileName = traceElement.getFileName();
            source.fileNameLine = traceElement.getLineNumber();
            source.className = traceElement.getClassName();
            return Optional.of(source);
        }

        return Optional.empty();
    }
}
