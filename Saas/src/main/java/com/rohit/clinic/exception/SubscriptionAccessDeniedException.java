package com.rohit.clinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SubscriptionAccessDeniedException extends RuntimeException {

    public SubscriptionAccessDeniedException(String message) {
        super(message);
    }
}
