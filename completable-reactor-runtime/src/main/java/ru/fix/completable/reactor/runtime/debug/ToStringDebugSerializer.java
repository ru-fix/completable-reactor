package ru.fix.completable.reactor.runtime.debug;

/**
 * Plain {@link Object#toString()} delegation.
 * Should be replaced with reflection based object dumping.
 *
 * @author Kamil Asfandiyarov
 */
public class ToStringDebugSerializer implements DebugSerializer {
    @Override
    public String dumpObject(Object object) {
        return "" + object;
    }
}
