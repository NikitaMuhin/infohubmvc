package com.infohubmvc.application.security;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String name) {
        super("User not logged in: " + name);
    }
}
