package com.example.chat_app.service.dto;



import com.example.chat_app.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    private String sender;
    private String receiver;

    // TEXT or FILE
    private MessageType type;

    // text OR file URL
    private String content;

    // file metadata (only for FILE)
    private String fileName;
    private String fileType;
    private Long fileSize;

    private String time;
}

