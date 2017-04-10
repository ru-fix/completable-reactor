package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.api.Reactored;

/**
 * @author Kamil Asfandiyarov
 */
public enum MergeStatus {
    @Reactored("Continue processing")
    CONTINUE,

    @Reactored("Stop processing")
    STOP,

    @Reactored("Withdraw money required")
    WITHDRAWAL,

    @Reactored("No withdrawal required")
    NO_WITHDRAWAL
}
