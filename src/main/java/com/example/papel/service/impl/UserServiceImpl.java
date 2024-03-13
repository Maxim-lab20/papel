package com.example.papel.service.impl;

import com.example.papel.entity.UserEntity;
import com.example.papel.mapper.UserMapper;
import com.example.papel.repository.UserRepository;
import com.example.papel.service.UserService;
import com.papel.openapi.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        return IntStream.range(0, 3)
                .mapToObj(i -> executeWithRetry(userDto))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Failed to create post user to optimistic locking conflict after 3 retries. Please try again later.");
                    return new OptimisticLockingFailureException("Failed to create user due to optimistic locking conflict.");
                });
    }

    private UserDto executeWithRetry(UserDto userDto) {
        try {
            UserEntity persistedUserEntity = userRepository.save(UserMapper.INSTANCE.toUserEntity(userDto));
            return UserMapper.INSTANCE.toUserDto(persistedUserEntity);
        } catch (OptimisticLockingFailureException ex) {
            log.warn("Optimistic locking failure occurred while creating user. Retrying operation...");
            return null;
        }
    }

}
