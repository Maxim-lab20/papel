package com.example.papel.service.impl;

import com.example.papel.entity.UserEntity;
import com.example.papel.exception.UserNotFoundException;
import com.example.papel.mapper.UserMapper;
import com.example.papel.repository.UserRepository;
import com.papel.openapi.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_Success() {
        // GIVEN
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");

        UserEntity userEntity = UserMapper.INSTANCE.toUserEntity(userDto);
        when(userRepository.save(userEntity))
                .thenReturn(userEntity);

        // WHEN
        UserDto createdUser = userService.createUser(userDto);

        // THEN
        assertThat(createdUser)
                .isNotNull();
        assertThat(createdUser.getUsername())
                .isEqualTo(userDto.getUsername());
        assertThat(createdUser.getEmail())
                .isEqualTo(userDto.getEmail());
    }

    @Test
    void createUser_OptimisticLockingFailure() {
        // GIVEN
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");

        UserEntity userEntity = UserMapper.INSTANCE.toUserEntity(userDto);
        when(userRepository.save(userEntity))
                .thenThrow(OptimisticLockingFailureException.class);

        // WHEN / THEN
        assertThrows(OptimisticLockingFailureException.class, () -> userService.createUser(userDto));
    }

    @Test
    void updateUser_Success() {
        // GIVEN
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");

        UserEntity existingUserEntity = UserMapper.INSTANCE.toUserEntity(userDto);
        when(userRepository.findById(userDto.getUserId()))
                .thenReturn(Optional.of(existingUserEntity));
        when(userRepository.save(existingUserEntity))
                .thenReturn(existingUserEntity);

        // WHEN
        UserDto updatedUser = userService.updateUser(userDto);

        // THEN
        assertThat(updatedUser)
                .isNotNull();
        assertThat(updatedUser.getUsername())
                .isEqualTo(userDto.getUsername());
        assertThat(updatedUser.getEmail())
                .isEqualTo(userDto.getEmail());
    }

    @Test
    void updateUser_UserNotFound() {
        // GIVEN
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);

        when(userRepository.findById(userDto.getUserId()))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDto));
    }

    @Test
    void updateUser_OptimisticLockingFailure() {
        // GIVEN
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");

        UserEntity existingUserEntity = UserMapper.INSTANCE.toUserEntity(userDto);
        when(userRepository.findById(userDto.getUserId()))
                .thenReturn(Optional.of(existingUserEntity));
        when(userRepository.save(existingUserEntity))
                .thenThrow(OptimisticLockingFailureException.class);

        // WHEN / THEN
        assertThrows(OptimisticLockingFailureException.class, () -> userService.updateUser(userDto));
    }

}
