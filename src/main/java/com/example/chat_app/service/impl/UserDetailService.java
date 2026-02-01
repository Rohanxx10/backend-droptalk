package com.example.chat_app.service.impl;

import com.example.chat_app.model.CustomerUserDetail;
import com.example.chat_app.model.User;
import com.example.chat_app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with provided username"+username));

        return new CustomerUserDetail(user);
    }
}
