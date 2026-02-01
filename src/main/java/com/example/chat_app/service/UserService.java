package com.example.chat_app.service;

import com.example.chat_app.errors.AlreadyExistsException;
import com.example.chat_app.errors.InvalidPasswordExecption;
import com.example.chat_app.response.BaseResponse;
import com.example.chat_app.response.LoginResponse;
import com.example.chat_app.response.UserResponse;
import com.example.chat_app.service.dto.LoginRequest;
import com.example.chat_app.service.dto.UserRegistrationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {


    BaseResponse registerUser(UserRegistrationRequest request, MultipartFile profilePicture) throws AlreadyExistsException, InvalidPasswordExecption;

    LoginResponse login(LoginRequest request) throws InvalidPasswordExecption;

    UserResponse getUserProfile(String username);
}
