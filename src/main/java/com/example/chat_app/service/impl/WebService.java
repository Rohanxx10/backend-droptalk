package com.example.chat_app.service.impl;


import com.example.chat_app.response.ChatResponse;
import com.example.chat_app.service.ChatService;
import com.example.chat_app.service.dto.MessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class WebService {

    private static final Logger logger= LoggerFactory.getLogger(WebService.class);

    private final ChatService chatService;

    private final SimpMessagingTemplate simpMessagingTemplate;


    public WebService(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMessage(MessageRequest request, Principal principal) {

        if (principal == null) {
            throw new SecurityException("Unauthenticated user");
        }

        //  override sender
        request.setSender(principal.getName());

        ChatResponse response = chatService.saveChat(request);

        simpMessagingTemplate.convertAndSendToUser(
                request.getReceiver(),
                "/queue/messages",
                response
        );
    }



}
