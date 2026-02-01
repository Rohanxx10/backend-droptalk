package com.example.chat_app.service.impl;


import com.example.chat_app.model.ActiveSession;
import com.example.chat_app.model.User;
import com.example.chat_app.repository.ActiveSessionRepository;
import com.example.chat_app.repository.UserRepository;
import com.example.chat_app.service.SessionManagementService;
import org.hibernate.Session;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SessionManagementServiceImp implements SessionManagementService {

    private final ActiveSessionRepository activeSessionRepository;
    private final UserRepository userRepository;
    public SessionManagementServiceImp(ActiveSessionRepository activeSessionRepository, UserRepository userRepository) {
        this.activeSessionRepository = activeSessionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ActiveSession createSession(User user, String token) {
        ActiveSession session = createActiveSession(user, token);
        return activeSessionRepository.save(session);
    }

    @Override
    public void invalidateActiveSession(User user, String token) {
        invalidateUserSession(user,token);

    }

    private void invalidateUserSession(User user,String token) {
        if(user.getSession()!=null){
            findAndDeleteSession(token);
            user.setSession(null);
            user.setToken(null);
            user.setLoggedIn(false);
        }

            userRepository.save(user);

    }

    private void findAndDeleteSession(String token) {
        activeSessionRepository.findByToken(token)
                .ifPresent(activeSessionRepository::delete);
    }

    private ActiveSession createActiveSession(User user, String token) {
        ActiveSession session = new ActiveSession();

        session.setUser(user);
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now());

        return session;
    }




}
