package com.example.chat_app.controller;


import com.example.chat_app.response.UserResponse;
import com.example.chat_app.service.SearchService;
import com.example.chat_app.service.impl.SearchServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${app.title}")
public class SearchController {

    private final SearchServiceImp searchService;

    public SearchController(SearchServiceImp searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search-user")
    public ResponseEntity<?> search(@RequestParam String name){
        System.out.println("keyword"+name);
        List<UserResponse> userResponses=searchService.fetchUserByKeyword(name);
        return ResponseEntity.ok(userResponses);



    }

}
