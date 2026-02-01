package com.example.chat_app.service.dto;

import com.example.chat_app.enums.Role;
import com.example.chat_app.model.ActiveSession;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private ActiveSession session;


    public UserRegistrationRequest(String firstName, String lastName, String username, String email, String phoneNumber, String password, String confirmPassword) {
    }
}
