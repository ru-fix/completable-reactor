package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.api.Reactored;

/**
 * @author Kamil Asfandiyarov
 */
public enum Flow {
    @Reactored("Continue processing")
    CONTINUE,

    @Reactored("Stop processing")
    STOP,

    @Reactored("Withdraw money required")
    WITHDRAWAL,

    //TODO add javadoc comments to UI
    /**
     * There is extra service available to user as a bonus
     */
    BONUS_EXIST,

    /**
     * There is no bonus available for given purchase.
     */
    NO_BONUS,

    @Reactored("No withdrawal required")
    NO_WITHDRAWAL
}
