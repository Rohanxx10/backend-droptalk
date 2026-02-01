package com.example.chat_app.errors;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException(String s, HttpStatus httpStatus) {
    }
}
