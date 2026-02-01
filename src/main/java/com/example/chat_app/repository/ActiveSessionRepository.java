package com.example.chat_app.repository;

import com.example.chat_app.model.ActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActiveSessionRepository extends JpaRepository<ActiveSession,Long> {
    Optional<ActiveSession> findByToken(String token);
}
