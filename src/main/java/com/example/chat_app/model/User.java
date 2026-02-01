package com.example.chat_app.model;


import com.example.chat_app.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String firstname;
    private String lastname;
    @Column(
            name = "username",
            nullable = false,
            unique = true,
            length = 50
    )
    private String username;

    private String email;

    private String profilePicture;
    private String password;
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ChatMessage> messages;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private ActiveSession session;
    private boolean enabled=true;
    private String token;
    private boolean isLoggedIn;
    private LocalDateTime createdAt;



}
