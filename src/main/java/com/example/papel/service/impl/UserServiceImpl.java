package com.example.papel.service.impl;

import com.example.papel.entity.UserEntity;
import com.example.papel.mapper.UserMapper;
import com.example.papel.repository.UserRepository;
import com.example.papel.service.UserService;
import com.papel.openapi.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity persistedUserEntity = userRepository.save(UserMapper.INSTANCE.toUserEntity(userDto));
        return UserMapper.INSTANCE.toUserDto(persistedUserEntity);
    }

}
