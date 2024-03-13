package com.example.papel.service.impl;

import com.example.papel.entity.UserEntity;
import com.example.papel.exception.UserNotFoundException;
import com.example.papel.mapper.UserMapper;
import com.example.papel.repository.UserRepository;
import com.example.papel.service.UserService;
import com.papel.openapi.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        try {
            UserEntity persistedUserEntity = userRepository.save(UserMapper.INSTANCE.toUserEntity(userDto));
            return UserMapper.INSTANCE.toUserDto(persistedUserEntity);
        } catch (OptimisticLockingFailureException e) {
            log.error("Failed to create user due to optimistic locking conflict.");
            throw e;
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserEntity existingUserEntity = userRepository.findById(userDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userDto.getUserId()));

        existingUserEntity.setUsername(userDto.getUsername());
        existingUserEntity.setEmail(userDto.getEmail());

        try {
            UserEntity updatedUserEntity = userRepository.save(existingUserEntity);
            return UserMapper.INSTANCE.toUserDto(updatedUserEntity);
        } catch (OptimisticLockingFailureException e) {
            log.error("Failed to update user due to optimistic locking conflict.");
            throw e;
        }
    }
}
