package io.nuwe.FemHack_JavaFemCoders.model.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}