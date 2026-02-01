package com.example.chat_app.model;


import com.example.chat_app.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;
    private String receiver;

    // TEXT or FILE
    @Enumerated(EnumType.STRING)
    private MessageType type;

    // text message OR file URL
    @Column(length = 2000)
    private String content;
    private String time;

    // FILE metadata (nullable)
    private String fileName;
    private String fileType;   // image/png, application/pdf
    private Long fileSize;


    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;
}

