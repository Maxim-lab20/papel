package com.example.papel.service;


import com.papel.openapi.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);
}
