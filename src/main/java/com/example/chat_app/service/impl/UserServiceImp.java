package com.example.chat_app.service.impl;

import com.example.chat_app.enums.Role;
import com.example.chat_app.errors.*;
import com.example.chat_app.model.ActiveSession;
import com.example.chat_app.model.User;
import com.example.chat_app.repository.UserRepository;
import com.example.chat_app.response.BaseResponse;
import com.example.chat_app.response.LoginResponse;
import com.example.chat_app.response.UserResponse;
import com.example.chat_app.service.SessionManagementService;
import com.example.chat_app.service.UserService;
import com.example.chat_app.service.dto.LoginRequest;
import com.example.chat_app.service.dto.UserRegistrationRequest;
import com.example.chat_app.utils.DtoMapper;
import com.example.chat_app.utils.PasswordValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final FileStorageConfigImp  fileStorageConfig;
    private final DtoMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final SessionManagementService sessionManagementService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public BaseResponse registerUser(UserRegistrationRequest request, MultipartFile profilePicture) throws AlreadyExistsException, InvalidPasswordExecption {

        validateUserRequest(request);
        Optional<User> existingUsername=userRepository.findByUsername(request.getUsername());

        if(existingUsername.isPresent()){
            throw new AlreadyExistsException("Username is already exists"+request.getUsername(),HttpStatus.BAD_REQUEST);
        }



       Optional<User> existingEmail=userRepository.findByEmail(request.getEmail());
        if (existingEmail.isPresent()) {
            throw new AlreadyExistsException("Email is already exists"+request.getEmail(),HttpStatus.BAD_REQUEST);
        }

        if(!PasswordValidator.isValid(request.getPassword())){
            throw new InvalidPasswordExecption("Password is not valid",HttpStatus.BAD_REQUEST);
        }



        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new InvalidPasswordExecption("Confirm password is not valid",HttpStatus.BAD_REQUEST);
        }
       User user= createUser(request);
        if(profilePicture!=null || !profilePicture.isEmpty()){
            String profilePicturePath=fileStorageConfig.store(profilePicture);
        user.setProfilePicture(profilePicturePath);
        }

        User savedUser=userRepository.save(user);
        UserResponse userResponse=mapper.buildUserResponse(savedUser);
        System.out.println("saved user "+savedUser);
        return new BaseResponse(HttpServletResponse.SC_CREATED,"User Create Succesfully",userResponse);

    }

    @Override
    public LoginResponse login(LoginRequest request) throws InvalidPasswordExecption {
        if(request.getUsername()==null || request.getPassword()==null){
            throw new EmptyFieldException("Username or password is empty",HttpStatus.BAD_REQUEST);
        }
       User user= userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new UserNotFoundException("Username is empty"+request.getUsername(),HttpStatus.BAD_REQUEST));

       if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
           throw new InvalidPasswordExecption("Invalid Password",HttpStatus.BAD_REQUEST);
       }
       sessionManagementService.invalidateActiveSession(user,user.getToken());

        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtService.generateJwtToken(user,user.getRole());
        System.out.println("token "+token);
        user.setToken(token);

        ActiveSession session=sessionManagementService.createSession(user,token);
        user.setSession(session);
        user.setLoggedIn(true);
        User savedUser=userRepository.save(user);

       return  LoginResponse.builder()
               .message("Login succesful")
               .userId(savedUser.getId())
               .username(savedUser.getUsername())
               .firstName(savedUser.getFirstname())
               .lastName(savedUser.getLastname())
               .email(savedUser.getEmail())

               .token(savedUser.getToken())
               .profilePicture(savedUser.getProfilePicture())
               .build();

    }

    @Override
    public UserResponse getUserProfile(String username) {

        User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("Username is empty"+username,HttpStatus.BAD_REQUEST));
        UserResponse userResponse=mapper.buildUserResponse(user);
        System.out.println("getUserProfile username "+userResponse.getUsername());
        return userResponse;
    }

    private void validateUserRequest(UserRegistrationRequest request) {
        if(request.getFirstname()==null){
            throw new EmptyFieldException("First name is required", HttpStatus.BAD_REQUEST);
        }

        if(request.getLastname()==null){
            throw new EmptyFieldException("Last name is required", HttpStatus.BAD_REQUEST);

        }
        if(request.getPassword()==null){
            throw new EmptyFieldException("Password is required", HttpStatus.BAD_REQUEST);
        }

        if(request.getEmail()==null){
            throw new EmptyFieldException("Email is required", HttpStatus.BAD_REQUEST);
        }





    }

    private User createUser(UserRegistrationRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        user.setRole(Role.USER);
        user.setConfirmPassword(request.getConfirmPassword());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }


    public Boolean isValidToken(String token) {

        return jwtService.validateJwtToken(token);
    }

    public boolean isUserExist(String username) {
        System.out.println("username "+ username +" "+userRepository.existsByUsernameIgnoreCase(username.trim()));
        return userRepository.existsByUsernameIgnoreCase(username.trim());    }

    public Boolean isUserEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
