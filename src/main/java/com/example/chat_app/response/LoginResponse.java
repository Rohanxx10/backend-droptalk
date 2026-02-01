package com.example.chat_app.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String message;
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicture;
    private String token;


}
