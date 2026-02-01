package com.example.chat_app.controller;


import com.example.chat_app.errors.AlreadyExistsException;
import com.example.chat_app.errors.InvalidPasswordExecption;
import com.example.chat_app.response.BaseResponse;
import com.example.chat_app.response.LoginResponse;
import com.example.chat_app.response.UserResponse;
import com.example.chat_app.service.dto.LoginRequest;
import com.example.chat_app.service.dto.UserRegistrationRequest;
import com.example.chat_app.service.impl.UserServiceImp;
import com.example.chat_app.utils.DtoMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${app.title}")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserServiceImp userService;
    private final DtoMapper mapper;


    public  UserController(UserServiceImp userService, DtoMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping(
            path = "/create-user",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )

    public ResponseEntity<?> registerUser(@RequestPart("first_name") String firstName, @RequestPart("last_name") String lastName, @RequestPart("username") String username, @RequestPart("email") String email, @RequestPart("password") String password, @RequestPart("confirm_password") String confirmPassword, @RequestPart(value = "profilePicture",required = false) MultipartFile profilePicture) throws IOException, AlreadyExistsException, InvalidPasswordExecption {

        UserRegistrationRequest userRequest = mapper.buildUserRegistrationRequest(firstName, lastName, username, email, password, confirmPassword);
        BaseResponse response = userService.registerUser(userRequest, profilePicture);
        System.out.println("response "+response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws IOException, AlreadyExistsException, InvalidPasswordExecption {
        LoginResponse response=userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user-profile/{username}")
    public ResponseEntity<?> getUserProfile(@PathVariable("username") String username){
        UserResponse response=userService.getUserProfile(username);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/token-validation")
    public ResponseEntity<?> validateToken(@RequestBody String token){
        Boolean result=userService.isValidToken(token);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/isExist-user")
    public ResponseEntity<Boolean> isExistUser(@RequestParam String username) {
        return ResponseEntity.ok(userService.isUserExist(username));
    }

    @PostMapping("/isExist-email")
    public ResponseEntity<Boolean> isExistEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.isUserEmailExist(email));
    }




}
