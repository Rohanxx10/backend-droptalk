package com.example.chat_app.service;

import com.example.chat_app.response.ChatResponse;
import com.example.chat_app.service.dto.MessageRequest;

import java.util.List;

public interface ChatService {



    ChatResponse saveChat(MessageRequest request);

    List<ChatResponse> getPreviousConversationsBetweenTwoUsers(String sender,String receiver);

}
