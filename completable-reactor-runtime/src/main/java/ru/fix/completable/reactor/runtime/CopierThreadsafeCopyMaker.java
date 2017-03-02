package ru.fix.completable.reactor.runtime;

import com.rits.cloning.Cloner;

/**
 * @author Kamil Asfandiyarov
 */
public class CopierThreadsafeCopyMaker implements ThreadsafeCopyMaker {

    final Cloner cloner = new Cloner();

    @Override
    public <T> T makeThreadsafeCopy(T pojo) {
        /**
         * cloner is thread-safe
         *
         * this implementation do not understand object immutability
         * should be replaced with better one that do not copy immutable objects
         */
        return cloner.deepClone(pojo);
    }
}
