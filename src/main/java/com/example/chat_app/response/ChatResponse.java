package com.example.chat_app.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private Long id;
    private String sender;
    private String receiver;

    // TEXT or FILE
    private String type;

    // text content OR file URL
    private String content;

    // FILE metadata (nullable for TEXT)
    private String fileName;
    private String fileType;
    private Long fileSize;

    private String time;
}
