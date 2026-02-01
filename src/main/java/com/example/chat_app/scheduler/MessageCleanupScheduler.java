package com.example.chat_app.scheduler;

import com.example.chat_app.model.ChatMessage;
import com.example.chat_app.repository.ChatRepository;
import com.example.chat_app.service.ChatService;
import com.example.chat_app.service.impl.BackblazeDeleteService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.message.Message;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling

public class MessageCleanupScheduler {

    private final ChatRepository messageRepository;
    private final BackblazeDeleteService backblazeDeleteService;

    public MessageCleanupScheduler(
            ChatRepository messageRepository,
            BackblazeDeleteService backblazeDeleteService
    ) {
        this.messageRepository = messageRepository;
        this.backblazeDeleteService = backblazeDeleteService;
    }

    @Transactional
    @Scheduled(fixedRate = 60_000)
    public void deleteExpiredMessages() {

        System.out.println("Cleanup scheduler running...");

        List<ChatMessage> expiredMessages =
                messageRepository.findByExpiresAtBefore(LocalDateTime.now());

        for (ChatMessage msg : expiredMessages) {


                System.out.println(msg.getContent());
                System.out.println("try deleting backblaze data");
                backblazeDeleteService.deleteFileByName(msg.getContent());

            messageRepository.delete(msg);
        }
    }
}
