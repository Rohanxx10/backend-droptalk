package com.example.chat_app.controller;


import com.example.chat_app.response.ChatResponse;
import com.example.chat_app.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.title}")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/conversations")
    public ResponseEntity<?> getPreviousChats(@RequestParam String sender, @RequestParam String receiver){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTH = " + auth);

        List<ChatResponse> responses=chatService.getPreviousConversationsBetweenTwoUsers(sender,receiver);
        return ResponseEntity.ok(responses);

    }

}
