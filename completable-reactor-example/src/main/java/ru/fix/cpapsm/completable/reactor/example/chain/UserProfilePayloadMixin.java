package ru.fix.cpapsm.completable.reactor.example.chain;

import ru.fix.cpapsm.completable.reactor.example.processors.UserProfile;

/**
 * @author Kamil Asfandiyarov
 */
public interface UserProfilePayloadMixin {
    Long getUserId();
    UserProfile getUserProfile();
    void setUserProfile(UserProfile userProfile);
    void setStatus(Enum status);
    Enum getStatus();
}