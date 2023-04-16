package org.example.app.exceptions;

public class BookShelfLoginException extends Exception {
    private final String message;
    public BookShelfLoginException(String invalid_username_or_password) {
        this.message = invalid_username_or_password;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
