package ru.fix.completable.reactor.example.processors;

import ru.fix.completable.reactor.api.HandlerDescription;
import ru.fix.completable.reactor.api.ProcessorDescription;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.fix.completable.reactor.example.processors.UserProfileProcessor.Status.OK;
import static ru.fix.completable.reactor.example.processors.UserProfileProcessor.Status.USER_NOT_FOUND;

/**
 * @author Kamil Asfandiyarov
 */

@ProcessorDescription(doc = {
        "UserProfileProcessor load information about user",
        "from remote database.",
        "User profile contains information whether user is blocked or not."

})
public class UserProfileProcessor  {

    public static final Long USER_ID_JOHN = 1L;
    public static final Long USER_ID_JACK = 2L;
    public static final Long USER_ID_INVALID = 3L;


    /**
     * Emulating data in remote database
     */
    final Map<Long, UserProfile> userProfiles = Stream.of(
            new UserProfile()
                    .setUserId(USER_ID_JOHN)
                    .setName("John")
                    .setBlocked(false),
            new UserProfile()
                    .setUserId(USER_ID_JACK)
                    .setName("Jack")
                    .setBlocked(true))
            .collect(Collectors.toMap(info -> info.userId, info -> info));


    public enum Status {
        USER_NOT_FOUND,
        USER_IS_BLOCKED,
        OK
    }

    public static class UserProfileResult {

        public Status status;
        public UserProfile userProfile;

        public UserProfileResult setStatus(Status status) {
            this.status = status;
            return this;
        }

        public UserProfileResult setUserProfile(UserProfile userProfile) {
            this.userProfile = userProfile;
            return this;
        }
    }


    @HandlerDescription(doc = {
            "Load user profile from database by user id  - lambda.",
    })
    public CompletableFuture<UserProfileResult> loadUserProfileById(long userId, String nonNullUnusedParam) {

        Objects.nonNull(nonNullUnusedParam);

        return CompletableFuture
                .supplyAsync(() ->
                        /**
                         * loading data from remote database
                         */
                        userProfiles.get(userId)
                )
                .thenApplyAsync(userProfile -> {

                    if (userProfile == null) {
                        return new UserProfileResult()
                                .setStatus(USER_NOT_FOUND);
                    } else {
                        if (userProfile.isBlocked) {
                            return new UserProfileResult()
                                    .setStatus(Status.USER_IS_BLOCKED);
                        }

                        return new UserProfileResult()
                                .setUserProfile(userProfile)
                                .setStatus(OK);
                    }
                });
    }

}
