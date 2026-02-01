package com.example.chat_app.service;

import com.example.chat_app.model.ActiveSession;
import com.example.chat_app.model.User;

public interface SessionManagementService {

    ActiveSession createSession(User user , String token);

    void invalidateActiveSession(User user, String token);

}
