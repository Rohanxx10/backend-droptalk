package com.example.chat_app.repository;


import com.example.chat_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUsername(String username);


    Optional<User> findByEmail(String email);


    List<User> findByUsernameContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(String keyword, String keyword1, String keyword2);


    boolean existsByUsernameIgnoreCase(String trim);
}
