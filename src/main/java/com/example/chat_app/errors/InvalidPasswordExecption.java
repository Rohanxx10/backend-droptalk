package com.example.chat_app.errors;

import org.springframework.http.HttpStatus;

public class InvalidPasswordExecption extends Throwable {
    public InvalidPasswordExecption(String passwordIsNotValid, HttpStatus httpStatus) {
    }
}
