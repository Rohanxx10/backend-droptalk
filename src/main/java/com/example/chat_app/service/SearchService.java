package com.example.chat_app.service;

import com.example.chat_app.response.UserResponse;

import java.util.List;

public interface SearchService {

    List<UserResponse> fetchUserByKeyword(String keyword);

}
