package com.example.chat_app.controller;


import com.example.chat_app.service.dto.MessageRequest;
import com.example.chat_app.service.impl.WebService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSecketController {

    private final WebService webService;


    public WebSecketController(WebService webService) {
        this.webService = webService;
    }


    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload MessageRequest request, Principal principal) {

        System.out.println("🔥 CONTROLLER HIT");
        System.out.println("Request = " + request);
        System.out.println("Principal = " + (principal != null ? principal.getName() : "NULL"));

        webService.sendMessage(request, principal);
    }




}
