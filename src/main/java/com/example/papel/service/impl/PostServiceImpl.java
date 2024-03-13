package com.example.papel.service.impl;

import com.example.papel.entity.PostEntity;
import com.example.papel.mapper.PostMapper;
import com.example.papel.repository.PostRepository;
import com.example.papel.service.PostService;
import com.papel.openapi.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        PostEntity persistedPostEntity = postRepository.save(PostMapper.INSTANCE.toPostEntity(postDto));
        return PostMapper.INSTANCE.toPostDto(persistedPostEntity);
    }

}
