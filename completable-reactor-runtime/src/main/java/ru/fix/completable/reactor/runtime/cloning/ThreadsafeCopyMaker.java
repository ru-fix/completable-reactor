package ru.fix.completable.reactor.runtime.cloning;

/**
 * @author Kamil Asfandiyarov
 */
public interface ThreadsafeCopyMaker {
    /**
     * Make copy of pojo.
     * It should be safe to read data from copy while origin pojo is being modified in different thread.
     *
     * If pojo or any part of pojo is immutable then it is possible copy only references to immutable objects.
     * @param object any java object
     */
    <T> T makeThreadsafeCopy(T object);
}
