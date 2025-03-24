package com.splashScore.waterpolo_app.exception;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String username) {
        super("Username " + username + " already exists");
    }
}
