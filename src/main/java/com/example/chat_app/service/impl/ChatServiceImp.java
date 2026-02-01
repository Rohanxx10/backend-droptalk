package com.example.chat_app.service.impl;

import com.example.chat_app.enums.MessageType;
import com.example.chat_app.errors.EmptyFieldException;
import com.example.chat_app.errors.EmptyRequestException;
import com.example.chat_app.model.ChatMessage;
import com.example.chat_app.model.User;
import com.example.chat_app.repository.ChatRepository;
import com.example.chat_app.repository.UserRepository;
import com.example.chat_app.response.ChatResponse;
import com.example.chat_app.service.ChatService;
import com.example.chat_app.service.dto.MessageRequest;
import com.example.chat_app.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service

public class ChatServiceImp implements ChatService {

    private  final UserRepository userRepository;
    private final DtoMapper dtoMapper;
    private final ChatRepository chatRepository;


    public ChatServiceImp(UserRepository userRepository, DtoMapper dtoMapper, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.dtoMapper = dtoMapper;
        this.chatRepository = chatRepository;
    }
    @Transactional
    @Override
    public ChatResponse saveChat(MessageRequest request) {

        if (request == null) {
            throw new EmptyRequestException("Request cannot be empty", HttpStatus.BAD_REQUEST);
        }

        if (request.getContent() == null) {
            throw new EmptyFieldException("Message cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (request.getType() == null) {
            throw new EmptyFieldException("Message type is required", HttpStatus.BAD_REQUEST);
        }

        if (request.getType() == MessageType.TEXT) {
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                throw new EmptyFieldException("Text message cannot be empty", HttpStatus.BAD_REQUEST);
            }
        }

        if (request.getType() != MessageType.TEXT) {
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                throw new EmptyFieldException("File URL cannot be empty", HttpStatus.BAD_REQUEST);
            }
        }


        User user = userRepository.findByUsername(request.getSender())
                .orElseThrow(() ->
                        new UsernameNotFoundException("No user found for provided username"));

        ChatMessage savedMessage = createMessage(request, user);
        return dtoMapper.buildChatResponse(savedMessage);
    }


    @Override
    public List<ChatResponse> getPreviousConversationsBetweenTwoUsers(String sender,String receiver) {
        List<ChatMessage> chatResponses=chatRepository.findChatMessagesBetweenUsers(sender,receiver);
        if(CollectionUtils.isEmpty(chatResponses)){
            return Collections.emptyList();
        }
        return dtoMapper.buildChatResponses(chatResponses);
    }


    private ChatMessage createMessage(MessageRequest request, User user) {

        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setSender(request.getSender());
        chatMessage.setReceiver(request.getReceiver());

        chatMessage.setType(request.getType());
        chatMessage.setContent(request.getContent());

        // FILE-specific fields (safe for TEXT as well)
        chatMessage.setFileName(request.getFileName());
        chatMessage.setFileType(request.getFileType());
        chatMessage.setFileSize(request.getFileSize());

        chatMessage.setTime(
                request.getTime() != null
                        ? request.getTime()
                        : LocalDateTime.now().toString()
        );

        chatMessage.setCreatedAt(LocalDateTime.now());
        chatMessage.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        chatMessage.setUser(user);

        user.getMessages().add(chatMessage); // 🔥 keep this
        System.out.println("before save ");
        return chatRepository.save(chatMessage);
    }




}
