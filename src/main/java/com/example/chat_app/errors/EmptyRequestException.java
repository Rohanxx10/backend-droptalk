package com.example.chat_app.errors;

import org.springframework.http.HttpStatus;

public class EmptyRequestException extends RuntimeException {

    public EmptyRequestException(String requestCannotTheEmpty, HttpStatus httpStatus) {

    }
}
