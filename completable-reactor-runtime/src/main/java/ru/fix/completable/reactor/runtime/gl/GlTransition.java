package ru.fix.completable.reactor.runtime.gl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

final class GlTransition {

    Set<Enum<?>> mergeStatuses;

    boolean isOnAny;
    boolean isComplete = false;

    Vertex mergeBy;
    Vertex handleBy;

    public GlTransition setOnAny(boolean onAny) {
        isOnAny = onAny;
        return this;
    }

    public GlTransition setComplete(boolean complete) {
        isComplete = complete;
        return this;
    }

    public GlTransition setMergeBy(Vertex mergeBy) {
        this.mergeBy = mergeBy;
        return this;
    }

    public GlTransition setHandleBy(Vertex handleBy) {
        this.handleBy = handleBy;
        return this;
    }

    public GlTransition setMergeStatuses(Enum<?>... mergeStatuses) {
        this.mergeStatuses = new HashSet<>(Arrays.asList(mergeStatuses));
        return this;
    }

    public String getDebugDescription() {
        if (isOnAny) {
            return "{onAny}";
        } else if (mergeStatuses != null) {
            return mergeStatuses.stream()
                    .map(status -> status.getDeclaringClass() + "." + status)
                    .collect(Collectors.joining(",", "{", "}"));
        } else {
            return "{INVALID}";
        }
    }
}
