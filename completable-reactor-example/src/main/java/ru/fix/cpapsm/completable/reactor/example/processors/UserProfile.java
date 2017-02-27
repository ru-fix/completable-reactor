package ru.fix.cpapsm.completable.reactor.example.processors;

/**
 * Created by swarmshine on 01.11.2016.
 */
public class UserProfile {
    public Long userId;
    public String name;
    public boolean isBlocked;

    public UserProfile setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public UserProfile setName(String name) {
        this.name = name;
        return this;
    }

    public UserProfile setBlocked(boolean blocked) {
        isBlocked = blocked;
        return this;
    }
}
