package com.example.papel.controller;

import com.example.papel.service.UserService;
import com.papel.openapi.api.UsersApi;
import com.papel.openapi.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userDto));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }
}
