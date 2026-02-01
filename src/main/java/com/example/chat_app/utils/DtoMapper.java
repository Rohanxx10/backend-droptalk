package com.example.chat_app.utils;


import com.example.chat_app.model.ChatMessage;
import com.example.chat_app.model.User;
import com.example.chat_app.response.ChatResponse;
import com.example.chat_app.response.UserResponse;
import com.example.chat_app.service.dto.UserRegistrationRequest;
import com.example.chat_app.service.impl.SearchServiceImp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DtoMapper {

    private static final Logger logger= LoggerFactory.getLogger(SearchServiceImp.class);

    public List<UserResponse> buildUserResponses(List<User> users) {
        if(CollectionUtils.isEmpty(users)){
            logger.warn("Users list is empty");
            return Collections.emptyList();
        }

        List<UserResponse> userResponses = new ArrayList<>();
        for(User user : users){
            userResponses.add(buildUserResponse(user));
        }
        return userResponses;
    }

    public UserResponse buildUserResponse(User user) {
       UserResponse response = new UserResponse();
       response.setEmail(user.getEmail());
       response.setUsername(user.getUsername());

       response.setProfilePicture(user.getProfilePicture());
       response.setFirstName(user.getFirstname());
       response.setLastName(user.getLastname());
       return response;
    }

    public UserRegistrationRequest buildUserRegistrationRequest(
            String firstName,
            String lastName,
            String username,
            String email,
            String password,
            String confirmPassword
    ) {
        UserRegistrationRequest req = new UserRegistrationRequest();
        req.setFirstname(firstName);
        req.setLastname(lastName);
        req.setUsername(username);
        req.setEmail(email);
        req.setPassword(password);
        req.setConfirmPassword(confirmPassword);
        return req;
    }

    public ChatResponse buildChatResponse(ChatMessage message) {

        if (message == null) {
            return null;
        }

        ChatResponse response = new ChatResponse();

        response.setId(message.getId());
        response.setSender(message.getSender());
        response.setReceiver(message.getReceiver());

        // ✅ FIX: default type for old messages
        if (message.getType() == null) {
            response.setType("TEXT");
            response.setContent(message.getContent());
        } else {
            response.setType(message.getType().name());
            response.setContent(message.getContent());
        }

        response.setFileName(message.getFileName());
        response.setFileType(message.getFileType());
        response.setFileSize(message.getFileSize());
        response.setTime(message.getTime());

        return response;
    }



    public List<ChatResponse> buildChatResponses(List<ChatMessage> messages){
        if(CollectionUtils.isEmpty(messages)){
            return Collections.emptyList();
        }
        List<ChatResponse> chatResponses = new ArrayList<>();
        for(ChatMessage message : messages){
            chatResponses.add(buildChatResponse(message));
        }
        return chatResponses;
    }


}
