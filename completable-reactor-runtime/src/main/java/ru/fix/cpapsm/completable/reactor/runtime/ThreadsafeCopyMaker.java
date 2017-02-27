package ru.fix.cpapsm.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
public interface ThreadsafeCopyMaker {
    /**
     * Make copy of pojo.
     * It should be safe to read data from copy while origin pojo is being modified in different thread.
     *
     * If pojo or any part of pojo is immutable then it is possible copy only references to immutable objects.
     */
    <T> T makeThreadsafeCopy(T pojo);
}
