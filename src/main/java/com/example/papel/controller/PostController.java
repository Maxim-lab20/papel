package com.example.papel.controller;

import com.example.papel.service.PostService;
import com.papel.openapi.api.PostsApi;
import com.papel.openapi.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController implements PostsApi {

    private final PostService postService;

    @Override
    public ResponseEntity<PostDto> createPost(PostDto postDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(postDto));
    }
}
