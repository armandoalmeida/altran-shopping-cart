package com.altran.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserMustBeUniqueException extends ResponseStatusException {
    public UserMustBeUniqueException(String user) {
        super(HttpStatus.BAD_REQUEST, String.format("The user '%s' already exists", user));
    }
}
