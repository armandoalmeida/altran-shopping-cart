package com.altran.shoppingcart.exception;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DocumentNotFoundException extends ResponseStatusException {
    public DocumentNotFoundException(String document) {
        super(HttpStatus.BAD_REQUEST, String.format("Document not found: %s", document));
    }
}
