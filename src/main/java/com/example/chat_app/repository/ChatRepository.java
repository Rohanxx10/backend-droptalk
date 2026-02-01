package com.example.chat_app.repository;


import com.example.chat_app.model.ChatMessage;
import org.apache.logging.log4j.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage,Long> {

    @Query("SELECT m FROM ChatMessage m WHERE (m.sender = :sender AND m.receiver = :recipient) OR (m.sender = :recipient AND m.receiver = :sender) ORDER BY m.createdAt ASC")
    List<ChatMessage> findChatMessagesBetweenUsers(@Param("sender") String sender, @Param("recipient") String recipient);


    List<ChatMessage> findByExpiresAtBefore(LocalDateTime now);
}
