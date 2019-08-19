package ru.fix.completable.reactor.runtime.debug;

/**
 * Plain {@link Object#toString()} delegation.
 * Should be replaced with reflection based object dumping.
 *
 * @author Kamil Asfandiyarov
 */
public class ToStringDebugSerializer implements DebugSerializer {

    //TODO add dump implementation for kotlin data classes
    @Override
    public String dumpObject(Object object) {
        return "" + object;
    }
    //TODO add to doc example how to provide custom debug serializer
}
