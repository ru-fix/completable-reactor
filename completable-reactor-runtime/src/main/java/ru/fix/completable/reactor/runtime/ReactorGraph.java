package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.api.ReactorGraphModel;

/**
 * Runtime graph model used to generate execution chain.
 *
 * @author Kamil Asfandiyarov
 */
public interface ReactorGraph<PayloadType> {
    ReactorGraphModel serialize();
}
