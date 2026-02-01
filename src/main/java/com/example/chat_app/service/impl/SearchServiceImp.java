package com.example.chat_app.service.impl;

import com.example.chat_app.errors.EmptyFieldException;
import com.example.chat_app.model.User;
import com.example.chat_app.repository.UserRepository;
import com.example.chat_app.response.UserResponse;
import com.example.chat_app.service.SearchService;
import com.example.chat_app.utils.DtoMapper;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class SearchServiceImp implements SearchService {


    private static final Logger log= LoggerFactory.getLogger(SearchServiceImp.class);

    private final DtoMapper dtoMapper;

    private final UserRepository userRepository;

    public SearchServiceImp(DtoMapper dtoMapper, UserRepository userRepository) {
        this.dtoMapper = dtoMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> fetchUserByKeyword(String keyword) {

        if(StringUtils.isEmpty(keyword)){
            throw new EmptyFieldException("Search Keyword must not be blank", HttpStatus.BAD_REQUEST);
        }

        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(keyword, keyword, keyword);

        if(CollectionUtils.isEmpty(users)){
            log.warn("Users not found");
            return new ArrayList<>();
        }
        return dtoMapper.buildUserResponses(users);


    }
}
