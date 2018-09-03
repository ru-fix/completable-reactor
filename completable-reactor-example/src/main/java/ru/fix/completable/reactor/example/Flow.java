package ru.fix.completable.reactor.example;

/**
 * @author Kamil Asfandiyarov
 */
public enum Flow {
    CONTINUE,

    STOP,

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

    NO_WITHDRAWAL,


}
